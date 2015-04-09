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

    public SunView(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitSun();
        paint = new Paint();
        paint.setAntiAlias(true);
        random = new Random();
        alpha = random.nextInt(20) + 215;
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
                LogUtil.d("SunView", "The boolean is:" + random.nextBoolean());
                state = -3;
                cycle=1;
            } else {
                state = 3;
                cycle=1;
            }
        }
        if (alpha > 250) {
            alpha = 250;
        }
        if (alpha <185) {
            alpha = 185;
        }
        paint.setAlpha(alpha);


        canvas.drawBitmap(sunbm,0,0,paint);
        if (alpha>230){
            paint.setAlpha(alpha-40);
            canvas.drawBitmap(yunguang,0,0,paint);
        }else if (alpha>225){
            paint.setAlpha(alpha-45);
            canvas.drawBitmap(yunguang,0,0,paint);
        }else if (alpha>215){
            paint.setAlpha(alpha-55);
            canvas.drawBitmap(yunguang,0,0,paint);
        }else if (alpha>205){
            paint.setAlpha(alpha-70);
            canvas.drawBitmap(yunguang,0,0,paint);
        }else if (alpha>195){
            paint.setAlpha(alpha-100);
            canvas.drawBitmap(yunguang,0,0,paint);
        }
    }//end onDraw


}
