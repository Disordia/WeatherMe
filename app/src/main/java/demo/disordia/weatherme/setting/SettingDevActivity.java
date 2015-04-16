package demo.disordia.weatherme.setting;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.optimization.ActivitiesCollector;
import demo.disordia.weatherme.optimization.GlobalApplication;

/**
 * Created by Disordia profaneden on 2015-04-12.
 */
public class SettingDevActivity extends Activity {
    //下拉列表框:
    private Spinner spinner;
    private Settings settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitiesCollector.addActivity(this);
        setContentView(R.layout.dev_set_layout);
        spinner = (Spinner) findViewById(R.id.spinner);
        ((TextView) findViewById(R.id.tv_title)).setText("开发者选项");
        //指定适配器:
        ArrayAdapter<CharSequence> layers = null;
        layers = ArrayAdapter.createFromResource(this, R.array.layer, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(layers);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(GlobalApplication.getContext(), "You click the " + position + " item", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        InitSpinner();
        //spinner初始化完毕:

        findViewById(R.id.btn_show_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.setLayer(spinner.getSelectedItemPosition());
                settings.saveDevSettings();
                finish();
            }
        });
        findViewById(R.id.btn_show_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings.ResetDev();
                InitSpinner();
            }
        });

    }


    @Override
    protected void onDestroy() {
        ActivitiesCollector.removeActivity(this);
        super.onDestroy();
    }

    public void InitSpinner(){
        settings=Settings.getInstance();
        spinner.setSelection(settings.getLayer());
    }

}
