import com.major.commons.util.PinYinUtil;
import com.major.commons.util.TimeZoneUtil;

import java.util.Date;
import java.util.TimeZone;

/**
 * User: Minjie
 * Date: 13-8-31
 * Time: 下午1:16
 */
public class TestMain {
    public static void main(String[] args) {
        System.out.println(TimeZoneUtil.getTimeZoneDate(new Date(), TimeZone.getDefault(), TimeZoneUtil.Australia_Sydney));
        System.out.println(PinYinUtil.getFullPinyinWithTone("嬲嫐娚"));
    }
}
