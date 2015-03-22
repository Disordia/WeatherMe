package demo.disordia.weatherme.setting;

import android.content.Context;
import android.content.SharedPreferences;

import demo.disordia.weatherme.model.Country;
import demo.disordia.weatherme.optimization.GlobalApplication;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-20.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.setting
 * Descibe:这个类用于存放设置的参数：
 */
public class Settings {
    //设置参数:
    private static boolean refresh;
    private static boolean showWeather;
    private static boolean startWithBoot;
    private static boolean runInBk;
    //是否为第一次设置:
    private static boolean firstset=true;
    private static String style;
    private static Settings settings;
    //私有构造函数:
    private Settings(){
        if (firstset){
            Reset();
        }else{
            loadSettings();
        }
    }

    /**
     * 储存设置信息:
     */
    public void saveSettings(){
        SharedPreferences.Editor editor= GlobalApplication.getContext().getSharedPreferences("settings", Context.MODE_PRIVATE).edit();
        editor.putBoolean("showWeather",showWeather);
        editor.putBoolean(" startWithBoot",startWithBoot);
        editor.putBoolean("runInBk",runInBk);
        editor.putBoolean("refresh",refresh);
        editor.putString("style", style);
        editor.commit();
    }

    /**
     * 读取设置信息:
     */
public void loadSettings(){
SharedPreferences preferences=GlobalApplication.getContext().getSharedPreferences("settings",Context.MODE_PRIVATE);
    this.showWeather=preferences.getBoolean("showWeather",false);
    this.startWithBoot=preferences.getBoolean("startWithBoot",false);
    this.refresh=preferences.getBoolean("refresh",false);
    this.runInBk=preferences.getBoolean("runInBk",false);
    this.style=preferences.getString("style","");
}

    public  void setShowWeather(boolean showWeather) {
        Settings.showWeather = showWeather;
    }

    public  void setRunInBk(boolean runInBk) {
        Settings.runInBk = runInBk;
    }

    public void setStartWithBoot(boolean startWithBoot) {
        Settings.startWithBoot = startWithBoot;
    }

    public void setRefresh(boolean refresh) {
        Settings.refresh = refresh;
    }

    public static boolean isRefresh() {
        return refresh;
    }
    public static boolean isRunInBk() {
        return runInBk;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public static Settings getInstance() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }


    //还原参数：
    public void Reset()
    {
        showWeather=true;
        startWithBoot=false;
        refresh=true;
        runInBk=true;
        style="default";
        firstset=false;
        saveSettings();
    }

    public String getStyle() {
        return style;
    }

    public Settings getSettings() {
        return settings;
    }

    public boolean isShowWeather() {
        return showWeather;
    }

    public boolean isStartWithBoot() {
        return startWithBoot;
    }


}
