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

    public SmokeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        yunwubm = BitmapFactory.decodeResource(getResources(), R.drawable.yunwu2);
        InitSmoke();
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    private void InitSmoke() {
        random = new Random();
        for (int i = 0; i < 5; i++) {
            Smoke smoke = new Smoke();
            smoke.x = random.nextInt(300) - 100;
            smoke.y = random.nextInt(100) - 20;
            smoke.alpha = random.nextInt(15);
            smoke.speed = random.nextInt(3) + 2;
            smoke.maxX = random.nextInt(300) + smoke.x;
            smoke.middle = (smoke.x + smoke.maxX) / 2;
            smoke.size = random.nextFloat() / 2 + 0.5f;
            smokes.add(smoke);
        }


    }

    private Paint paint;
    private Random random;
    private Bitmap yunwubm;
    private Matrix matrix;

    @Override
    protected void onDraw(Canvas canvas) {


        for (Smoke smoke : smokes) {

            if (smoke.x < smoke.middle) {
                smoke.alpha += 8+smoke.speed;
            } else {
                smoke.alpha -= 2+smoke.speed;
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
            canvas.drawBitmap(yunwubm, matrix, paint);
            if (smoke.x > smoke.maxX&&smoke.alpha<=0) {
                smoke.x = random.nextInt(300) - 100;
                smoke.y = random.nextInt(100) - 20;
                smoke.alpha = random.nextInt(15);
                smoke.speed = random.nextInt(3) + 2;
                smoke.maxX = random.nextInt(300) + smoke.x;
                smoke.middle = (smoke.x + smoke.maxX) / 2;
                smoke.size = random.nextFloat() / 2 + 0.5f;
            }
        }//end for each:
    }
}
