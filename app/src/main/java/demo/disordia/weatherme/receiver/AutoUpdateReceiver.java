package demo.disordia.weatherme.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import demo.disordia.weatherme.console.ShowWeatherService;
import demo.disordia.weatherme.service.AutoUpdateService;
import demo.disordia.weatherme.setting.Settings;
import demo.disordia.weatherme.util.LogUtil;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-22.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.receiver
 * Descibe:
 */
public class AutoUpdateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Settings settings=Settings.getInstance();
        //侦测是否在后台运行:
        if (settings.isRunInBk()) {
            //更新天气
            Intent i = new Intent(context, AutoUpdateService.class);
            context.startService(i);
            //刷新显示

            LogUtil.d("AutoUpdateRecever", "The Alpha is" + settings.getAlpha());
            //侦测是否显示天气:
            if (settings.getAlpha() > 0) {
                Intent i1 = new Intent(context, ShowWeatherService.class);
                context.startService(i1);
            }
        }
        //结束侦测
    }
}
