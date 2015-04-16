package demo.disordia.weatherme.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.optimization.ActivitiesCollector;
import demo.disordia.weatherme.util.LogUtil;

/**
 * Created by Disordia profaneden on 2015-04-09.
 */
public class SettingShowActivity extends Activity {

    private CheckBox anti_ali,showOnlyDesktop;
    private SeekBar alphaShow;
    private TextView alphaText;
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitiesCollector.addActivity(this);
        LogUtil.d("SettingShowActivity", "onCreate");
        setContentView(R.layout.setting_show_layout);
        anti_ali = (CheckBox) findViewById(R.id.anti_ali);
        alphaText = (TextView) findViewById(R.id.alpha_test);
        alphaShow = (SeekBar) findViewById(R.id.seekBarAlpha);
        showOnlyDesktop= (CheckBox) findViewById(R.id.show_only_desktop);


        ((TextView)findViewById(R.id.tv_title)).setText("天气显示设置");
        //初始化 设置:
        initSet();
        //为控件增加侦听器:
        anti_ali.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setAntiAli(anti_ali.isChecked());
                settings.saveSettings();
            }
        });

        alphaShow.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress==0){
                    alphaText.setText("     已关闭显示");
                }else {
                    alphaText.setText("     现在选择的透明度是: "+progress);
                }
                settings.setAlpha(progress);
                settings.saveSettings();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        findViewById(R.id.btn_show_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setAntiAli(anti_ali.isChecked());
                settings.setAlpha(alphaShow.getProgress());
                settings.setShowOnlyDesktop(showOnlyDesktop.isChecked());
                settings.saveShowSettings();
                finish();
            }
        });
        findViewById(R.id.btn_show_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.ResetShow();
                initSet();
            }
        });

    }//end onCreate()

    @Override
    protected void onDestroy() {
        ActivitiesCollector.removeActivity(this);
        super.onDestroy();
    }

    private void initSet() {
        settings = Settings.getInstance();
        anti_ali.setChecked(settings.isAntiAli());
        alphaShow.setProgress(settings.getAlpha());
        showOnlyDesktop.setChecked(settings.isShowOnlyDesktop());
     }
}
