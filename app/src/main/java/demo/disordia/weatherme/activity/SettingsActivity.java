package demo.disordia.weatherme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.optimization.ActivitiesCollector;
import demo.disordia.weatherme.setting.Settings;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-20.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.activity
 * Descibe:存储设置信息：
 */
public class SettingsActivity extends Activity implements View.OnClickListener{
    //控件:
    private Button help;
    private Button about;
    private Button btn_ok, btn_reset;
    private Button btn_setweathershow,btn_setappwidget;
    private CheckBox startWithBoot;
    private CheckBox refresh;
    private CheckBox runInBk;
    //
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        ActivitiesCollector.addActivity(this);
        //载入控件
        help = (Button) findViewById(R.id.btn_help);
        about = (Button) findViewById(R.id.btn_about);
        btn_ok = (Button) findViewById(R.id.btn_ok);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        btn_setweathershow= (Button) findViewById(R.id.btn_setweathershow);
        btn_setappwidget= (Button) findViewById(R.id.btn_setappwidget);
        //载入按钮结束:
        //载入选择控件
        startWithBoot = (CheckBox) findViewById(R.id.start_with_boot);
        runInBk = (CheckBox) findViewById(R.id.run_in_bk);
        refresh = (CheckBox) findViewById(R.id.refresh);

        //获取设置信息:
        settings = Settings.getInstance();
        //设置选择:
        setCheck();
        //设置内部逻辑按钮；
        help.setOnClickListener(this);
        about.setOnClickListener(this);
        //设置上方按钮逻辑：
        btn_ok.setOnClickListener(this);
        btn_reset.setOnClickListener(this);

    }

    private void setCheck() {
        startWithBoot.setChecked(settings.isStartWithBoot());
        runInBk.setChecked(settings.isRunInBk());
        refresh.setChecked(settings.isRefresh());
    }

    @Override
    protected void onDestroy() {
        ActivitiesCollector.removeActivity(this);
        super.onDestroy();
    }

    private  Intent intent;
    /**
     * 设置按钮点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_help:
                 intent=new Intent(SettingsActivity.this,HelpActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_reset:
                settings.Reset();
                setCheck();
                finish();
                break;
            case R.id.btn_ok:
                settings.setRunInBk(runInBk.isChecked());
                settings.setStartWithBoot(startWithBoot.isChecked());
                settings.setRefresh(refresh.isChecked());
                settings.saveSettings();
                finish();
                break;
            case R.id.btn_about:
                 intent=new Intent(SettingsActivity.this,AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_setweathershow:
                break;
            case R.id.btn_setappwidget:
                break;
        }
    }
}
