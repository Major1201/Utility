import com.major.util.mail.*;
import org.apache.commons.mail.EmailException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Minjie
 * Date: 13-8-31
 * Time: 下午1:16
 */
public class TestMain {

    private static Logger logger = LogManager.getLogger(TestMain.class);

    public static boolean judge(){
        logger.entry();
        logger.debug("In doIt().");
        logger.info("In doIt()..");
        logger.warn("In doIt()...");
        return logger.exit(true);
    }

    public static void main(String[] args) {
        logger.trace("begin...");
        if (judge()) {
            logger.error("Do it again!");
            logger.fatal("Do it again!!");
        }
        logger.trace("end!");
    }
}
