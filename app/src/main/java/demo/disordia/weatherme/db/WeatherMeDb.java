package demo.disordia.weatherme.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import demo.disordia.weatherme.model.City;
import demo.disordia.weatherme.model.Country;
import demo.disordia.weatherme.model.Province;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-20.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.db
 * Descibe:此类用于对数据库的一些查询等操作：
 */
public class WeatherMeDb {
    /**
     * 数据库版本
     */
    public  static int VERSION=1;
    /**
     * 数据库名
     */
    public static final String DB_NAME="weather_me";
    /**
     * 数据库
     */
    private SQLiteDatabase db;
    /**
     * 本地实例
     */
    private  static WeatherMeDb weatherMeDb;

    //将构造函数私有化：
    private WeatherMeDb(Context context) {
        WeatherMeOpenHelper weatherMeOpenHelper=new WeatherMeOpenHelper(context,DB_NAME+".db",null,VERSION);
        db=weatherMeOpenHelper.getWritableDatabase();
    }

    //获取实例：
    public synchronized static WeatherMeDb getInstance(Context context) {
        if (weatherMeDb==null) {
            weatherMeDb = new WeatherMeDb(context);
        }
        return weatherMeDb;
    }

    /**
     * 保存省份信息：
     * @param province
     */
    public void saveProvince(Province province){
        ContentValues values=new ContentValues();
        values.put("province_name",province.getProvinceName());
        values.put("province_code",province.getProvinceCode());
        db.insert("Province",null,values);
    }

    /**
     * 获取省份信息
     *
     */
    public List<Province> getProvincies(){
        List<Province> provinces=new ArrayList<Province>();
        Cursor cursor=db.query("Province",null,null,null,null,null,null);
        if (cursor.moveToFirst()){//如果数据库有信息：
            do {
                Province province=new Province();
                province.setId(cursor.getInt(cursor.getColumnIndex("id")));
                province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
                province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
                provinces.add(province);
            }while (cursor.moveToNext());
        }
        return provinces;
    }

    /**
     * 此函数用于储存城市信息:
     * @param city
     */

    public void saveCity(City city){
        ContentValues values=new ContentValues();
        values.put("city_name",city.getCityName());
        values.put("city_code",city.getCityCode());
        values.put("province_id",city.getProvinceId());
        db.insert("City",null,values);
    }

    /**
     * 获取城市信息
     * @param provinceId
     * @return
     */
    public List<City> getCities(int provinceId){
        List<City> cities=new ArrayList<City>();
        Cursor cursor=db.query("City",null,"province_id=?",new String[]{String.valueOf(provinceId)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                City city=new City();
                city.setId(cursor.getInt(cursor.getColumnIndex("id")));
                city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
                city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
                city.setProvinceId(provinceId);
                cities.add(city);
            }while (cursor.moveToNext());
        }

        return cities;
    }

    /**
     * 存储县信息:
     * @param country
     */
    public void saveCountry(Country country){
        ContentValues values=new ContentValues();
        values.put("city_id",country.getCityId());
        values.put("country_code",country.getCountryCode());
        values.put("country_name",country.getCountryName());
        db.insert("Country",null,values);
    }

    /**
     * 获取县信息
     * @param cityId
     * @return
     */
    public List<Country> getCountries(int cityId){
        List<Country> countries=new ArrayList<Country>();
        Cursor cursor=db.query("Country",null,"city_id=?",new String[]{String.valueOf(cityId)},null,null,null);
        if (cursor.moveToFirst()){
            do {
                Country country=new Country();
                country.setCountryName(cursor.getString(cursor.getColumnIndex("country_name")));
                country.setCountryCode(cursor.getString(cursor.getColumnIndex("country_code")));
                country.setCityId(cityId);
                country.setId(cursor.getInt(cursor.getColumnIndex("id")));
                countries.add(country);
            }while (cursor.moveToNext());
        }

        return countries;
    }

}
