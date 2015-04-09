package demo.disordia.weatherme.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.util.Timer;
import java.util.TimerTask;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.optimization.ActivitiesCollector;
import demo.disordia.weatherme.optimization.GlobalApplication;
import demo.disordia.weatherme.util.LogUtil;


/**
 * Descibe:这个活动是显示程序的开始界面：
 */
public class IndexPage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加实例(优化用)
        ActivitiesCollector.addActivity(this);
        setContentView(R.layout.index);
        //测试是否为第一次开启程序:
        SharedPreferences preferences=PreferenceManager.getDefaultSharedPreferences(GlobalApplication.getContext());
        if (preferences==null){
            //
            SharedPreferences.Editor editor=  PreferenceManager.getDefaultSharedPreferences(GlobalApplication.getContext()).edit();
            editor.putBoolean("first_boot",true);
            editor.commit();
        }
        Boolean firstBoot=preferences.getBoolean("first_boot",true);
        LogUtil.d("Index Page","the firstBoot is: "+firstBoot);
        //如果是第一次启动程序:
        if (firstBoot==true) {
            LogUtil.d("Index Page","start Index");
            //将第一次开启程序设置成false:
            SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(GlobalApplication.getContext()).edit();
            editor.putBoolean("first_boot", false);
            editor.commit();
            //提交结束:

            //计时显示开始界面
            Timer timer = new Timer();
            TimerTask tast = new TimerTask() {
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
                    } else {
                        //进入选择城市界面:
                        Intent intent = new Intent(IndexPage.this, AreaActivity.class);
//                        intent.putExtra("first boot",true);
                        startActivity(intent);
                        finish();
                    }
                }
            };
            timer.schedule(tast, 3000);
        }else {

            //如果不是第一次开启程序：
            if (preferences.getBoolean("city_selected", false)) {
                //如果选择了地区:进入天气显示界面:
                Intent intent = new Intent(IndexPage.this, WeatherActivity.class);
                intent.putExtra("country_code", preferences.getString("country_code", ""));
                startActivity(intent);
                finish();
            } else {
                //进入选择城市界面:
                Intent intent = new Intent(IndexPage.this, AreaActivity.class);
//                intent.putExtra("first boot",false);
                startActivity(intent);
                finish();
            }
        }  //结束启动
    }




    @Override
    protected void onDestroy() {
        ActivitiesCollector.removeActivity(this);
        super.onDestroy();
    }



}
