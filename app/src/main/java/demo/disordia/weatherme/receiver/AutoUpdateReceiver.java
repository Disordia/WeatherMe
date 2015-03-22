package demo.disordia.weatherme.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import demo.disordia.weatherme.service.AutoUpdateService;

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
        Intent i=new Intent(context, AutoUpdateService.class);
        context.startService(i);
    }
}
