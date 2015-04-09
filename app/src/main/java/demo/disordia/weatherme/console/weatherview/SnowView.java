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
 * create time: 2015-04-06.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.canvasprac
 * Descibe:
 */
public class SnowView extends View {

    List<Snow> snows=new ArrayList<Snow>();

    public SnowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        InitSnow();
    }

    private void InitSnow() {
        random=new Random();
        for (int i=0;i<8;i++){
            Snow isnow=new Snow();
            isnow.x=random.nextInt(500)-50;
            isnow.y=random.nextInt(50)+10;
            isnow.alpha=random.nextInt(20)+10;
            isnow.size=random.nextFloat()/2+0.2f;
            isnow.maxY=random.nextInt(300)+100+isnow.y;
            isnow.middle=(isnow.maxY+isnow.y)/2;
            snows.add(isnow);
        }

    }

    private Random random;
    private Bitmap snow;
    private Paint paint;
    private Matrix matrix;
    @Override
    protected void onDraw(Canvas canvas) {
        snow= BitmapFactory.decodeResource(getResources(), R.drawable.xuehua2);
        matrix=new Matrix();

        for (Snow isnow:snows){
            if (isnow.y<isnow.middle){
                isnow.alpha+=5;
            }else {
                isnow.alpha-=5;
            }
            if(isnow.alpha>255){
                isnow.alpha=255;
            }
            if (isnow.alpha<0){
                isnow.alpha=0;
            }

            paint.setAlpha(isnow.alpha);
            isnow.y+=3;
            matrix.setScale(isnow.size,isnow.size,isnow.x,isnow.y);
            canvas.drawBitmap(snow,matrix,paint);
            if (isnow.y>isnow.maxY){
                isnow.x=random.nextInt(500)-50;
                isnow.y=random.nextInt(50)+10;
                isnow.alpha=random.nextInt(20)+10;
                isnow.size=random.nextFloat()/2+0.2f;
                isnow.maxY=random.nextInt(300)+100+isnow.y;
                isnow.middle=(isnow.maxY+isnow.y)/2;

            }
        }
    }//end onDraw

    

}
