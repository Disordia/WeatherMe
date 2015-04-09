package demo.disordia.weatherme.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.util.LogUtil;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-31.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.ui
 * Descibe:此类用于显示卫星菜单:
 */
public class ArcMenu extends ViewGroup implements View.OnClickListener {

    //用于定义位置的枚举
    public enum Position {
        LEFT_TOP,
        LEFT_BOTTOM,
        RIGHT_TOP,
        RIGHT_BOTTOM,
        CENTER;

    }

    //用于定义位置的常数:
    public final int LEFT_TOP = 0;

    public final int LEFT_BOTTOM = 1;
    public final int RIGHT_TOP = 2;
    public final int RIGHT_BOTTOM = 3;
    public final int CENTER = 4;

    //用于定义菜单状态的枚举:
    public enum State {
        OPEN, CLOSE;
    }


    //侦听器:
    public interface OnMenuItemClickListener {
        void onClick(View childView, int pos);
    }

    private OnMenuItemClickListener mMenuItemClickListener;

    public void setOnMenuItemClickListener(OnMenuItemClickListener mMenuItemClickListener) {
        this.mMenuItemClickListener = mMenuItemClickListener;
    }
    //菜单半径
    private int mRadius;
    public int count;
    //状态：
    private State currentState = State.CLOSE;
    //位置:
    private Position mPosition = Position.RIGHT_BOTTOM;
    //用于存放主按钮:
    private View mcButton;

    @Override
    public void onClick(View v) {
        if (mcButton == null) {
            mcButton = getChildAt(0);
        }
        View img = ((RelativeLayout) mcButton).getChildAt(0);
        rotateButton(img, 0f, 360f, 300);
        toggleMenu(300);
    }

    /**
     * 子控件的动画
     *
     * @param duration
     */
    public void toggleMenu(int duration) {

        count = getChildCount();
        for (int i = 1; i < count; i++) {
            final View childView = getChildAt(i);
            childView.setVisibility(VISIBLE);
            int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * (i - 1)));
            int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * (i - 1)));

            //如果位置在中间的话:
            if (mPosition == Position.CENTER) {
                cl = (int) (mRadius * Math.sin(Math.PI * 2 / (count - 1) * (i - 1)));
                ct = (int) (mRadius * Math.cos(Math.PI * 2 / (count - 1) * (i - 1)));
            }


            int xFlag = 1;
            int yFlag = 1;

            //如果在左边:
            if (mPosition == Position.LEFT_BOTTOM || mPosition == Position.LEFT_TOP) {
                xFlag = -1;
            }
            //如果在上部:
            if (mPosition == Position.RIGHT_TOP || mPosition == Position.LEFT_TOP) {
                yFlag = -1;
            }
            if (mPosition == Position.CENTER) {
                xFlag = -1;
                yFlag = -1;
            }

            AnimationSet animationSet = new AnimationSet(true);

            Animation tranAnimi = null;

            if (currentState == State.CLOSE) {
                tranAnimi = new TranslateAnimation(xFlag * cl, 0, yFlag * ct, 0);
                childView.setClickable(true);
                childView.setFocusable(true);
            } else {
                tranAnimi = new TranslateAnimation(0, xFlag * cl, 0, yFlag * ct);
                childView.setClickable(false);
                childView.setFocusable(false);
            }

            tranAnimi.setDuration(duration);

            tranAnimi.setStartOffset(i * 100 / count);

            tranAnimi.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (currentState == State.CLOSE) {
                        childView.setVisibility(GONE);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });


            RotateAnimation rotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);


            //添加动画：
            animationSet.addAnimation(rotateAnimation);
            animationSet.addAnimation(tranAnimi);


            childView.startAnimation(animationSet);

        //为菜单添加点击动画:
            //设置事件侦听器:
            final int pos=i;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMenuItemClickListener!=null){
                        mMenuItemClickListener.onClick(childView,pos);
                    }
                    menuItemAnimi(pos);
                    changeStatus();
                }
            });




        }
        changeStatus();

    }

    /**
     * 切换状态
     */
    private void changeStatus() {
        currentState = (currentState == State.CLOSE ? State.OPEN : State.CLOSE);
    }

    /**
     * 主按钮的动画
     */
    private void rotateButton(View view, float start, float end, int duration) {

        RotateAnimation rotateAnimation = new RotateAnimation(start, end,
                Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(duration);
        rotateAnimation.setFillAfter(true);
        view.startAnimation(rotateAnimation);

    }

    public ArcMenu(Context context) {
        this(context, null);
    }

    public ArcMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ArcMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义参数:
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ArcMenu, defStyleAttr, 0);
        //获取位置:
        int pos = a.getInt(R.styleable.ArcMenu_position, 3);
        //判断位置:
        switch (pos) {
            case LEFT_TOP:
                mPosition = Position.LEFT_TOP;
                break;
            case LEFT_BOTTOM:
                mPosition = Position.LEFT_BOTTOM;
                break;
            case RIGHT_TOP:
                mPosition = Position.RIGHT_TOP;
                break;
            case RIGHT_BOTTOM:
                mPosition = Position.RIGHT_BOTTOM;
                break;
            case CENTER:
                mPosition = Position.CENTER;
                break;
        }
        LogUtil.d("ArcMenu", "The Position is :" + pos);

        mRadius = (int) a.getDimension(R.styleable.ArcMenu_radius,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        100,
                        getResources().getDisplayMetrics()));


//        LogUtil.d("ArcMenu","the count is :"+count);
        //回收资源!!!
        a.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        count = getChildCount();
        LogUtil.d("ArcMenu", "the count is :" + count);
        for (int i = 0; i < count; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            //绘制主按钮:
            layoutCButton();
            count = getChildCount();

            for (int i = 1; i < count; i++) {
                View childView = getChildAt(i);
                //设置子菜单不可见:
                childView.setVisibility(GONE);

                int cl = (int) (mRadius * Math.sin(Math.PI / 2 / (count - 2) * (i - 1)));
                int ct = (int) (mRadius * Math.cos(Math.PI / 2 / (count - 2) * (i - 1)));
                //计算子控件的大小:
                int cwidth = childView.getMeasuredWidth();
                int cheight = childView.getMeasuredHeight();


                //如果位置在中间的话:
                if (mPosition == Position.CENTER) {
                    cl = (int) ((getMeasuredWidth() - cwidth) / 2 + (mRadius * Math.sin(Math.PI * 2 / (count - 1) * (i - 1))));
                    ct = (int) ((getMeasuredHeight() - cheight) / 2 + (mRadius * Math.cos(Math.PI * 2 / (count - 1) * (i - 1))));
                }
                //如果在右边:
                if (mPosition == Position.RIGHT_TOP || mPosition == Position.RIGHT_BOTTOM) {
                    cl = getMeasuredWidth() - cwidth - cl;

                }
                //如果在底部:
                if (mPosition == Position.RIGHT_BOTTOM || mPosition == Position.LEFT_BOTTOM) {
                    ct = getMeasuredHeight() - cheight - ct;
                }

                childView.layout(cl, ct, cl + cwidth, ct + cheight);
            }

        }//end if
    }


    /**
     * 绘制主按钮程序:
     */
    private void layoutCButton() {

        mcButton = getChildAt(0);
        mcButton.setOnClickListener(this);
        //定义画面的四边:
        int l = 0;
        int t = 0;
        int width = mcButton.getMeasuredWidth();
        int height = mcButton.getMeasuredHeight();

        LogUtil.d("ArcMenu", "The mcButton start to draw");
        switch (mPosition) {
            case LEFT_TOP:
                l = 0;
                t = 0;
                break;
            case LEFT_BOTTOM:
                l = 0;
                t = getMeasuredHeight() - height;
                break;
            case RIGHT_TOP:
                l = getMeasuredWidth() - width;
                t = 0;
                break;
            case RIGHT_BOTTOM:
                l = getMeasuredWidth() - width;
                t = getMeasuredHeight() - height;
                break;
            case CENTER:
                l = (getMeasuredWidth() - width) / 2;
                t = (getMeasuredHeight() - height) / 2;
                break;
        }
        LogUtil.d("ArcMenu", "The mcButton on layout");
        mcButton.layout(l, t, l + width, t + height);
    }//end function

    /**
     * 菜单点击动画
     */

    private void menuItemAnimi(int pos) {
        for (int i=1;i<getChildCount();i++){
            View childView=getChildAt(i);

            if(i==pos){
                childView.startAnimation(ScaleBigAnimi(300));
            }else {
                childView.startAnimation(ScaleSmallAnimi(300));
            }

            childView.setClickable(false);
            childView.setFocusable(false);
        }
    }

    private Animation ScaleSmallAnimi(int duration) {
        AnimationSet animationSet=new AnimationSet(true);

        ScaleAnimation scaleAnimation=new ScaleAnimation(1.0f,0.7f,1.0f,0.7f,Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);

        AlphaAnimation alphaAnimation=new AlphaAnimation(1f,0.0f);

        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }

    private Animation ScaleBigAnimi(int duration) {
        AnimationSet animationSet=new AnimationSet(true);

        ScaleAnimation scaleAnimation=new ScaleAnimation(1.0f,2.0f,1.0f,2.0f,Animation.RELATIVE_TO_SELF,
                0.5f,Animation.RELATIVE_TO_SELF,0.5f);

        AlphaAnimation alphaAnimation=new AlphaAnimation(1f,0.0f);

        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(duration);
        animationSet.setFillAfter(true);
        return animationSet;
    }



}
