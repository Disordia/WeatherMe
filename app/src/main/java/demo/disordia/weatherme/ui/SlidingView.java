package demo.disordia.weatherme.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.activity.AboutActivity;
import demo.disordia.weatherme.activity.HelpActivity;
import demo.disordia.weatherme.activity.SettingsActivity;
import demo.disordia.weatherme.activity.WeatherActivity;
import demo.disordia.weatherme.optimization.GlobalApplication;
import demo.disordia.weatherme.util.LogUtil;

/**
 * Created by Disordia profaneden on 2015-04-16.
 */
public class SlidingView extends HorizontalScrollView {
    //屏幕宽度:
    private int mScreenWidth;
    //右边距:
    private int rightPadding;

    private int mMenuWidth;


    public SlidingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager vm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        vm.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;

        //获取自定义属性:
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingView, defStyleAttr, 0);

        int n = attrs.getAttributeCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.SlidingView_rightPadding:
                    rightPadding = (int) typedArray.getDimension(attr,
                            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics()));
            }//end switch
        }

    }//end function

    public SlidingView(Context context) {
        this(context, null);
    }

    public SlidingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

//是否为第一次
    private boolean once=false;
    //控件:
    private LinearLayout mWapper;
    private LinearLayout mMenu;
    private LinearLayout mContent;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once){
            mWapper= (LinearLayout) getChildAt(0);
            mMenu= (LinearLayout) mWapper.getChildAt(0);
            setMenuOnClick(mMenu);
            mContent= (LinearLayout) mWapper.getChildAt(1);
            mMenuWidth=mMenu.getLayoutParams().width=mScreenWidth-rightPadding;
            mContent.getLayoutParams().width=mScreenWidth;
            once=true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //侧边菜单的按钮
private View item1;
private View item2;
private View item3;
private View item4;

    /**
     * 此函数用于设置菜单按钮的点击事件
     * @param view
     */
    private void setMenuOnClick(View view){

        item1=view.findViewById(R.id.home);
        item2=view.findViewById(R.id.help);
        item3=view.findViewById(R.id.setting);
        item4=view.findViewById(R.id.about);

           item1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMenu();
            }
        });

        item2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HelpActivity.class);
                ((Activity) getContext()).startActivity(intent);
            }
        });

        item3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SettingsActivity.class);
                ((Activity) getContext()).startActivity(intent);
            }
        });

        item4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AboutActivity.class);
                ((Activity) getContext()).startActivity(intent);
            }
        });

    }//end setMenuClick


    private boolean isOpen=false;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){
            this.scrollTo(mMenuWidth,0);
            item1.setClickable(false);
            item2.setClickable(false);
            item3.setClickable(false);
            item4.setClickable(false);
            isOpen=true;
        }
    }//end function

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action=ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
            {
                int scrollX=getScrollX();
                if (scrollX>=mMenuWidth/2){
                    closeMenu();
                }else {
                    openMenu();
                }
              return true;
            }
        }
        return super.onTouchEvent(ev);
    }//end onTouch event

    public void openMenu(){
        this.smoothScrollTo(0,0);
        if (isOpen)return;
        //设置菜单按钮可点击
        item1.setClickable(true);
        item1.setFocusable(true);
        item2.setClickable(true);
        item2.setFocusable(true);
        item3.setClickable(true);
        item3.setFocusable(true);
        item4.setClickable(true);
        item4.setFocusable(true);

        isOpen=true;
    }

    public void closeMenu(){
        this.smoothScrollTo(mMenuWidth,0);
        if (!isOpen)return;
        item1.setClickable(false);
        item1.setFocusable(false);
        item2.setClickable(false);
        item2.setFocusable(false);
        item3.setClickable(false);
        item3.setFocusable(false);
        item4.setClickable(false);
        item4.setFocusable(false);
        isOpen=false;
    }
    //切换菜单:
    public void toggle(){
        LogUtil.d("SlidingView","onToggle");
        if (!isOpen){
            openMenu();
        }
        else {
            closeMenu();
        }
    }//end menu

//滚动发生时调用
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //调用属性动画设置:
        float scale=1.0f*l/mMenuWidth;
        float rightScale=0.8f+0.2f*scale;
        float leftScale=1.0f-scale*0.3f;
        float leftAlpha=1.0f-1.0f*scale;
        //设置缩放中心点:
        mContent.setPivotX(0);
        mContent.setPivotY(mContent.getHeight()/2);
        //设置缩放:
        mContent.setTranslationX(rightScale);
        mContent.setScaleY(rightScale);
        mContent.setScaleX(rightScale);


        mMenu.setScaleX(leftScale);
        mMenu.setScaleY(leftScale);
        mMenu.setAlpha(leftAlpha);
        mMenu.setTranslationX(mMenuWidth*scale*0.7f);
    }//end function

}
