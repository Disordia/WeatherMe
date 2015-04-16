package demo.disordia.weatherme.console.weatherview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.setting.Settings;
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
public class SunView extends View {

    private int level;

    public void setLevel(int level) {
        this.level = level;
    }

    public SunView(Context context, AttributeSet attrs,int levelx) {
        super(context, attrs);
        setLevel(levelx);
        LogUtil.d("SunView","The level is:"+levelx);
        InitSun();
        paint = new Paint();
        random = new Random();
        alpha = random.nextInt(20) + 175+level*10;
        Settings settings=Settings.getInstance();
        if (settings.isAntiAli()){
            paint.setAntiAlias(true);
        }

    }


    private void InitSun() {
        sunbm = BitmapFactory.decodeResource(getResources(), R.drawable.sunex);
        yunguang=BitmapFactory.decodeResource(getResources(),R.drawable.yunguang);

    }

    Bitmap sunbm;
    Bitmap yunguang;
    Paint paint;
    private Random random;
    private static int cycle = 1;
    private static int alpha;
    private static int state = 3;
    @Override
    protected void onDraw(Canvas canvas) {
        cycle++;
        alpha += state;
        if (cycle % 3 == 0) {
            if (random.nextBoolean()) {
//                LogUtil.d("SunView", "The boolean is:" + random.nextBoolean());
                state = -3;
                cycle=1;
            } else {
                state = 3;
                cycle=1;
            }
        }
        if (alpha > 170+level*10) {
            alpha = 170+level*10;
        }
        if (alpha <105+level*10) {
            alpha = 105+level*10;
        }

        paint.setAlpha(alpha+level*10);


        if (level==1){
            canvas.drawBitmap(sunbm, 0, 0, paint);
            if (alpha > 190 + level * 10) {
                paint.setAlpha(alpha - 40 + level * 10);
            } else if (alpha > 185 + level * 10) {
                paint.setAlpha(alpha - 45 + level * 10);
            } else if (alpha > 175 + level * 10) {
                paint.setAlpha(alpha - 55 + level * 10);
            } else if (alpha > 165 + level * 10) {
                paint.setAlpha(alpha - 70 + level * 10);
            } else if (alpha > 155 + level * 10) {
                paint.setAlpha(alpha - 100 + level * 10);
            }
        }else {
            canvas.drawBitmap(sunbm, 0, 0, paint);
            if (alpha > 190 + level * 10) {
                paint.setAlpha(alpha - 40 + level * 10);
                canvas.drawBitmap(yunguang, 0, 0, paint);
            } else if (alpha > 185 + level * 10) {
                paint.setAlpha(alpha - 45 + level * 10);
                canvas.drawBitmap(yunguang, 0, 0, paint);
            } else if (alpha > 175 + level * 10) {
                paint.setAlpha(alpha - 55 + level * 10);
                canvas.drawBitmap(yunguang, 0, 0, paint);
            } else if (alpha > 165 + level * 10) {
                paint.setAlpha(alpha - 70 + level * 10);
                canvas.drawBitmap(yunguang, 0, 0, paint);
            } else if (alpha > 155 + level * 10) {
                paint.setAlpha(alpha - 100 + level * 10);
                canvas.drawBitmap(yunguang, 0, 0, paint);
            }
        }
    }//end onDraw


}
