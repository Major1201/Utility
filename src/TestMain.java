import com.major.util.EncryptUtil;
import com.major.util.UnitUtil;

import java.io.File;

/**
 * User: Minjie
 * Date: 13-8-31
 * Time: 下午1:16
 */
public class TestMain {
    public static void main(String[] args) {
        EncryptUtil.decodeBase64(new File("d:\\1.jpg"), new File("d:\\3.jpg"));
    }
}
