package demo.disordia.weatherme.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

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
public class TitleMainLayout extends LinearLayout {
    private static Button titleOverflow,share;
    private static PopupWindow popupWindow;

    public TitleMainLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_title_main, this);
        titleOverflow= (Button) findViewById(R.id.btn_overflowx);
        share= (Button) findViewById(R.id.btn_share);
        //添加分享功能:
        share.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT, "我现在正在用一款名叫WeatherME的天气软件,用起来效果还不错哦~~");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(Intent.createChooser(intent, ((Activity)getContext()).getTitle()));
            }
        });
        //为按钮增加侦听器:
        titleOverflow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater mLayoutInflater = (LayoutInflater) GlobalApplication.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ViewGroup viewGroup = (ViewGroup) mLayoutInflater.inflate(R.layout.popup_layout, null);
                popupWindow = new PopupWindow(viewGroup, LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT, true);

                popupWindow.setAnimationStyle(android.R.anim.fade_in);

                popupWindow.setOutsideTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(new ColorDrawable(0));

//                popupWindow.showAtLocation(titleOverflow, Gravity.RIGHT|Gravity.TOP, 0, 5);
                popupWindow.showAsDropDown(titleOverflow);

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
                                Intent intent = new Intent(getContext(), SettingsActivity.class);
                                ((Activity) getContext()).startActivity(intent);
                                popupWindow.dismiss();
                            }
                        });
                        viewGroup.findViewById(R.id.ttv_home).setVisibility(GONE);
                        viewGroup.findViewById(R.id.ttv_help).setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), HelpActivity.class);
                                ((Activity) getContext()).startActivity(intent);
                                popupWindow.dismiss();
                            }
                        });

                    }
                });//end setOnclick
    }
}
