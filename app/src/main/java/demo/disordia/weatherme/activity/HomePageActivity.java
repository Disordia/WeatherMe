package demo.disordia.weatherme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.optimization.ActivitiesCollector;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-22.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.activity
 * Descibe:此活动是整个程序的主页：
 */
public class HomePageActivity extends Activity implements View.OnClickListener {
    private Button cross;
    private Button to_set;
    private Button to_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        ActivitiesCollector.addActivity(this);
        //载入控件
        cross= (Button) findViewById(R.id.cross);
        to_set= (Button) findViewById(R.id.to_set);
        to_help= (Button) findViewById(R.id.to_help);

        //设置按钮逻辑:
        cross.setOnClickListener(this);
        to_set.setOnClickListener(this);
        to_help.setOnClickListener(this);
        //设置逻辑完毕
    }

    @Override
    protected void onDestroy() {
        ActivitiesCollector.removeActivity(this);
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.to_help:{
                Intent i=new Intent(HomePageActivity.this,HelpActivity.class);
                startActivity(i);
            }break;
            case R.id.to_set:{
                Intent i=new Intent(HomePageActivity.this,SettingsActivity.class);
                startActivity(i);
            }break;
            case R.id.cross:{
                Intent i=new Intent(HomePageActivity.this,WeatherActivity.class);
                startActivity(i);
            }break;
            default:
                break;
        }
    }
}
