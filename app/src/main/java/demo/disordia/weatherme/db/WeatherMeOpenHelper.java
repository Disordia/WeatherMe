package demo.disordia.weatherme.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-20.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.db
 * Descibe: 此类用于一些对数据库的建立:
 */
public class WeatherMeOpenHelper extends SQLiteOpenHelper {
    //建表语句:
    /**
    *Province 建表语句
     */
    public static final String CREATE_PROVINCE="create table Province(" +
            "id integer primary key autoincrement," +
            "province_name text," +
            "province_code text)";

    /**
     * City 建表语句
     */
      public static final String CREATE_CITY="create table City(" +
            "id integer primary key autoincrement," +
            "city_name text," +
            "city_code text," +
            "province_id integer)";
    /**
     * Country 建表工具
     */
    public static final String CREATE_COUNTRY="create table Country(" +
            "id integer primary key autoincrement," +
            "country_name text," +
            "country_code text," +
            "city_id integer)";


    public WeatherMeOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PROVINCE);//创建省份表
        db.execSQL(CREATE_CITY);//创建城市表
        db.execSQL(CREATE_COUNTRY);//创建县表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
