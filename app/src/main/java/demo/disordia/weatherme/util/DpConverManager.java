package demo.disordia.weatherme.util;

import android.content.res.TypedArray;

import demo.disordia.weatherme.optimization.GlobalApplication;

/**
 * Created by Disordia profaneden on 2015-04-18.
 */
public class DpConverManager {

    private static float scale;

    public static float getDp(){
        scale = GlobalApplication.getContext().getResources().getDisplayMetrics().density;
        return scale;
    }
}
