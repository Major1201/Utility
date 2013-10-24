import com.major.util.CompressUtil;
import com.major.util.EncryptUtil;
import com.major.util.UnitUtil;
import org.apache.commons.io.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * User: Minjie
 * Date: 13-8-31
 * Time: 下午1:16
 */
public class TestMain {
    public static void main(String[] args) {
//        EncryptUtil.decodeBase64(new File("d:\\1.jpg"), new File("d:\\3.jpg"));
        File files[] = new File[2];
        files[0] = new File("d:\\IMG_0043.JPG");
        files[1] = new File("d:\\IMG_0545.JPG");
        CompressUtil.cpio(files, new File("d:\\1.cpio"));

        try {
            CompressUtil.unCpio(new File("d:\\1.cpio"), new File("d:\\aa"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
