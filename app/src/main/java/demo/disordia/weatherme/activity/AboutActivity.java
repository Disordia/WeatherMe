package demo.disordia.weatherme.activity;

import android.app.Activity;
import android.os.Bundle;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.optimization.ActivitiesCollector;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-22.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.activity
 * Descibe:此类用于显示项目的相关信息:
 */
public class AboutActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        ActivitiesCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        ActivitiesCollector.removeActivity(this);
        super.onDestroy();
    }
}
