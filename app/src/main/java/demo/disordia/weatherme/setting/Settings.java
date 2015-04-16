package demo.disordia.weatherme.setting;

import android.content.Context;
import android.content.SharedPreferences;

import demo.disordia.weatherme.model.Country;
import demo.disordia.weatherme.optimization.GlobalApplication;
import demo.disordia.weatherme.util.LogUtil;

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
    private static boolean showBk;
    //显示设置参数:
    private static int alpha;
    private static boolean antiAli;
    private static boolean showOnlyDesktop;
    //是否为第一次设置显示:
    private static boolean firstsetShow = true;
    //是否为第一次设置:
    private static boolean firstset = true;
    private static String style;
    private static Settings settings;
    //开发者选项:
    private static int layer;
    private static boolean firstSetDev = true;

    //私有构造函数:
    private Settings() {
        SharedPreferences preferences = GlobalApplication.getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        firstset = preferences.getBoolean("firstset", true);
        firstsetShow = preferences.getBoolean("firstsetShow", true);
        if (firstset) {

            Reset();
        } else {
            LogUtil.d("SettingsActivity", "loadSettings");
            loadSettings();
        }
        if (firstsetShow) {
            ResetShow();
        } else {
            LogUtil.d("SettingsActivity", "loadShowSettings");
            loadShowSettings();
        }
        if (firstSetDev){
            ResetDev();
        }else {
            LogUtil.d("SettingActivity","loadDevSettings");
            loadDevSettings();
        }
    }

    /**
     * 储存设置信息:
     */
    public void saveSettings() {
        SharedPreferences.Editor editor = GlobalApplication.getContext().getSharedPreferences("settings", Context.MODE_PRIVATE).edit();
        editor.putBoolean("showWeather", showWeather);
        editor.putBoolean("startWithBoot", startWithBoot);
        editor.putBoolean("runInBk", runInBk);
        editor.putBoolean("refresh", refresh);
        editor.putString("style", style);
        editor.putBoolean("firstset", false);
        editor.putBoolean("showBk",showBk);
        editor.commit();
    }

    public void saveShowSettings() {
        SharedPreferences.Editor editor = GlobalApplication.getContext().getSharedPreferences("settings", Context.MODE_PRIVATE).edit();
        editor.putBoolean("antiAli", antiAli);
        editor.putInt("alpha", alpha);
        editor.putBoolean("firstsetShow", false);
        editor.putBoolean("showOnlyDesktop", showOnlyDesktop);
        editor.commit();
    }

    public void saveDevSettings() {
        SharedPreferences.Editor editor = GlobalApplication.getContext().getSharedPreferences("settings", Context.MODE_PRIVATE).edit();
        editor.putBoolean("firstSetDev", false);
        editor.putInt("layer", layer);
        editor.commit();
    }

    /**
     * 读取设置信息:
     */
    public void loadSettings() {
        SharedPreferences preferences = GlobalApplication.getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        this.showWeather = preferences.getBoolean("showWeather", false);
        this.startWithBoot = preferences.getBoolean("startWithBoot", false);
        this.refresh = preferences.getBoolean("refresh", false);
        this.runInBk = preferences.getBoolean("runInBk", false);
        this.antiAli = preferences.getBoolean("antiAli", false);
        this.showBk=preferences.getBoolean("showBk",true);
    }

    public void loadShowSettings() {
        SharedPreferences preferences = GlobalApplication.getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        this.alpha = preferences.getInt("alpha", 0);
        this.style = preferences.getString("style", "");
        this.showOnlyDesktop = preferences.getBoolean("showOnlyDesktop", false);
    }

    public void loadDevSettings() {
        SharedPreferences preferences = GlobalApplication.getContext().getSharedPreferences("settings", Context.MODE_PRIVATE);
        this.layer = preferences.getInt("layer", 0);
    }

    /**
     * set函数们:
     */
    public static void setAlpha(int alpha) {
        Settings.alpha = alpha;
    }

    public static void setAntiAli(boolean antiAli) {
        Settings.antiAli = antiAli;
    }

    public void setShowWeather(boolean showWeather) {
        Settings.showWeather = showWeather;
    }

    public void setRunInBk(boolean runInBk) {
        Settings.runInBk = runInBk;
    }

    public void setStartWithBoot(boolean startWithBoot) {
        Settings.startWithBoot = startWithBoot;
    }

    public void setRefresh(boolean refresh) {
        Settings.refresh = refresh;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public static void setShowOnlyDesktop(boolean showOnlyDesktop) {
        Settings.showOnlyDesktop = showOnlyDesktop;
    }

    public static void setLayer(int layer) {
        Settings.layer = layer;
    }

    public static void setShowBk(boolean showBk) {
        Settings.showBk = showBk;
    }

    /**
     * get函数们:
     */
    public static boolean isRefresh() {
        return refresh;
    }

    public static boolean isRunInBk() {
        return runInBk;
    }

    public String getStyle() {
        return style;
    }

    public boolean isShowWeather() {
        return showWeather;
    }

    public boolean isStartWithBoot() {
        return startWithBoot;
    }

    public static boolean isAntiAli() {
        return antiAli;
    }

    public static int getAlpha() {
        return alpha;
    }

    public static boolean isShowOnlyDesktop() {
        return showOnlyDesktop;
    }

    public static int getLayer() {
        return layer;
    }

    public static boolean isShowBk() {
        return showBk;
    }

    /**
     * 获取实例化对象:
     *
     * @return
     */
    public static Settings getInstance() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }


    //还原参数：
    public void Reset() {
        showWeather = true;
        startWithBoot = false;
        refresh = true;
        runInBk = true;
        style = "default";
        firstset = false;
        showBk=true;
        saveSettings();
        loadSettings();
    }


    //显示还原参数:
    public void ResetShow() {

        antiAli = true;
        alpha = 240;
        firstsetShow = false;
        showOnlyDesktop = false;
        saveShowSettings();
        loadShowSettings();

    }

    //还原开发者参数:
    public void ResetDev(){
        layer=0;
        firstSetDev=false;
        saveDevSettings();
        loadDevSettings();
    }

}
