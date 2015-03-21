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
    private boolean showWeather;
    private boolean startWithBoot;
    private Country currentCuntry;
    private String style;
    Settings settings;
    //私有构造函数:
    private Settings(){
        showWeather=true;
        startWithBoot=false;
        currentCuntry=null;
        style="default";
    }

    /**
     * 储存设置信息:
     */
    public void saveSettings(){
        SharedPreferences.Editor editor= GlobalApplication.getContext().getSharedPreferences("settings", Context.MODE_PRIVATE).edit();
        editor.putBoolean("showWeather",showWeather);
        editor.putBoolean(" startWithBoot",startWithBoot);
        editor.putString("style", style);
        if (currentCuntry!=null) {
            editor.putString("country_name", currentCuntry.getCountryName());
            editor.putString("country_code",currentCuntry.getCountryCode());
            editor.putInt("country_id",currentCuntry.getId());
            editor.putInt("city_id",currentCuntry.getCityId());
        }
    }

    /**
     * 读取设置信息:
     */
public void loadSettings(){
SharedPreferences preferences=GlobalApplication.getContext().getSharedPreferences("settings",Context.MODE_PRIVATE);
    this.showWeather=preferences.getBoolean("showWeather",false);
    this.startWithBoot=preferences.getBoolean("startWithBoot",false);
    this.style=preferences.getString("style","");
    //将选择的城市读入:
    this.currentCuntry.setCountryCode(preferences.getString("country_code",""));
    this.currentCuntry.setCountryName(preferences.getString("country_name",""));
    this.currentCuntry.setId(preferences.getInt("country_id",0));
    this.currentCuntry.setCityId(preferences.getInt("city_id",0));
}

    public Country getCurrentCuntry() {
        return currentCuntry;
    }

    public void setCurrentCuntry(Country currentCuntry) {
        this.currentCuntry = currentCuntry;
    }

    public void setShowWeather(boolean showWeather) {
        this.showWeather = showWeather;
    }

    public void setStartWithBoot(boolean startWithBoot) {
        this.startWithBoot = startWithBoot;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public Settings getInstance() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }
    //
    public  void initSetting()
    {

    }

    //还原参数：
    public void Reset()
    {
        showWeather=true;
        startWithBoot=false;
        currentCuntry=null;
        style="default";
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
