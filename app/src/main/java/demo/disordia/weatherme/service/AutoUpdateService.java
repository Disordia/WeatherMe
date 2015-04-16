package demo.disordia.weatherme.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import demo.disordia.weatherme.console.ShowWeatherService;
import demo.disordia.weatherme.net.HttpCallbackListener;
import demo.disordia.weatherme.net.QueryNTManager;
import demo.disordia.weatherme.optimization.GlobalApplication;
import demo.disordia.weatherme.receiver.AutoUpdateReceiver;
import demo.disordia.weatherme.setting.Settings;
import demo.disordia.weatherme.util.LogUtil;
import demo.disordia.weatherme.util.WeatherUtility;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-22.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.service
 * Descibe:此类用于在后台实时更新程序:
 */
public class AutoUpdateService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //获取设置信息:
        Settings settings=Settings.getInstance();
        //检测是否更新天气:
        if (settings.isRefresh()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    updateWeather();
                }
            }).start();
        }//结束检测
        LogUtil.d("AutoUpdateService","Update Service");


        //开始显示天气:
        LogUtil.d("AutoUpdateService", "The Alpha is" + settings.getAlpha());
        //检测是否显示
        if (settings.getAlpha()>0) {
            Intent intentw = new Intent(GlobalApplication.getContext(), ShowWeatherService.class);
            GlobalApplication.getContext().startService(intentw);
        }

            AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
            int anHour = 1 * 60 * 60 * 1000;
            long triggerTime = SystemClock.elapsedRealtime() + anHour;
            Intent i = new Intent(this, AutoUpdateReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);
            //设置苏醒时间:
            manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, pendingIntent);

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 此函数用于更新天气信息:
     */
    private void updateWeather() {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(GlobalApplication.getContext());
        //获取城市信息:
        String weatherCode=preferences.getString("weather_code","");
        //向服务器发出请求:
        String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + weatherCode;
        QueryNTManager.sendHttpRequest(address,new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                WeatherUtility.handleWeatherResponse(response);
            }

            @Override
            public void onErro(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
