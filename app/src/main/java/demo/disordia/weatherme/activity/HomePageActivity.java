package demo.disordia.weatherme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.optimization.ActivitiesCollector;
import demo.disordia.weatherme.ui.ArcMenu;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-22.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.activity
 * Descibe:此活动是整个程序的主页：
 */
public class HomePageActivity extends Activity  {

    private ArcMenu arcMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        ActivitiesCollector.addActivity(this);
        //载入控件
        arcMenu= (ArcMenu) findViewById(R.id.arcmenu);
        //设置按钮逻辑:
        arcMenu.setOnMenuItemClickListener(new ArcMenu.OnMenuItemClickListener() {
            @Override
            public void onClick(View childView, int pos) {
                switch (childView.getId()) {
                    case R.id.to_help: {
                        Intent i = new Intent(HomePageActivity.this, HelpActivity.class);
                        startActivity(i);
                    }
                    break;
                    case R.id.to_set: {
                        Intent i = new Intent(HomePageActivity.this, SettingsActivity.class);
                        startActivity(i);
                    }
                    break;
                    case R.id.cross: {
                        Intent i = new Intent(HomePageActivity.this, WeatherActivity.class);
                        startActivity(i);
                    }
                    break;
                    default:
                        break;

                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        ActivitiesCollector.removeActivity(this);
        super.onDestroy();

    }

}
