import com.major.util.CompressUtil;
import com.major.util.EncryptUtil;
import com.major.util.UnitUtil;
import org.apache.commons.io.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Minjie
 * Date: 13-8-31
 * Time: 下午1:16
 */
public class TestMain {
    public static void main(String[] args) {
        List<File> files = new ArrayList<>();
//        files.add(new File("d:\\IMG_0043.JPG"));
//        files.add(new File("d:\\IMG_0545.JPG"));

//        Map<File, String> map = new HashMap<>();
//        map.put(new File("d:\\IMG_0043.JPG"), "?");
//        map.put(new File("d:\\IMG_0545.JPG"), "?");
//        CompressUtil.zip(map, new File("d:\\1.zip"));
        files.add(new File("d:\\新建文件夹"));
        CompressUtil.zip(new File("d:\\新建文件夹"), new File("d:\\1.zip"));

        try {
            CompressUtil.unzip(new File("d:\\1.zip"), new File("d:\\aa"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
