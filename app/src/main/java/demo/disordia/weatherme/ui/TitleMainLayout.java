package demo.disordia.weatherme.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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
public class TitleMainLayout extends LinearLayout {

    public TitleMainLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.layout_title_main,this);
    }
}
