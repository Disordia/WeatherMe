package demo.disordia.weatherme.util;

import android.text.TextUtils;
import android.util.Log;

import demo.disordia.weatherme.db.WeatherMeDb;
import demo.disordia.weatherme.model.City;
import demo.disordia.weatherme.model.Country;
import demo.disordia.weatherme.model.Province;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-20.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.util
 * Descibe: 此类用于从网络获取地区信息并将信息存入本地:
 */
public class Utility {
    /**
     * Describe: 此函数用于从网络或本地获取省份信息:
     *
     * @param weatherMeDb
     * @param response
     * @return
     */

    //synchronized :同一时刻只有一个线程执行:
    public synchronized static boolean handleProvinceResponsse(WeatherMeDb weatherMeDb, String response) {

//        LogUtil.d("Utility",response);
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province(array[0], array[1]);
                    weatherMeDb.saveProvince(province);
                }
                LogUtil.d("Utility", "Use net Province information");
                return true;
            }

        } else if (response == "") {
            //如果没有信息则调用本地信息：
            response = "01|北京,02|上海,03|天津,04|重庆,05|黑龙江,06|吉林,07|辽宁,08|内蒙古,09|河北,10|山西," +
                    "11|陕西,12|山东,13|新疆,14|西藏,15|青海,16|甘肃,17|宁夏,18|河南,19|江苏,20|湖北,21|浙江,22|安徽," +
                    "23|福建,24|江西,25|湖南,26|贵州,27|四川,28|广东,29|云南,30|广西,31|海南,32|香港,33|澳门,34|台湾";
            String[] allProvinces = response.split(",");
            if (allProvinces != null && allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] array = p.split("\\|");
                    Province province = new Province(array[0], array[1]);
                    weatherMeDb.saveProvince(province);
                }
                LogUtil.d("Utility", "Use local Province information");
                return true;
            }
        }
        return false;
    }

    /**
     * 此函数用于获取网络的城市信息
     *
     * @param weatherMeDb
     * @param response
     * @param provinceId
     * @return
     */

    public static boolean handleCitiesResponse(WeatherMeDb weatherMeDb, String response, int provinceId) {
        LogUtil.d("Utility","response is: "+response);
        if (!TextUtils.isEmpty(response)) {
            LogUtil.d("Utility","response is not empty");
            String[] allCities = response.split(",");
            if (allCities != null && allCities.length > 0) {
                for (String c : allCities) {
                    String[] array = c.split("\\|");
                    City city = new City(array[0], array[1], provinceId);
                    LogUtil.d("handleCitiesResponse","SaveCity");
                    weatherMeDb.saveCity(city);
                }
                LogUtil.d("Utility", "Use net City information");
                return true;
            }
        }
        //maybe add local function if possible in the future:
        return false;
    }

    /**
     * 此函数用于从网络获取县信息
     *
     * @param weatherMeDb
     * @param response
     * @param cityId
     * @return
     */
    public static boolean handleCountriesResponse(WeatherMeDb weatherMeDb, String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCountries = response.split(",");
            if (allCountries != null && allCountries.length > 0) {
                for (String c : allCountries) {
                    String[] array = c.split("\\|");
                    Country country = new Country(array[0], array[1], cityId);
                    weatherMeDb.saveCountry(country);
                }
                LogUtil.d("Utility", "Use net Country information");
                return true;
            }
        }
        return false;
    }


}
