package demo.disordia.weatherme.console;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.setting.Settings;
import demo.disordia.weatherme.util.HomeJudger;
import demo.disordia.weatherme.util.LogUtil;
import demo.disordia.weatherme.util.WindowManagerUtil;

/**
 * Created by Disordia profaneden on 2015-04-12.
 */
public class ShowFrozenService extends Service {
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private ImageView frozenView;
    private boolean showOnlyDesktop;
    private boolean showWeather=true;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showWeather=true;
        return super.onStartCommand(intent, flags, startId);
    }

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
                    super.handleMessage(msg);
                } else {
                    RemoveView();
                    super.handleMessage(msg);
                }
            }else {
                RemoveView();
            }
        }
    };



    public void ShowOnScreen() {
        Settings settings=Settings.getInstance();
        showOnlyDesktop=settings.isShowOnlyDesktop();
        windowManager = (WindowManager) getSystemService(getApplicationContext().WINDOW_SERVICE);
        layoutParams =  WindowManagerUtil.getLayoutParams();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        frozenView=new ImageView(this);
        frozenView.setImageResource(R.drawable.frozen1);
        AddView();
        timer.schedule(timerTask, 1000, 100);
    }

    @Override
    public void onDestroy() {
        RemoveView();
        showWeather=false;
        super.onDestroy();
    }


    private boolean isAdd = false;

    private void AddView() {
        if (!isAdd) {
            windowManager.addView(frozenView, layoutParams);
            isAdd = true;
        }
    }

    private void RemoveView() {
        if (isAdd) {
            if (frozenView != null) {
                windowManager.removeView(frozenView);
            }
            isAdd = false;
        }
    }
}
