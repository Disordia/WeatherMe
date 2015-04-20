package demo.disordia.weatherme.ui;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.text.format.Time;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.Random;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.optimization.GlobalApplication;
import demo.disordia.weatherme.setting.Settings;
import demo.disordia.weatherme.util.LogUtil;

/**
 * Created by Disordia profaneden on 2015-04-10.
 */
public class BackGroundManager {

    private static Bitmap bitmap;
    private String weather;
    private static BufferedInputStream bufferedInputStream;
    private boolean isNight = false;
//随机种子:
    private Random random=new Random();
    private void getTheWeather() {
        Time time = new Time();
        time.setToNow();
        int hour = time.hour;
        LogUtil.d("BackGroundManager", "Start to get the weather");
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(GlobalApplication.getContext());
        LogUtil.d("BackGroundManager", "The time is:" + hour);
        if (hour <= 17&&hour>=6) {
            isNight = false;
            weather = sharedPreferences.getString("day_weather", "");
        } else {
            isNight = true;
            weather = sharedPreferences.getString("night_weather", "");
        }
    }


    public Bitmap getBackGround() {

        //判断是否显示:
        Settings settings=Settings.getInstance();
        if (!settings.isShowBk()){
            return null;
        }

        getTheWeather();
        try {
            bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/xingkong.jpeg"));
            //判断天气情况:
            //晴天:
            if (weather.equals("晴")) {
                if (!isNight) {
                    bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/qingtian.jpeg"));
                } else {
                    bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/qingye.jpg"));
                }
                //多云
            } else if (weather.equals("多云")) {
                bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/duoyun.jpg"));
            }
            //阴天:
            else if (weather.equals("阴")) {
                LogUtil.d("BackGroundManager", "Start show Smoke");
                bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/yinyu.jpg"));
            }
            //下面统统是雨天:
            else if (weather.equals("小雨")) {
                //是否为晚上:
                if (!isNight) {
                    if (random.nextBoolean()) {
                        bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/xiayuy.jpeg"));
                    }else {
                        bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/xiayuy.jpeg"));
                    }
                } else {
                    bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/xiayuy.jpeg"));
                }//结束对时间的判断:
            } else if (weather.equals("中雨")) {
                //是否为晚上:
                if (!isNight) {
                    bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/xiayuy.jpeg"));
                } else {
                    bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/xiayuy.jpeg"));
                }//结束对时间的判断:            } else if (weather.equals("暴雨")) {

            } else if (weather.equals("阵雨")) {
                //是否为晚上:
                if (!isNight) {
                    bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/xiayuy.jpeg"));
                } else {
                    bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/xiayuy.jpeg"));
                }//结束对时间的判断:            } else if (weather.equals("雷阵雨")) {
            }

            //下面是对其他天气的判断:

            else if (weather.equals("雷电")) {
                bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/shandian.jpg"));
            } else if (weather.equals("冰雹")) {
            } else if (weather.equals("轻雾")) {
                bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/wu.jpg"));
            } else if (weather.equals("雾")) {
                bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/wu.jpg"));
            } else if (weather.equals("浓雾")) {
                bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/wu.jpg"));
            } else if (weather.equals("霾")) {
            } else if (weather.equals("雨夹雪")) {
            } else if (weather.equals("小雪")) {
                bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/xiaxue.jpeg"));
            } else if (weather.equals("中雪")) {
                bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/xiaxue.jpeg"));
            } else if (weather.equals("大雪")) {
                bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/xiaxue.jpeg"));
            } else if (weather.equals("暴雪")) {
                bufferedInputStream = new BufferedInputStream(GlobalApplication.getContext().getAssets().open("weather/xiaxue.jpeg"));
            } else if (weather.equals("冻雨")) {
            } else if (weather.equals("霜")) {
            } else if (weather.equals("霜冻")) {
            } else if (weather.equals("台风")) {
            } else if (weather.equals("浮尘")) {
            } else if (weather.equals("扬沙")) {
            } else if (weather.equals("沙尘暴")) {
            }//end if


            bitmap = BitmapFactory.decodeStream(bufferedInputStream);
            bufferedInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtil.d("BackGroundManager", "Return bitmap");
        return bitmap;
    }


}
