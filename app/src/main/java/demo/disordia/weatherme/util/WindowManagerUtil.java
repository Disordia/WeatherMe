package demo.disordia.weatherme.util;

import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;

import demo.disordia.weatherme.setting.Settings;

/**
 * Created by Disordia profaneden on 2015-04-12.
 */
public class WindowManagerUtil {
    private static WindowManager.LayoutParams layoutParams;

    public static WindowManager.LayoutParams getLayoutParams(){
        Settings settings=Settings.getInstance();
        layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.LEFT | Gravity.LEFT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;

        layoutParams.format = PixelFormat.RGBA_8888;
        //ps:这里的alpha：当alpha=0是，是不会显示哒~~
        layoutParams.alpha = 255 - settings.getAlpha();
        LogUtil.d("ShowOnSErvice", "The flag is:" + layoutParams.flags);
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        switch (settings.getLayer()){
            case 0:
                layoutParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                break;
            case 1:
                layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
                break;
            case 2:
                layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
                break;
            case 3:
                layoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
                break;
            case 4:
                layoutParams.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;
                break;
            default:break;
        }

        return layoutParams;
    }

}
