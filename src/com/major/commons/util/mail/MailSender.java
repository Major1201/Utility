package com.major.commons.util.mail;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.*;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Minjie
 * Date: 13-9-20
 * Time: 下午3:15
 */
@SuppressWarnings("UnusedDeclaration")
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
    public void send(String to, String subject, String content) throws EmailException {
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
     * Send an e-mail with html content or both html and text content.
     * @param to  receiver address
     * @param subject subject
     * @param htmlContent html content
     * @param textContent text content
     * @throws EmailException
     */
    public void sendHtmlMail(String to, String subject, String htmlContent, String textContent) throws EmailException {
        HtmlEmail htmlEmail = new HtmlEmail();
        htmlEmail.setHostName(hostName);
        htmlEmail.setAuthentication(userName, password);
        htmlEmail.setCharset("UTF-8");
        htmlEmail.setFrom(userName);
        htmlEmail.addTo(to);
        htmlEmail.setSubject(subject);
        htmlEmail.setHtmlMsg(htmlContent);
        if (StringUtils.isNotEmpty(textContent))
            htmlEmail.setTextMsg(textContent);
        htmlEmail.send();
    }

    /**
     * Send an e-mail with html content.
     * @param to  receiver address
     * @param subject subject
     * @param htmlContent html content
     * @throws EmailException
     */
    public void sendHtmlMail(String to, String subject, String htmlContent) throws EmailException {
        sendHtmlMail(to, subject, htmlContent, null);
    }

    /**
     * Send an e-mail with an attachment.
     * @param to receiver address
     * @param subject subject
     * @param content content
     * @param attachment attachment path
     * @throws EmailException
     */
    public void send(String to, String subject, String content, String attachment) throws EmailException {
        List<String> attachments = new ArrayList<>();
        attachments.add(attachment);
        send(to, subject, content, attachments);
    }

    /**
     * Send an e-mail with attachment list.
     * @param to receiver address
     * @param subject subject
     * @param content content
     * @param attachments attachment list
     * @throws EmailException
     */
    public void send(String to, String subject, String content, List<String> attachments) throws EmailException {
        MultiPartEmail multiPartEmail = new MultiPartEmail();
        multiPartEmail.setHostName(hostName);
        multiPartEmail.setAuthentication(userName, password);
        multiPartEmail.setCharset("UTF-8");
        multiPartEmail.setFrom(userName);
        multiPartEmail.addTo(to);
        multiPartEmail.setSubject(subject);
        multiPartEmail.setMsg(content);

        //set attachments
        if (CollectionUtils.isNotEmpty(attachments)) {
            for (String attachment : attachments) {
                multiPartEmail.attach(getAttachment(attachment));
            }
        }

        multiPartEmail.send();
    }

    /**
     * Get an EmailAttachment object from a file string.
     * @param file a file path string
     * @return an EmailAttachment object
     */
    private EmailAttachment getAttachment(String file) {
        EmailAttachment emailAttachment = new EmailAttachment();
        emailAttachment.setPath(file);
        emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
        String fileBaseName = FilenameUtils.getName(file);
        emailAttachment.setDescription(fileBaseName);
        emailAttachment.setName(fileBaseName);
        return emailAttachment;
    }
}
