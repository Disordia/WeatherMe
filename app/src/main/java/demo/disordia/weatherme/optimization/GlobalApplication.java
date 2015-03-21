package demo.disordia.weatherme.optimization;

import android.app.Application;
import android.content.Context;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-21.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.optimization
 * Descibe:
 */
public class GlobalApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        context=getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }
}
