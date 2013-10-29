package com.major.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import javax.mail.internet.MimeMultipart;
import java.util.List;

/**
 * User: Minjie
 * Date: 13-9-20
 * Time: 下午3:15
 */
public class MailSender {
    private String userName = "";
    private String password = "";
    private String hostName = "";

    /**
     * Constructor that indicates authenticate messages.
     * @param userName user name
     * @param password password
     * @param hostName host name
     */
    public MailSender(String userName, String password, String hostName) {
        this.userName = userName;
        this.password = password;
        this.hostName = hostName;
    }

    /**
     * Send an e-mail without attachments.
     * @param to receiver address
     * @param subject subject
     * @param content content
     * @throws EmailException
     */
    public void send(String to, String subject, String content) throws EmailException{
        SimpleEmail simpleEmail = new SimpleEmail();
        simpleEmail.setHostName(hostName);
        simpleEmail.setAuthentication(userName, password);
        simpleEmail.setCharset("UTF-8");
        simpleEmail.setFrom(userName);
        simpleEmail.addTo(to);
        simpleEmail.setSubject(subject);
        simpleEmail.setMsg(content);
        simpleEmail.send();
    }

    /**
     * Send an e-mail with an attachment.
     * @param to receiver address
     * @param subject subject
     * @param content content
     * @param attachment attachment path
     * @throws EmailException
     */
    public void send(String to, String subject, String content, String attachment) throws EmailException{
        MultiPartEmail multiPartEmail = new MultiPartEmail();
        multiPartEmail.setHostName(hostName);
        multiPartEmail.setAuthentication(userName, password);
        multiPartEmail.setCharset("UTF-8");
        multiPartEmail.setFrom(userName);
        multiPartEmail.addTo(to);
        multiPartEmail.setSubject(subject);
        multiPartEmail.setMsg(content);

        //set attachment
        EmailAttachment emailAttachment = new EmailAttachment();
        emailAttachment.setPath(attachment);
        emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
        String fileBaseName = FilenameUtils.getBaseName(attachment);
        emailAttachment.setDescription(fileBaseName);
        emailAttachment.setName(fileBaseName);
        multiPartEmail.attach(emailAttachment);

        multiPartEmail.send();
    }
}
