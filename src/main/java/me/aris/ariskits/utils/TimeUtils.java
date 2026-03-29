package me.aris.ariskits.utils;

public class TimeUtils {
    public static long parseTime(String input) {
        try {
            input = input.toLowerCase();
            if (input.endsWith("s")) return Long.parseLong(input.replace("s", ""));
            if (input.endsWith("m")) return Long.parseLong(input.replace("m", "")) * 60;
            if (input.endsWith("h")) return Long.parseLong(input.replace("h", "")) * 3600;
            if (input.endsWith("d")) return Long.parseLong(input.replace("d", "")) * 86400;
            return Long.parseLong(input);
        } catch (Exception e) {
            return 0;
        }
    }
}
