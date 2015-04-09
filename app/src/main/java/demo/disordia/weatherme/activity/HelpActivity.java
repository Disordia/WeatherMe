package demo.disordia.weatherme.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.optimization.ActivitiesCollector;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-22.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.activity
 * Descibe:
 */
public class HelpActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.help);
        ActivitiesCollector.addActivity(this);
        ((TextView)findViewById(R.id.tv_title)).setText("遇到问题了?");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivitiesCollector.removeActivity(this);
    }
}
