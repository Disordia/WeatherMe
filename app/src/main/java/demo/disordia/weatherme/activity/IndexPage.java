package demo.disordia.weatherme.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.optimization.ActivitiesCollector;
import demo.disordia.weatherme.optimization.GlobalApplication;
import demo.disordia.weatherme.util.LogUtil;

/**
 * Created by Disordia profaneden on 2015-03-20.
 */
public class IndexPage extends Activity {

    public final int SHOW_WEATHER=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitiesCollector.addActivity(this);
        setContentView(R.layout.index);

        //
         Timer timer = new Timer();
        TimerTask tast=new TimerTask() {
            @Override
            public void run() {
                //获取信息:
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(GlobalApplication.getContext());
                if (preferences.getBoolean("city_selected", false)) {
                    //如果选择了地区:进入天气显示界面:
                    Intent intent = new Intent(IndexPage.this, WeatherActivity.class);
                    intent.putExtra("country_code", preferences.getString("country_code", ""));
                    startActivity(intent);
                    finish();
                }else {
                    //进入选择城市界面:
                    Intent intent=new Intent(IndexPage.this,AreaActivity.class);
                    startActivity(intent);
                    finish();
                }
                //添加实例(优化用)
                ActivitiesCollector.addActivity(IndexPage.this);
            }
        };
        timer.schedule(tast, 3000);
//        LogUtil.d("Index Page","Finish onCreate");
    }

    @Override
    protected void onDestroy() {
        ActivitiesCollector.removeActivity(this);
        super.onDestroy();
    }



}
