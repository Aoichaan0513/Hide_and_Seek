package org.aoichaan0513.hide_and_seek.API;

public class TimerFormat {

    public static String format(int time) {
        int m = 0;
        int s = time;

        if (s >= 60) {
            m = s / 60;
            s = s - m * 60;
        }
        String sm = String.valueOf(m);
        String ss = String.valueOf(s);
        if (m <= 9) sm = "0" + sm;
        if (s <= 9) ss = "0" + ss;
        return sm + ":" + ss;
    }

    public static String formatMin(int time) {
        String t = TimerFormat.format(time);
        return t.split(":")[0].startsWith("0") ? t.split(":")[0].substring(t.split(":")[0].length() - 1) : t.split(":")[0];
    }

    public static String formatSec(int time) {
        String t = TimerFormat.format(time);
        return t.split(":")[1].startsWith("0") ? t.split(":")[1].substring(t.split(":")[1].length() - 1) : t.split(":")[1];
    }

    public static String formatJapan(int time) {
        String t = TimerFormat.format(time);
        return t.replace(":", "分") + "秒";
    }
}