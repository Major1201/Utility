package com.major.util.mail;

import java.io.InputStream;
import java.util.*;

/**
 * User: Minjie
 * Date: 13-10-29
 * Time: 下午10:00
 */
@SuppressWarnings("UnusedDeclaration")
public class MailObject {
    String from;
    Date receiveTime;
    String subject;
    String content;
    Map<String, InputStream> attachments = new HashMap<>();

    public Map<String, InputStream> getAttachments() {
        return attachments;
    }

    public String getFrom() {
        return from;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

}
