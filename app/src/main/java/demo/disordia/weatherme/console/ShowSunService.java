package demo.disordia.weatherme.console;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Gravity;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import demo.disordia.weatherme.console.weatherview.SunView;
import demo.disordia.weatherme.optimization.GlobalApplication;
import demo.disordia.weatherme.setting.Settings;
import demo.disordia.weatherme.util.HomeJudger;
import demo.disordia.weatherme.util.LogUtil;
import demo.disordia.weatherme.util.WindowManagerUtil;

/**
 * Project: CanvasPrac
 *
 * @author: Disrodia
 * create time: 2015-04-07.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.canvasprac
 * Descibe:
 */
public class ShowSunService extends Service {

    WindowManager windowManager;
    WindowManager.LayoutParams layoutParams;
    SunView sunView;
    private boolean showOnlyDesktop;
    private boolean showWeather = true;

    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            handler.sendMessage(message);
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (showWeather) {
                if (HomeJudger.isHome() || showOnlyDesktop == false) {
                    AddView();
                    sunView.invalidate();
                } else {
                    RemoveView();
                }
                super.handleMessage(msg);
            } else {
                RemoveView();
            }
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = GlobalApplication.getContext().getSharedPreferences("weatherLevel", Context.MODE_PRIVATE);
        level = sharedPreferences.getInt("sun", 0);
        LogUtil.d("ShowSunService", "The sun level is:" + level);
        ShowOnScreen();
        sunView = new SunView(this, null, level);
        AddView();
        //设置计时
        timer.schedule(timerTask, 80, 80);
    }

    @Override
    public void onDestroy() {
        RemoveView();
        showWeather = false;
        super.onDestroy();
    }

    public void ShowOnScreen() {
        Settings settings = Settings.getInstance();
        showOnlyDesktop = settings.isShowOnlyDesktop();
        windowManager = (WindowManager) getSystemService(getApplicationContext().WINDOW_SERVICE);
        //获取布局参数
        layoutParams =  WindowManagerUtil.getLayoutParams();

    }

    private int level;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showWeather=true;
        return super.onStartCommand(intent, flags, startId);
    }


    private boolean isAdd = false;

    private void AddView() {
        if (!isAdd) {
            windowManager.addView(sunView, layoutParams);
            isAdd = true;
        }
    }

    private void RemoveView() {
        if (isAdd) {
            if (sunView != null) {
                windowManager.removeView(sunView);
            }
            isAdd = false;
        }
    }
}
