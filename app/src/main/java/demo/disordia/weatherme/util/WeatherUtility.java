package demo.disordia.weatherme.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import demo.disordia.weatherme.optimization.GlobalApplication;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-21.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.util
 * Descibe:此类用于解析网络传回的天气信息
 */
public class WeatherUtility {
    private static String city_name;
    private static String updatetime;
    private static String wendu;
    private static String fengli;
    private static String shidu;
    private static String fengxiang;
    private static String high;
    private static String low;
    private static boolean breakout = false;
//here reqires more information：

    //存放白天天气信息:
    private static class day {
        public static String type;
        public static String fengxiang;
        public static String fengli;
    }

    //存放夜晚天气信息
    private static class night {
        public static String type;
        public static String fengxiang;
        public static String fengli;
    }

    public static void saveWeatherInfo() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(GlobalApplication.getContext()).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", city_name);
//注意这边没有city code！！
        editor.putString("wendu", wendu);
        editor.putString("fengli", fengli);
        editor.putString("date",simpleDateFormat.format(new Date()));
        editor.putString("updatetime", updatetime);
        editor.putString("shidu", shidu);
        editor.putString("fengxiang", fengxiang);
        editor.putString("high", high);
        editor.putString("low", low);
        editor.putString("day_weather", day.type);
        editor.putString("night_weather", night.type);
        editor.commit();

    }


    /**
     * 处理传回的xml文件:
     *
     * @param response
     */
    public static void handleWeatherResponse(String response) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(response));
            int eventType = xmlPullParser.getEventType();
            //开始解析:
            while (eventType != XmlPullParser.END_DOCUMENT) {

                String nodeName = xmlPullParser.getName();

                switch (eventType) {
                    //开始解析某个节点:
                    case XmlPullParser.START_TAG: {
                        //抽取前段信息:
                        if ("city".equals(nodeName)) {
                            city_name = xmlPullParser.nextText();
                            xmlPullParser.nextTag();
                            updatetime = xmlPullParser.nextText();
                            xmlPullParser.nextTag();
                            wendu = xmlPullParser.nextText();
                            xmlPullParser.nextTag();
                            fengli = xmlPullParser.nextText();
                            xmlPullParser.nextTag();
                            shidu = xmlPullParser.nextText();
                            xmlPullParser.nextTag();
                            fengxiang = xmlPullParser.nextText();
                            LogUtil.d("WeatherUtility", "The fengxiang is :" + fengxiang);
                            xmlPullParser.nextTag();
                        } else if ("forecast".equals(nodeName)) {
                            xmlPullParser.nextTag();//weather
                            xmlPullParser.nextTag();//date
//                            xmlPullParser.nextTag();
                            xmlPullParser.nextText();//the text in date
                            xmlPullParser.nextTag();//high
                            high = xmlPullParser.nextText();
                            LogUtil.d("WeatherUtiles", "Next Text is:（高温）" + high);
//                            LogUtil.d("Weather utility","The high temp is："+high);
                            xmlPullParser.nextTag();//low
                            low = xmlPullParser.nextText();
                            xmlPullParser.nextTag();//day
                            xmlPullParser.nextTag();//type
                            day.type = xmlPullParser.nextText();
                            LogUtil.d("Weather Utility", "The type is:" + day.type);
                            xmlPullParser.nextTag();//fengxiang
                            day.fengxiang = xmlPullParser.nextText();
                            xmlPullParser.nextTag();//fengli
                            day.fengli = xmlPullParser.nextText();
                            //com to night
                            xmlPullParser.nextTag();//day
                            xmlPullParser.nextTag();//night
                            xmlPullParser.nextTag();//type
                            night.type = xmlPullParser.nextText();
                            xmlPullParser.nextTag();//fengxiang
                            night.fengxiang = xmlPullParser.nextText();
                            xmlPullParser.nextTag();//fengli
                            night.fengli = xmlPullParser.nextText();
                            breakout = true;
                        }
                    }
                    break;//end start tag case


                    case XmlPullParser.END_TAG: {

                    }
                    break;//end end tag case
                    default:
                        break;
                }//end switch
                if (breakout) {
                    breakout = false;
                    break;
                }
                eventType = xmlPullParser.next();
            }//end while
            //存储天气信息
            saveWeatherInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//end funciton
}
