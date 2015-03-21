package demo.disordia.weatherme.util;

import android.util.Log;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-20.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.util
 * Descibe:此类用于调试时显示日志
 */
public class LogUtil {
    public static final int VERBOSE=1;
    public static final int DEBUG=2;
    public static final int INFO=3;
    public static final int WARN=4;
    public static final int ERRO=5;
    public static final int NOTING=6;
    //LEVEl用于指定打印日志的级别，使用NOTING屏蔽所有的打印日志:
    public static final int LEVEL=VERBOSE;

    public static void v(String tag,String msg){
        if (LEVEL<=VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag,String msg){
        if (LEVEL<=DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag,String msg){
        if (LEVEL<=INFO) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag,String msg){
        if (LEVEL<=WARN) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag,String msg){
       if (LEVEL<=ERRO){
           Log.e(tag,msg);
       }
    }


}
