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

import demo.disordia.weatherme.console.weatherview.SmokeView;
import demo.disordia.weatherme.util.LogUtil;

/**
 * Project: CanvasPrac
 *
 * @author: Disrodia
 * create time: 2015-04-07.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.canvasprac
 * Descibe:
 */
public class ShowSmokeService extends Service {

    WindowManager windowManager;
    WindowManager.LayoutParams layoutParams;
    SmokeView smokeView;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Timer timer=new Timer();
    TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {
            Message message=handler.obtainMessage();
            handler.sendMessage(message);
        }
    };

            Handler handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    smokeView.invalidate();
                    super.handleMessage(msg);
                }
            };

    @Override
    public void onCreate() {
        super.onCreate();
        ShowOnScreen();
        LogUtil.d("SmokeService","onCreate");
        smokeView=new SmokeView(this,null);
        windowManager.addView(smokeView,layoutParams);
        timer.schedule(timerTask,80,80);
    }


    public void ShowOnScreen() {
        windowManager = (WindowManager) getSystemService(getApplicationContext().WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.LEFT | Gravity.LEFT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.type = WindowManager.LayoutParams.FIRST_SYSTEM_WINDOW + 2;
        layoutParams.format = PixelFormat.RGBA_8888;
//        layoutParams.alpha=10;
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|
                WindowManager.LayoutParams. FLAG_LAYOUT_IN_SCREEN ;
    }

    @Override
    public void onDestroy() {
        windowManager.removeView(smokeView);
        super.onDestroy();
    }
}
