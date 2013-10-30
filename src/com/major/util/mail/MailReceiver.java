package com.major.util.mail;

import org.apache.commons.io.IOUtils;

import javax.mail.*;
import javax.mail.internet.ContentType;
import javax.mail.internet.MimeUtility;
import javax.mail.search.SearchTerm;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * User: Minjie
 * Date: 13-10-29
 * Time: 下午4:06
 */
//@SuppressWarnings("UnusedDeclaration")
public class MailReceiver {
    private String userName = "";
    private String password = "";
    private String pop3Server = "";

    /**
     * Constructor that indicates authenticate messages.
     * @param userName user name
     * @param password password
     * @param pop3Server pop3 server name
     */
    public MailReceiver(String userName, String password, String pop3Server) {
        this.userName = userName;
        this.password = password;
        this.pop3Server = pop3Server;
    }

    /**
     * Create a new thread and receive mails that is under specified conditions.
     * @param condition condition
     * @param executor do what you want with each mail.
     * @throws MessagingException
     */
    public void receive(final SearchTerm condition, final MailExecutor executor) throws MessagingException {
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(0);
                receiveAndWait(condition, executor);
                return null;
            }
        });
        new Thread(futureTask).start();
    }

    /**
     * Receive mails that is under specified conditions.
     * @param condition condition
     * @param executor do what you want with each mail.
     * @throws MessagingException
     */
    public void receiveAndWait(SearchTerm condition, MailExecutor executor) throws MessagingException {
        Properties properties = new Properties();
        properties.setProperty("mail.store.protocol", "imap");
        properties.setProperty("mail.pop3.host", pop3Server); //pop3 server
        Session session = Session.getInstance(properties);
        Store store = null;
        Folder folder = null;
        try {
            store = session.getStore("imap"); //or pop3
            store.connect(pop3Server, userName, password);
            //get folder
            folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);
            //get messages
            Message[] messages;
            if (condition != null)
                messages = folder.search(condition);
            else
                messages = folder.getMessages();
            if (messages != null)
                for (Message message : messages) {
                    MailObject mailObject = new MailObject();
                    processMessage(message, mailObject);
                    executor.processMail(mailObject);
                    //close input streams
                    Set<String> attachmentSet = mailObject.attachments.keySet();
                    for (String key : attachmentSet) {
                        IOUtils.closeQuietly(mailObject.attachments.get(key));
                    }
                }
        } finally {
            if (folder != null)
                try {
                    folder.close(false);
                } catch (MessagingException e) {
                    //nothing to do
                }
            if (store != null)
                try {
                    store.close();
                } catch (MessagingException e) {
                    //nothing to do
                }
        }
    }

    /**
     * Process a mail
     * @param message message to process
     * @param mailObject mail object
     */
    private void processMessage(Message message, MailObject mailObject) {
        try {
            message.setFlag(Flags.Flag.SEEN, true); //set seen flag

            {
                mailObject.receiveTime = message.getSentDate();
                mailObject.subject = MimeUtility.decodeText(message.getSubject());
                mailObject.from = MimeUtility.decodeText(message.getFrom()[0].toString());
                //analyze content
                ContentType contentType = new ContentType(message.getContentType());
                if ("TEXT".equals(contentType.getPrimaryType().toUpperCase())) {
                    mailObject.content = String.valueOf(message.getContent());
                } else if ("MULTIPART".equals(contentType.getPrimaryType().toUpperCase())) {
                    Multipart multipart = (Multipart) message.getContent();
                    StringBuilder content = new StringBuilder();
                    for (int i = 0; i < multipart.getCount(); i++) {
                        content.append(processPart(multipart.getBodyPart(i), mailObject));
                    }
                    mailObject.content = content.toString();
                }
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Analyze a body part recursively.
     * @param part part to analyze
     * @param mailObject mail object
     * @return if it's an attachment then add to mail object, else if text then return, still a multipart then make a recursion
     */
    private String processPart(BodyPart part, MailObject mailObject) throws MessagingException, IOException {
        String disposition = part.getDisposition();
        if (disposition != null && (disposition.equals(Part.ATTACHMENT) || disposition.equals(Part.INLINE))) {
            //if attachment
            mailObject.attachments.put(MimeUtility.decodeText(part.getFileName()), part.getInputStream());
            return "";
        } else if (disposition == null) {
            ContentType partContentType = new ContentType(part.getContentType());
            if ("TEXT".equals(partContentType.getPrimaryType().toUpperCase())) {
                if ("ALTERNATIVE".equals(new ContentType(part.getParent().getContentType()).getSubType().toUpperCase()))
                    if ("PLAIN".equals(partContentType.getSubType().toUpperCase()))
                        return String.valueOf(part.getContent());
                    else if ("HTML".equals(partContentType.getSubType().toUpperCase()))
                        return "";
                    else
                        return "";
                else
                    return String.valueOf(part.getContent());
            } else if ("MULTIPART".equals(partContentType.getPrimaryType().toUpperCase())) {
                Multipart multipart = (Multipart) part.getContent();
                StringBuilder content = new StringBuilder();
                for (int i = 0; i < multipart.getCount(); i++) {
                    content.append(processPart(multipart.getBodyPart(i), mailObject));
                }
                return content.toString();
            }
        }
        return "";
    }
}
