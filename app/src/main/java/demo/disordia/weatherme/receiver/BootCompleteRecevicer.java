package demo.disordia.weatherme.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import demo.disordia.weatherme.service.AutoUpdateService;
import demo.disordia.weatherme.setting.Settings;

/**
 * Created by Disordia profaneden on 2015-04-11.
 */
public class BootCompleteRecevicer extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "Boot Complete", Toast.LENGTH_SHORT).show();
        Settings settings=Settings.getInstance();
        //如果开机启动:
        if (settings.isStartWithBoot()) {
            //开启后台服务:
            Toast.makeText(context, "WeatherME Service Start", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context, AutoUpdateService.class);
            context.startService(i);
        }
    }
}
