import com.major.util.NetUtil;
import com.major.util.ObjectUtil;
import com.major.util.mail.*;
import org.apache.commons.mail.EmailException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.MessagingException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Minjie
 * Date: 13-8-31
 * Time: 下午1:16
 */
public class TestMain {

    private static Logger logger = LogManager.getLogger(TestMain.class);

    public static void main(String[] args) {
        System.out.println(NetUtil.download("http://www.baidu.com/img/bdlogo.gif", new File("d:\\1.gif")));
//        System.out.println(NetUtil.download("http://static.blog.csdn.net/images/medal/holdon_s2.gif", new File("d:\\2.gif")));
    }
}
