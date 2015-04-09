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

import demo.disordia.weatherme.console.weatherview.SunView;

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

    Timer timer=new Timer();
    TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {
            Message message=new Message();
            handler.sendMessage(message);
        }
    };

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            sunView.invalidate();
            super.handleMessage(msg);
        }
    };


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ShowOnScreen();

        sunView=new SunView(this,null);
        windowManager.addView(sunView,layoutParams);
        timer.schedule(timerTask,80,80);
    }

    @Override
    public void onDestroy() {
        windowManager.removeView(sunView);

        super.onDestroy();
    }

    public void ShowOnScreen() {
        windowManager = (WindowManager) getSystemService(getApplicationContext().WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.LEFT | Gravity.LEFT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.type = WindowManager.LayoutParams.FIRST_SYSTEM_WINDOW + 2;
        layoutParams.format = PixelFormat.RGBA_8888;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;


    }

}
