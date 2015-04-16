package demo.disordia.weatherme.console;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.media.Image;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.activity.WeatherActivity;
import demo.disordia.weatherme.optimization.GlobalApplication;
import demo.disordia.weatherme.setting.Settings;
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

    private static final int LOW_LEVEL = 1;
    private static final int MIDDLE_LEVEL = 2;
    private static final int HIGH_LEVEL = 3;
    private static final int MAX_LEVEL = 4;

    //之前显示的天气:
    private static String preweather;
    //现在的天气
    private String weather;
    List<Intent> weatherServices = new ArrayList<Intent>();

    //结束现在所有的显示们
    public void RemoveAllWeather() {
        for (Intent intent : weatherServices) {
            stopService(intent);
        }
        weatherServices.clear();
    }

    //各种天气的显示函数:
    public void showRain(int level) {
        SharedPreferences.Editor editor = GlobalApplication.getContext().getSharedPreferences("weatherLevel", Context.MODE_PRIVATE).edit();
        editor.putInt("rain", level);
        editor.commit();
        Intent intent = new Intent(this, ShowRainService.class);
        weatherServices.add(intent);
        startService(intent);
    }

    public void showSun(int level) {
        SharedPreferences.Editor editor = GlobalApplication.getContext().getSharedPreferences("weatherLevel", Context.MODE_PRIVATE).edit();
        editor.putInt("sun", level);
        editor.commit();
        Intent intent = new Intent(this, ShowSunService.class);
        weatherServices.add(intent);
        startService(intent);
    }

    public void showSmoke(int level) {

        SharedPreferences.Editor editor = GlobalApplication.getContext().getSharedPreferences("weatherLevel", Context.MODE_PRIVATE).edit();
        editor.putInt("smoke", level);
        editor.commit();
        Intent intent = new Intent(this, ShowSmokeService.class);
        weatherServices.add(intent);
        LogUtil.d("ShowWeatherService", "showSmoke function called");
        startService(intent);
    }

    public void showSnow(int level) {
        SharedPreferences.Editor editor = GlobalApplication.getContext().getSharedPreferences("weatherLevel", Context.MODE_PRIVATE).edit();
        editor.putInt("snow", level);
        editor.commit();
        Intent intent = new Intent(this, ShowSnowService.class);
        weatherServices.add(intent);
        startService(intent);
    }

    public void showFrozen(int level){
        SharedPreferences.Editor editor = GlobalApplication.getContext().getSharedPreferences("weatherLevel", Context.MODE_PRIVATE).edit();
        editor.putInt("frozen", level);
        editor.commit();
        Intent intent = new Intent(this, ShowFrozenService.class);
        weatherServices.add(intent);
        startService(intent);
    }



    private static final int StartID = 1;

    /**
     * onCreate()
     */
    @Override
    public void onCreate() {
        super.onCreate();
        //将服务变为前台:
//        Notification notification = new Notification(R.drawable.notification, "开始运行WeatherME", System.currentTimeMillis());

        Intent notificationIntent = new Intent(this, WeatherActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
//        //自定义界面
//        RemoteViews rv = new RemoteViews(getPackageName(), R.layout.notification_layout);
//        notification.contentView = rv;
//
//        notification.setLatestEventInfo(this,"显示天气ing","点击来获取更多信息",pendingIntent);

/**
 * 另外一种方法:
 */
        // 3构建通知
        Notification.Builder builder = new Notification.Builder(this)
        // API 11添加的方法
        .setContentIntent(pendingIntent).setSmallIcon(R.drawable.notification)
                // 设置状态栏的小标题
                .setLargeIcon(
                        BitmapFactory.decodeResource(getResources(),
                                R.drawable.weather_mel))// 设置下拉列表里的图标
                .setWhen(System.currentTimeMillis()).setTicker("WeatherME 运行中")// 设置状态栏的显示的信息
                .setAutoCancel(false)// 设置不可以清除
                .setContentTitle("WeatherME正在运行") // 设置下拉列表里的标题
                .setContentText("点击可进入"); // 设置可以清除
        Notification notification = builder.build();// API 16添加创建notification的方法
        // 通知
        startForeground(StartID, notification);
        //结束~~

        LogUtil.d("ShowWeatherService", "onCreate()");
        ShowWeather();
    }

    public void ShowWeather() {
        LogUtil.d("ShowWeatherService", "now start to get the weather");
        //获取天气信息:
        getTheWeather();
        //调试信息:
        LogUtil.d("ShowWeatherService", "The weather is:" + weather);
        LogUtil.d("ShowWeatherService", "The boolean is" + ("阴").equals(weather));

        LogUtil.d("ShowWeatherService","开始判断天气情况");
        LogUtil.d("ShowWeatherService","之前的天气是"+preweather);


        LogUtil.d("ShowWeatherService","The two boolean are:"+!weather.equals(preweather)+weatherServices.isEmpty());

        //如果之前的显示的天气和现在要显示的天气是不一样的
        if ((!weather.equals(preweather)) || weatherServices.isEmpty()) {
            //停止当前的天气:
            RemoveAllWeather();
            LogUtil.d("ShowWeatherService", "进入内部判断");
            //判断天气情况:
            if (weather.equals("晴")) {
                showSun(HIGH_LEVEL);
            } else if (weather.equals("多云")) {
                LogUtil.d("ShowWeatherService","显示多云天气");
                showSun(LOW_LEVEL);
                showSmoke(MIDDLE_LEVEL);
            } else if (weather.equals("阴")) {
                LogUtil.d("ShowWeatherService", "Start show Smoke");
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
                showSmoke(MIDDLE_LEVEL);
            } else if (weather.equals("雾")) {
                showSmoke(HIGH_LEVEL);
            } else if (weather.equals("浓雾")) {
                showSmoke(MAX_LEVEL);
            } else if (weather.equals("霾")) {
                showSmoke(MAX_LEVEL);
            } else if (weather.equals("雨夹雪")) {
                showSnow(LOW_LEVEL);
                showRain(MIDDLE_LEVEL);
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
                showFrozen(MIDDLE_LEVEL);
            } else if (weather.equals("霜冻")) {
                showFrozen(HIGH_LEVEL);
            } else if (weather.equals("台风")) {
            } else if (weather.equals("浮尘")) {
            } else if (weather.equals("扬沙")) {
            } else if (weather.equals("沙尘暴")) {
            }//end if
            preweather=weather;
        }//end if
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
        LogUtil.d("ShowWeatherService", "Start to get the weather");
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(GlobalApplication.getContext());
        //此处以后需要添加判断是上午还是下午
        LogUtil.d("ShowWeatherService", "The time is:" + hour);
        if (hour <= 17&&hour>=6) {
            weather = sharedPreferences.getString("day_weather","");
        } else {
            weather = sharedPreferences.getString("night_weather", "");
        }
        //结束获取
        if (TextUtils.isEmpty(preweather)) {
            preweather = weather;
        }
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        RemoveAllWeather();
        super.onDestroy();
    }
}
