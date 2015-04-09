package demo.disordia.weatherme.console;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.media.Image;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.optimization.GlobalApplication;
import demo.disordia.weatherme.util.LogUtil;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-04-02.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.console
 * Descibe:
 */
public class ShowWeatherService extends Service {

    private static final int LOW_LEVEL=1;
    private static final int MIDDLE_LEVEL=2;
    private static final int HIGH_LEVEL=3;
    private static final int MAX_LEVEL=4;




    private String weather;
List<Intent> weatherServices=new ArrayList<Intent>();
    public void RemoveAllWeather(){
        for (Intent intent:weatherServices){
        stopService(intent);
        }
        weatherServices.clear();
    }

    public void showRain(int level){
        Intent intent=new Intent(this,ShowRainService.class);
        intent.putExtra("level",level);
        weatherServices.add(intent);
        startService(intent);
    }

    public  void showSun(int level){
        Intent intent=new Intent(this,ShowSunService.class);
        intent.putExtra("level",level);
        weatherServices.add(intent);
        startService(intent);
    }

    public void showSmoke(int level){

        Intent intent=new Intent(this,ShowSmokeService.class);
        intent.putExtra("level",level);
        weatherServices.add(intent);
        LogUtil.d("ShowWeatherService", "showSmoke function called");
        startService(intent);
    }

    public void showSnow(int level){
        Intent intent=new Intent(this,ShowSnowService.class);
        intent.putExtra("level",level);
        weatherServices.add(intent);
        startService(intent);
    }


    /**
     *
     */
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.d("ShowWeatherService", "onCreate()");
        ShowWeather();
    }

    public void ShowWeather(){
        LogUtil.d("ShowWeatherService", "now start to get the weather");
        //获取天气信息:
        getTheWeather();
        LogUtil.d("ShowWeatherService","The weather is:"+weather);
        //停止当前的天气:
        RemoveAllWeather();
        //判断天气情况:
        if (weather.equals("晴")) {
            showSun(HIGH_LEVEL);
        } else if (weather.equals("多云")) {
            showSun(LOW_LEVEL);
            showSmoke(MIDDLE_LEVEL);
        } else if (weather.equals("阴")) {
            LogUtil.d("ShowWeatherService","Start show Smoke");
            showSmoke(HIGH_LEVEL);
        } else if (weather.equals("小雨")) {
            showRain(LOW_LEVEL);
        } else if (weather.equals("中雨")) {
            showRain(MIDDLE_LEVEL);
        } else if (weather.equals("暴雨")) {
            showRain(HIGH_LEVEL);
        } else if (weather.equals("阵雨")) {
            showRain(HIGH_LEVEL);
        } else if (weather.equals("雷阵雨")) {
            showRain(HIGH_LEVEL);
        } else if (weather.equals("雷电")) {
        } else if (weather.equals("冰雹")) {
        } else if (weather.equals("轻雾")) {
        } else if (weather.equals("雾")) {
        } else if (weather.equals("浓雾")) {
        } else if (weather.equals("霾")) {
        } else if (weather.equals("雨夹雪")) {
        } else if (weather.equals("小雪")) {
            showSnow(LOW_LEVEL);
        } else if (weather.equals("中雪")) {
            showSnow(MIDDLE_LEVEL);
        } else if (weather.equals("大雪")) {
            showSnow(HIGH_LEVEL);
        } else if (weather.equals("暴雪")) {
            showSnow(MAX_LEVEL);
        } else if (weather.equals("冻雨")) {
        } else if (weather.equals("霜")) {
        } else if (weather.equals("霜冻")) {
        } else if (weather.equals("台风")) {
        } else if (weather.equals("浮尘")) {
        } else if (weather.equals("扬沙")) {
        } else if (weather.equals("沙尘暴")) {
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //
        LogUtil.d("ShowWeatherService", "ShowWeatherService Start to flush");
        ShowWeather();
        return START_STICKY;
    }

    private void getTheWeather() {
        Time time = new Time();
        //获取当前的时间:
        time.setToNow();
        int hour = time.hour;
        LogUtil.d("ShowWeatherService","Start to get the weather");
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(GlobalApplication.getContext());
        //此处以后需要添加判断是上午还是下午
        weather = sharedPreferences.getString("day_weather", "");

        //结束获取

    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
