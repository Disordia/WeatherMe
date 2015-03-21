package demo.disordia.weatherme.activity;

import android.app.Activity;
import android.os.Bundle;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.optimization.ActivitiesCollector;

/**
 * Created by Disordia profaneden on 2015-03-20.
 */
public class IndexPage extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitiesCollector.addActivity(this);
        setContentView(R.layout.index);
    }


}
