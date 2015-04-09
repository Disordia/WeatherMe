package demo.disordia.weatherme.console;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.Gravity;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import demo.disordia.weatherme.console.weatherview.RainView;
import demo.disordia.weatherme.util.LogUtil;

/**
 * Created by Disordia profaneden on 2015-04-09.
 */
public class ShowRainService extends Service {
    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private RainView rainView;


    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Message message = handler.obtainMessage();
//            message.what = 1;
            handler.sendMessage(message);
        }
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            //要做的事情
            rainView.invalidate();
            super.handleMessage(msg);
        }
    };




    public void ShowOnScreen() {
        windowManager = (WindowManager) getSystemService(getApplicationContext().WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.LEFT | Gravity.LEFT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.type = WindowManager.LayoutParams.FIRST_SYSTEM_WINDOW + 2;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.alpha=10;
        LogUtil.d("ShowOnSErvice", "The flag is:" + layoutParams.flags);
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                WindowManager.LayoutParams. FLAG_LAYOUT_IN_SCREEN ;

    }


    @Override
    public void onCreate() {
        super.onCreate();
        ShowOnScreen();
        rainView = new RainView(this, null);
        windowManager.addView(rainView, layoutParams);
        timer.schedule(timerTask, 40, 100);
    }

    @Override
    public void onDestroy() {
        windowManager.removeView(rainView);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
