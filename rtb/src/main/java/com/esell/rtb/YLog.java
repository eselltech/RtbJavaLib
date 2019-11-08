package com.esell.rtb;

/**
 * log打印
 *
 * @author NiuLei
 * @date 2019/10/22 11:07
 */
public class YLog {
    /**
     * log前缀
     */
    private static String LOG_TAG_PREFIX = "YiXinFa";
    /**
     * 打印是否可用
     */
    private static boolean PRINT_ABLE = true;

    /**
     * 设置打印是否可用
     *
     * @param printAble
     */
    public static void setPrintAble(boolean printAble) {
        PRINT_ABLE = printAble;
    }

    public static void d(String msg) {
        println(Log.DEBUG, generateTag(), msg, null);
    }

    public static void i(String msg) {
        println(Log.INFO, generateTag(), msg, null);
    }

    public static void w(String msg) {
        println(Log.WARN, generateTag(), msg, null);
    }

    public static void e(String msg) {
        println(Log.ERROR, generateTag(), msg, null);
    }

    public static void e(Throwable tr) {
        println(Log.ERROR, generateTag(), "", tr);
    }

    public static void d(String tag, String msg) {
        println(Log.DEBUG, tag, msg, null);
    }

    public static void d(String tag, String msg, Throwable tr) {
        println(Log.DEBUG, tag, msg, tr);
    }

    public static void i(String tag, String msg) {
        println(Log.INFO, tag, msg, null);
    }

    public static void i(String tag, String msg, Throwable tr) {
        println(Log.INFO, tag, msg, tr);
    }

    public static void w(String tag, String msg) {
        println(Log.WARN, tag, msg, null);
    }

    public static void w(String tag, String msg, Throwable tr) {
        println(Log.WARN, tag, msg, tr);
    }

    public static void e(String tag, String msg) {
        println(Log.ERROR, tag, msg, null);
    }

    public static void e(String msg, Throwable tr) {
        println(Log.ERROR, null, msg, tr);
    }

    public static void e(String tag, String msg, Throwable tr) {
        println(Log.ERROR, tag, msg, tr);
    }

    private static String generateTag() {
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return tag;
    }

    public static void println(int priority, String tag, String msg, Throwable tr) {
        tag = LOG_TAG_PREFIX + " -> " + (tag == null ? "" : tag);
        msg = msg + Tools.throwable2String(tr);
        if (PRINT_ABLE) {
            if (Log.ERROR == priority) {
                System.err.println(tag + " " + Tools.priorityKey(priority) + " " + msg);
            } else {
                System.out.println(tag + " " + Tools.priorityKey(priority) + " " + msg);
            }
        }
    }
}
