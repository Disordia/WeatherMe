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

/**
 * Created by Disordia profaneden on 2015-04-09.
 */
public class RainView extends View {

    List<Rain> rains = new ArrayList<Rain>();
    Paint paint, paint2, paint3;
    private int level=1;

    public void setLevel(int level) {
        this.level = level;
    }

    public RainView(Context context, AttributeSet attrs,int level) {
        super(context, attrs);
        setLevel(level);
        /**
         *  init all pens
         */
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);

        paint.setDither(true);
        paint.setStrokeWidth(1.5f);
        paint2 = new Paint();
        paint2.setColor(Color.WHITE);
        paint2.setStrokeJoin(Paint.Join.ROUND);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setStyle(Paint.Style.STROKE);

        paint2.setDither(true);
        paint2.setStrokeWidth(1f);
        paint3 = new Paint();
        paint3.setColor(Color.WHITE);
        paint3.setStrokeJoin(Paint.Join.ROUND);
        paint3.setStrokeCap(Paint.Cap.ROUND);
        paint3.setStyle(Paint.Style.STROKE);

        paint3.setDither(true);
        paint3.setStrokeWidth(0.5f);
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

    public void InitRain() {
        random = new Random();
        for (int i = 0; i < level*2+4; i++) {
            Rain rain = new Rain();
            rain.x = random.nextInt(200);
            rain.y = random.nextInt(240);
            rain.radius = random.nextInt(60) + 5;
            rain.maxradius=random.nextInt(60)+36-level*5;
            if(rain.x+rain.y>460){
                rain.x=200-rain.x;
                rain.y=240-rain.y;
            }
            rains.add(rain);
        }
    }

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(final Canvas canvas) {

        for (Rain rain : rains) {
            rain.radius +=level/2+2;
            if (rain.radius < 15) {
                canvas.drawCircle(rain.x, rain.y, rain.radius, paint);
            } else if (rain.radius < 25) {
                canvas.drawCircle(rain.x, rain.y, rain.radius, paint2);
                canvas.drawCircle(rain.x, rain.y, rain.radius - 15, paint);
            } else if (rain.radius < 35) {
                canvas.drawCircle(rain.x, rain.y, rain.radius, paint3);
                canvas.drawCircle(rain.x, rain.y, rain.radius - 15, paint2);
                canvas.drawCircle(rain.x, rain.y, rain.radius - 25, paint2);
            } else if (rain.radius < 45) {
                canvas.drawCircle(rain.x, rain.y, rain.radius - 15, paint3);
                canvas.drawCircle(rain.x, rain.y, rain.radius - 25, paint2);
            } else if (rain.radius < 55) {
                canvas.drawCircle(rain.x, rain.y, rain.radius - 25, paint3);
            }//end if

            if (rain.radius > rain.maxradius) {
                rain.x = random.nextInt(200);
                rain.y = random.nextInt(240);
                rain.radius = random.nextInt(8);
                rain.maxradius=random.nextInt(25)+56-level*5;
                if(rain.x+rain.y>460){
                    rain.x=200-rain.x;
                    rain.y=240-rain.y;
                }
            }//end if
        }//end for
    }//end onDraw
}
