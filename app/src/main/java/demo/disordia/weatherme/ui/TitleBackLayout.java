package demo.disordia.weatherme.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import demo.disordia.weatherme.R;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-24.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.ui
 * Descibe:
 */
public class TitleBackLayout extends LinearLayout {
    public TitleBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.title_back_layout,this);
        findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity)getContext()).finish();
            }
        });
    }
}
