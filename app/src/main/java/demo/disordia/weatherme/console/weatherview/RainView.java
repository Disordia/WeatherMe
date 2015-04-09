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

/**
 * Created by Disordia profaneden on 2015-04-09.
 */
public class RainView extends View {

    List<Rain> rains = new ArrayList<Rain>();
    Paint paint, paint2, paint3;


    public RainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        /**
         * 初始化所有的画笔
         */
        paint = new Paint(); //设置一个笔刷大小是3的黄色的画笔
        paint.setColor(Color.WHITE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(1.5f);
        paint2 = new Paint(); //设置一个笔刷大小是3的黄色的画笔
        paint2.setColor(Color.WHITE);
        paint2.setStrokeJoin(Paint.Join.ROUND);
        paint2.setStrokeCap(Paint.Cap.ROUND);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setAntiAlias(true);
        paint2.setDither(true);
        paint2.setStrokeWidth(1f);
        paint3 = new Paint(); //设置一个笔刷大小是3的黄色的画笔
        paint3.setColor(Color.WHITE);
        paint3.setStrokeJoin(Paint.Join.ROUND);
        paint3.setStrokeCap(Paint.Cap.ROUND);
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setAntiAlias(true);
        paint3.setDither(true);
        paint3.setStrokeWidth(0.5f);

        //初始化雨容器:
        InitRain();
    }

    private Random random;

    /**
     * 对雨容器进行初始化:
     */
    public void InitRain() {
        random = new Random();
        for (int i = 0; i < 10; i++) {
            Rain rain = new Rain();
            rain.x = random.nextInt(300);
            rain.y = random.nextInt(300);
            rain.radius = random.nextInt(60) + 5;
            rain.maxradius=random.nextInt(65)+36;
            if(rain.x+rain.y>350){
                rain.x=300-rain.x;
                rain.y=300-rain.y;
            }
            rains.add(rain);
        }
    }

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(final Canvas canvas) {
/**
 * 遍历每个雨点来绘制:
 */
        for (Rain rain : rains) {
            rain.radius += 2;
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

/**
 * 这里测试雨是否要重新初始化
 */
            if (rain.radius > rain.maxradius) {
                rain.x = random.nextInt(300);
                rain.y = random.nextInt(300);
                rain.radius = random.nextInt(10);
                rain.maxradius = random.nextInt(35) + 46;
                if (rain.x + rain.y > 350) {
                    rain.x = 300 - rain.x;
                    rain.y = 300 - rain.y;
                }

            }//end if
        }//end for
    }//end onDraw
}
