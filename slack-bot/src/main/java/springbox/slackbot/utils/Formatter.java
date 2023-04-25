package springbox.slackbot.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Formatter {
    public static String format(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }
}
