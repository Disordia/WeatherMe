package demo.disordia.weatherme.console.weatherview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import demo.disordia.weatherme.setting.Settings;
import demo.disordia.weatherme.util.DpConverManager;
import demo.disordia.weatherme.util.LogUtil;

/**
 * Created by Disordia profaneden on 2015-04-09.
 */
public class RainView extends View {

    List<Rain> rains = new ArrayList<Rain>();
    Paint paint, paint2, paint3;
    private int level=1;
    private float dp1;

    public void setLevel(int level) {
        this.level = level;
    }

    public RainView(Context context, AttributeSet attrs,int level) {
        super(context, attrs);
        setLevel(level);
        dp1= DpConverManager.getDp()/2;
        LogUtil.d("RainView", "one dip is: " + dp1);
        /**
         *  init all pens
         */
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);

        paint.setDither(true);
        paint.setStrokeWidth(1.2f);
        paint2 = new Paint();
        paint2.setColor(Color.WHITE);
        paint2.setStrokeJoin(Paint.Join.ROUND);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setStyle(Paint.Style.STROKE);

        paint2.setDither(true);
        paint2.setStrokeWidth(0.8f);
        paint3 = new Paint();
        paint3.setColor(Color.WHITE);
        paint3.setStrokeJoin(Paint.Join.ROUND);
        paint3.setStrokeCap(Paint.Cap.ROUND);
        paint3.setStyle(Paint.Style.STROKE);

        paint3.setDither(true);
        paint3.setStrokeWidth(0.4f);
        /* 是否抗锯齿: */
        Settings settings=Settings.getInstance();
        if (settings.isAntiAli()){
            paint.setAntiAlias(true);
            paint2.setAntiAlias(true);
            paint3.setAntiAlias(true);
        }


        InitRain();
    }

    private Random random;
    private int scale;
    public void InitRain() {
        scale=60-level*5;
        random = new Random();
        for (int i = 0; i < level*1.5+4; i++) {
            Rain rain = new Rain();
            rain.x = random.nextInt((int) (200*dp1));
            rain.y = random.nextInt((int) (340*dp1));
            rain.radius = random.nextInt((int) (scale*dp1)) + 5;
            rain.maxradius=rain.radius+random.nextInt((int) (scale/2*dp1));
            if((rain.x+rain.y)>350*dp1){
                rain.x= (int) (200*dp1-rain.x);
                rain.y= (int) (240*dp1-rain.y);
            }
            rains.add(rain);
        }
    }

    private int tidu;
    /**
     * @param canvas
     */
    @Override
    protected void onDraw(final Canvas canvas) {

        for (Rain rain : rains) {
            rain.radius +=(level/2+2)*dp1;
            tidu= (int) (rain.maxradius/6*dp1+4*dp1);
            if (rain.radius < tidu) {
                canvas.drawCircle(rain.x, rain.y, rain.radius, paint);
            } else if (rain.radius < tidu*2) {
                canvas.drawCircle(rain.x, rain.y, rain.radius, paint2);
                canvas.drawCircle(rain.x, rain.y, rain.radius - tidu, paint);
            } else if (rain.radius < tidu*3) {
                canvas.drawCircle(rain.x, rain.y, rain.radius, paint3);
                canvas.drawCircle(rain.x, rain.y, rain.radius - tidu, paint2);
                canvas.drawCircle(rain.x, rain.y, rain.radius - tidu*2, paint2);
            } else if (rain.radius < tidu*4) {
                canvas.drawCircle(rain.x, rain.y, rain.radius - tidu, paint3);
                canvas.drawCircle(rain.x, rain.y, rain.radius - tidu*2, paint2);
            } else if (rain.radius < tidu*5) {
                canvas.drawCircle(rain.x, rain.y, rain.radius - tidu*2, paint3);
            }//end if

            if (rain.radius > rain.maxradius) {
                rain.x = (int) (random.nextInt((int) (200*dp1))-level*5*dp1);
                rain.y = (int) (random.nextInt((int) (240*dp1))-level*5*dp1);
                rain.radius = random.nextInt((int) (8*dp1));
                rain.maxradius= (int) (random.nextInt((int) (25*dp1))+scale-25*dp1);
//                LogUtil.d("ShowRainService","The adjusted position is :"+rain.x+":"+rain.y);
                if((rain.x+rain.y)>250*dp1){
                    rain.x= (int) (200*dp1-rain.x);
                    rain.y= (int) (240*dp1-rain.y);
                    //LogUtil.d("ShowRainService","The adjusted position is :"+rain.x+":"+rain.y);
                }
            }//end if
        }//end for
    }//end onDraw
}
