package demo.disordia.weatherme.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.net.HttpCallbackListener;
import demo.disordia.weatherme.net.QueryNTManager;
import demo.disordia.weatherme.optimization.ActivitiesCollector;
import demo.disordia.weatherme.optimization.GlobalApplication;
import demo.disordia.weatherme.service.AutoUpdateService;
import demo.disordia.weatherme.ui.BackGroundManager;
import demo.disordia.weatherme.ui.SlidingView;
import demo.disordia.weatherme.util.LogUtil;
import demo.disordia.weatherme.util.WeatherUtility;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-22.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.activity
 * Descibe:此活动用于显示天气信息:(在程序内)
 */
public class WeatherActivity extends Activity {

    private String countryCode;
    private String currentCountry;
    //存放天气状况:
    private String weatherStr;
    private Button change_city;
    private Button btnfresh, btnhome;
    //各控件:
    private TextView
            date,
            fengxiang,
            fengli,
            weather_now,
            wendu,
            updatetime,
            city_name;
    //设置背景用：
    ImageView bk_layout;
    //设置天气是否显示用:
    View weather_info_layout;

    //侧滑菜单:
    SlidingView slidingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.weather_layout);

        //初始化各控件:
        date = (TextView) findViewById(R.id.date);
        fengxiang = (TextView) findViewById(R.id.fengxiang);
        fengli = (TextView) findViewById(R.id.fengli);
        updatetime = (TextView) findViewById(R.id.updatetime);
        wendu = (TextView) findViewById(R.id.wendu);
        weather_now = (TextView) findViewById(R.id.weather_now);
        city_name = (TextView) findViewById(R.id.city_name);
        change_city = (Button) findViewById(R.id.set_country);
        btnfresh = (Button) findViewById(R.id.btn_fresh);
        btnhome = (Button) findViewById(R.id.btn_home);
        slidingView = (SlidingView) findViewById(R.id.sliding_view);
        //设置不透明度
        bk_layout = (ImageView) findViewById(R.id.bk_layout);

//        bk_layout.getBackground().setAlpha(255);//0~255
        //隐藏天气显示:
        weather_info_layout = findViewById(R.id.weather_info_layout);
        weather_info_layout.setVisibility(View.INVISIBLE);

        //获取城市信息:
        countryCode = getIntent().getStringExtra("country_code");
        LogUtil.d("WeatherActivity", "CountryCode is" + countryCode);
        currentCountry = PreferenceManager.getDefaultSharedPreferences(GlobalApplication.getContext()).getString("country_code", "");
        //设置按钮逻辑:
        change_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WeatherActivity.this, AreaActivity.class);
                startActivity(intent);
                ActivitiesCollector.removeActivity(WeatherActivity.this);
                finish();
            }
        });
        btnfresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果选择了城市
                if (!TextUtils.isEmpty(currentCountry)) {
                    //刷新显示以及获取
                    if (!TextUtils.isEmpty(countryCode)) {
                        updatetime.setText("同步中,请稍候~~");
                        queryWeatherCode(countryCode);
                    } else {
                        updatetime.setText("同步中,请稍候~~");
                        queryWeatherCode(currentCountry);
                    }
                } else {
                    //避免bug001
                    String firstSet=PreferenceManager.getDefaultSharedPreferences(GlobalApplication.getContext()).getString("firstCountryCode", "");
                    if (!TextUtils.isEmpty(firstSet)) {
                        currentCountry=firstSet;
                        updatetime.setText("同步中,请稍候~");
                        queryWeatherCode(firstSet);
                    } else {
                        //如果用户没有选择城市:
                        updatetime.setText("都说了要选择城市啦~~");
                    }
                }
            }
        });
        //还是不显示那个捉急的界面了吧
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //原来是显示关于界面，现在改成了侧滑菜单啦~~
                slidingView.toggle();
//                Intent intent=new Intent(WeatherActivity.this,AboutActivity.class);
//                startActivity(intent);
//                ActivitiesCollector.removeActivity(WeatherActivity.this);
                //finish();
            }
        });
        //设置按钮逻辑完毕~~

        //如果就是不是原来的城市就开始重新查询
        if (!TextUtils.isEmpty(countryCode) && (!currentCountry.equals(countryCode))) {
            //存储本地实例
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(GlobalApplication.getContext()).edit();
            editor.putString("country_code", countryCode);
            editor.commit();

            //让天气部分看不见
            weather_info_layout.setVisibility(View.INVISIBLE);
            //开始查询天气:
            updatetime.setText("同步中,请稍候~~");
            queryWeatherCode(countryCode);
        } else if (TextUtils.isEmpty(currentCountry)) {
            updatetime.setText("您还没有选择任何城市哟~~");
        } else {
            //直接显示本地天气:
            showWeather();
        }
    }

    /**
     * 此函数用于向网络发送获取天气请求:
     *
     * @param countryCode
     */
    private void queryWeatherCode(String countryCode) {
        String address = "http://www.weather.com.cn/data/list3/city" + countryCode + ".xml";
        queryFromServer(address, "countryCode");
    }

    /**
     * 此函数用于向网络发送获取城市请求:
     *
     * @param weatherCode
     */
    private void queryWeatherInfo(String weatherCode) {
        String address = "http://wthrcdn.etouch.cn/WeatherApi?citykey=" + weatherCode;
        queryFromServer(address, "weatherCode");
    }

    /**
     * 此函数用于向服务器发送请求:
     *
     * @param address
     * @param type
     */
    private void queryFromServer(final String address, final String type) {
        QueryNTManager.sendHttpRequest(address, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                //获取城市编号:
                if ("countryCode".equals(type)) {
                    if (!TextUtils.isEmpty(response)) {
                        String[] array = response.split("\\|");
                        if (array != null && array.length == 2) {
                            String weatherCode = array[1];
                            //把城市代码存储
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(GlobalApplication.getContext()).edit();
                            editor.putString("weather_code", weatherCode);
                            editor.commit();
                            //存储完毕:
                            queryWeatherInfo(weatherCode);
                        }
                    }
                } else if ("weatherCode".equals(type)) {
                    //获取城市天气:
                    WeatherUtility.handleWeatherResponse(response);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showWeather();
                        }
                    });
                }
            }

            @Override
            public void onErro(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updatetime.setText("喵呜~~同步失败...");
                    }
                });

            }
        });

    }

    /**
     * 此函数用于显示天气信息:
     */
    private void showWeather() {
        LogUtil.d("WeatherActivity", "Start to show weather");


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(GlobalApplication.getContext());
        //显示天气:(调试)
        LogUtil.d("Weather Activity", "The day weather is：" + preferences.getString("day_weather", ""));
        LogUtil.d("Weather Activity", "The night weather is：" + preferences.getString("night_weather", ""));
        //显示控件:(运行)
        city_name.setText(preferences.getString("city_name", ""));
        updatetime.setText("今天" + preferences.getString("updatetime", "") + "发布");
        date.setText(preferences.getString("date", ""));
        fengxiang.setText(preferences.getString("fengxiang", ""));
        fengli.setText(preferences.getString("fengli", ""));
        if (!preferences.getString("day_weather", "").equals(preferences.getString("night_weather", ""))) {
            //确认白天和晚上的天气不是一样的,不然出现多云转多云就搞笑了.....>w<
            weatherStr = preferences.getString("day_weather", "") + "转" + preferences.getString("night_weather", "");
        } else {
            weatherStr = preferences.getString("day_weather", "");
        }
        weather_now.setText(weatherStr);
        String wenduStr = preferences.getString("high", "").substring(3) + "~" + preferences.getString("low", "").substring(3);
        wendu.setText(wenduStr);
        //更新完毕，显示天气:
        weather_info_layout.setVisibility(View.VISIBLE);
        //显示我们的天气背景:
        BackGroundManager backGroundManager = new BackGroundManager();
        bk_layout.setImageBitmap(backGroundManager.getBackGround());

        /**
         * 激活AutoUpdate服务:
         */
        Intent intent = new Intent(this, AutoUpdateService.class);
        startService(intent);
    }

    /**
     * 屏蔽back键:
     */
    @Override
    public void onBackPressed() {

    }
}
