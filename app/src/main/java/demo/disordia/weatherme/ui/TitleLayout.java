package demo.disordia.weatherme.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.lang.annotation.Annotation;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.activity.HelpActivity;
import demo.disordia.weatherme.activity.SettingsActivity;
import demo.disordia.weatherme.activity.WeatherActivity;
import demo.disordia.weatherme.optimization.GlobalApplication;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-24.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.ui
 * Descibe:
 */
public class TitleLayout extends LinearLayout {

    private static Button titleOverflow;
    private static PopupWindow popupWindow;
    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.layout_title, this);
        Button titleBack = (Button) findViewById(R.id.btn_back);
        titleOverflow= (Button) findViewById(R.id.btn_overflow);
        //设置侦听器:

        titleBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).onBackPressed();
            }
        });

        titleOverflow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater mLayoutInflater = (LayoutInflater) GlobalApplication.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup viewGroup= (ViewGroup) mLayoutInflater.inflate(R.layout.popup_layout, null);
                 popupWindow = new PopupWindow(viewGroup, LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT, true);
                //设置进场动画:
                popupWindow.setAnimationStyle(android.R.anim.fade_in);
                //设置 如果在外面点击会自动关闭:
                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(0));

//                popupWindow.showAtLocation(titleOverflow, Gravity.RIGHT|Gravity.TOP, 0, 5);
                popupWindow.showAsDropDown(titleOverflow);
                //设置在外部点击后关闭:
                popupWindow.setTouchInterceptor(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {

                        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                            popupWindow.dismiss();
                            return true;
                        }//end if

                        return false;
                    }
                });//end set

                viewGroup.findViewById(R.id.ttv_set).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getContext(), SettingsActivity.class);
                        ((Activity) getContext()).startActivity(intent);
                        popupWindow.dismiss();
                    }
                });
                viewGroup.findViewById(R.id.ttv_home).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getContext(), WeatherActivity.class);
                        ((Activity) getContext()).startActivity(intent);
                        popupWindow.dismiss();
                    }
                });
                viewGroup.findViewById(R.id.ttv_help).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getContext(), HelpActivity.class);
                        ((Activity) getContext()).startActivity(intent);
                        popupWindow.dismiss();
                    }
                });

            }
        });//end setOnclick
    }
}
