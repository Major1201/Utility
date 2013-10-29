import com.major.util.*;
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
        SystemUtil.execute("ababda", new SystemUtilExecutor() {
            @Override
            public void runAtTheEnd(String returned) {
                System.out.println(returned);
            }
        });
        System.out.println("aa");
    }
}
