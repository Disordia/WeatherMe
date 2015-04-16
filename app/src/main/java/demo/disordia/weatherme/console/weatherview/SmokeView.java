package demo.disordia.weatherme.console.weatherview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
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
public class SmokeView extends View {

    List<Smoke> smokes = new ArrayList<Smoke>();
    private int level;

    public void setLevel(int level) {
        this.level = level;
    }

    public SmokeView(Context context, AttributeSet attrs,int level) {
        super(context, attrs);
        setLevel(level);
        yunwubm = BitmapFactory.decodeResource(getResources(), R.drawable.yunwu2);
        yunwubm3 = BitmapFactory.decodeResource(getResources(), R.drawable.yunwu3);
        yunwubm4 = BitmapFactory.decodeResource(getResources(), R.drawable.yunwu4);
        yunwubm5 = BitmapFactory.decodeResource(getResources(), R.drawable.yunwu5);
        InitSmoke();
        paint = new Paint();
        Settings settings=Settings.getInstance();
        //是否抗锯齿:
        if (settings.isAntiAli()){
            paint.setAntiAlias(true);
        }

    }

    private void InitSmoke() {
        LogUtil.d("SmokeView","The current level is :"+level);
        random = new Random();
        for (int i = 0; i < level*2+2; i++) {
            Smoke smoke = new Smoke();
            smoke.x = random.nextInt(300) - 100;
            smoke.y = random.nextInt(100) - 20;
            smoke.alpha = random.nextInt(15);
            smoke.speed = random.nextInt(2) + 2;
            smoke.maxX = random.nextInt(300) + smoke.x;
            smoke.middle = (smoke.x + smoke.maxX) / 2;
            smoke.size = random.nextFloat() / 2 + 0.5f;
            smoke.type=random.nextInt(4);
            smokes.add(smoke);
        }
    }

    private Paint paint;
    private Random random;
    private Bitmap yunwubm,yunwubm3,yunwubm4,yunwubm5;
    private Matrix matrix;

    @Override
    protected void onDraw(Canvas canvas) {


        for (Smoke smoke : smokes) {

            if (smoke.x < smoke.middle) {
                smoke.alpha += 6+smoke.speed;
            } else {
                smoke.alpha -= 1+smoke.speed;
            }

            if (smoke.alpha > 250) {
                smoke.alpha = 250;
            }
            if (smoke.alpha < 0) {
                smoke.alpha = 0;
            }

            paint.setAlpha(smoke.alpha);
            smoke.x += smoke.speed;
            matrix = new Matrix();
            matrix.setScale(smoke.size, smoke.size, smoke.x, smoke.y);

            switch (smoke.type) {
                case 0:   canvas.drawBitmap(yunwubm, matrix, paint);break;
                case 1:   canvas.drawBitmap(yunwubm3, matrix, paint);break;
                case 2:   canvas.drawBitmap(yunwubm4, matrix, paint);break;
                case 3:   canvas.drawBitmap(yunwubm5, matrix, paint);break;
                default:
                    LogUtil.d("SmokeView","There can be another :"+smoke.type);
            }

            if (smoke.x > smoke.maxX&&smoke.alpha<=0) {
                smoke.x = random.nextInt(300) - 100;
                smoke.y = random.nextInt(100) - 20;
                smoke.alpha = random.nextInt(15);
                smoke.speed = random.nextInt(2) + 2;
                smoke.maxX = random.nextInt(300) + smoke.x;
                smoke.middle = (smoke.x + smoke.maxX) / 2;
                smoke.size = random.nextFloat() / 2 + 0.5f;
                smoke.type=random.nextInt(4);
            }
        }//end for each:
    }
}
