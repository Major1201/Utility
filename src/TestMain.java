import com.major.util.mail.*;
import org.apache.commons.mail.EmailException;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Minjie
 * Date: 13-8-31
 * Time: 下午1:16
 */
public class TestMain {
    public static void main(String[] args) {
        MailSender mailSender = new MailSender("454713371@qq.com", "u7i8o9p0Z", "smtp.exmail.qq.com");
        List<String> list = new ArrayList<>();
        list.add("d:\\download\\p_large_DDiE_14a0000083eb1265.jpg");
        list.add("d:\\download\\P020130624407952037020.xls");
        try {
            mailSender.send("454713371@qq.com", "主题", "正文", list);
        } catch (EmailException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        MailReceiver receiver = new MailReceiver();
        try {
            receiver.receive(SearchCondition.ALL, new MailExecutor() {
                @Override
                public void processMail(MailObject mail) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }
            });
        } catch (MessagingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
