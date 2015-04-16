package demo.disordia.weatherme.console;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Gravity;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import demo.disordia.weatherme.console.weatherview.RainView;
import demo.disordia.weatherme.optimization.GlobalApplication;
import demo.disordia.weatherme.setting.Settings;
import demo.disordia.weatherme.util.HomeJudger;
import demo.disordia.weatherme.util.LogUtil;
import demo.disordia.weatherme.util.WindowManagerUtil;

/**
 * Created by Disordia profaneden on 2015-04-09.
 */
public class ShowRainService extends Service {
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private RainView rainView;
    private int level;
    private boolean showOnlyDesktop;
    private boolean showWeather = true;

    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            //判断是否为桌面

            Message message = handler.obtainMessage();
//            message.what = 1;
            handler.sendMessage(message);

        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            // do things here
            if (showWeather) {
                if (HomeJudger.isHome() || showOnlyDesktop == false) {
                    AddView();
                    rainView.invalidate();
                    super.handleMessage(msg);
                } else {
                    RemoveView();
                    super.handleMessage(msg);
                }
            } else {
                RemoveView();
            }
        }
    };


    public void ShowOnScreen() {
        Settings settings = Settings.getInstance();
        //获取是否只在桌面显示:
        showOnlyDesktop = settings.isShowOnlyDesktop();
        //结束获取
        windowManager = (WindowManager) getSystemService(getApplicationContext().WINDOW_SERVICE);
        layoutParams = WindowManagerUtil.getLayoutParams();

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showWeather = true;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //获取等级参数:
        SharedPreferences sharedPreferences = GlobalApplication.getContext().getSharedPreferences("weatherLevel", Context.MODE_PRIVATE);
        level = sharedPreferences.getInt("rain", 0);
        ShowOnScreen();
        rainView = new RainView(this, null, level);
        AddView();
        timer.schedule(timerTask, 40, 100);

    }

    @Override
    public void onDestroy() {
        RemoveView();
        showWeather = false;
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    private boolean isAdd = false;

    private void AddView() {
        if (!isAdd) {
            windowManager.addView(rainView, layoutParams);
            isAdd = true;
        }
    }

    private void RemoveView() {
        if (isAdd) {
            if (rainView != null) {
                windowManager.removeView(rainView);
            }
            isAdd = false;
        }
    }

}
