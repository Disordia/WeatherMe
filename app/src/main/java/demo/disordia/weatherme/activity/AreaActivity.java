package demo.disordia.weatherme.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import demo.disordia.weatherme.R;
import demo.disordia.weatherme.db.WeatherMeDb;
import demo.disordia.weatherme.list.AreaAdapter;
import demo.disordia.weatherme.model.City;
import demo.disordia.weatherme.model.Country;
import demo.disordia.weatherme.model.Province;
import demo.disordia.weatherme.net.HttpCallbackListener;
import demo.disordia.weatherme.net.QueryNTManager;
import demo.disordia.weatherme.optimization.ActivitiesCollector;
import demo.disordia.weatherme.optimization.GlobalApplication;
import demo.disordia.weatherme.util.DialogManager;
import demo.disordia.weatherme.util.LogUtil;
import demo.disordia.weatherme.util.Utility;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-21.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.activity
 * Descibe:此类用于选择和显示中国境内所有地区:
 */
public class AreaActivity extends Activity {
    /**
     * 常量用于为LIST分层:
     */
    private static int LEVEL_PROVINCE=0;
    private static int LEVEL_CITY=1;
    private static int LEVEL_COUNTRY=2;

    /**
     * 目前的分层:
     */
    private int currentLevel;
    /**
     * 控件:
     */
    private TextView title_text;
    private ListView area_list;
    /**
     *适配器
     */
    private AreaAdapter areaAdapter;
    //此处以后可能会做更改:
    private List<String> areaList=new ArrayList<String>();
    /**
     * 数据库
     */
    private WeatherMeDb weatherMeDb;
    /**
     * 存放信息
     */
    private List<Province> provinces;
    private List<City>cities;
    private List<Country>countries;
    /**
     * 目前选择的:
     */
    private Province selectedProvince;
    private City selectedCity;
    private Country selectedCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.areas);
        //添加实例信息(优化用)
        ActivitiesCollector.addActivity(this);
        //获取各种控件:
        title_text= (TextView) findViewById(R.id.area_title);
        area_list= (ListView) findViewById(R.id.area_list);
        areaAdapter=new AreaAdapter(this,R.layout.area_list,areaList);
        area_list.setAdapter(areaAdapter);
        weatherMeDb=WeatherMeDb.getInstance(this);
        //设置点击事件:
        area_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(currentLevel==LEVEL_PROVINCE){
                    //点击的是省份时
                    selectedProvince=provinces.get(position);
                    //countinue here:
                    LogUtil.d("AreaActivity","go to that Province");
                    initCityData();
                }else if (currentLevel==LEVEL_CITY){
                    selectedCity=cities.get(position);
                    LogUtil.d("AreaActivity","go to that City");
                    initCountryData();
                }else if (currentLevel==LEVEL_COUNTRY){
                    String countryCode=countries.get(position).getCountryCode();
                    //移除实例信息(优化)
                    ActivitiesCollector.removeActivity(AreaActivity.this);

//                    //存储城市代码:
//                    SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(GlobalApplication.getContext()).edit();
//                    editor.putString("country_code",countryCode);
//                    editor.commit();
//                    //结束存储
                    //获取存储实例:
                    Intent intent=new Intent(AreaActivity.this,WeatherActivity.class);
                    intent.putExtra("country_code",countryCode);
                    startActivity(intent);
                    finish();
                }
            }
        });
        initProvinceData();
    }

    /**
     * 初始化县信息:
     */
    private void initCountryData() {
        countries=weatherMeDb.getCountries(selectedCity.getId());
        if(countries.size()>0) {
            areaList.clear();
            for (Country country : countries) {
                areaList.add(country.getCountryName());
            }
            areaAdapter.notifyDataSetChanged();
            area_list.setSelection(0);
            title_text.setText(selectedCity.getCityName());
            currentLevel=LEVEL_COUNTRY;
        }else {
            //从网络获取信息；
            queryFromServer(selectedCity.getCityCode(),"country");
        }

    }

    /**
     * 初始化城市信息：
     */
    private void initCityData() {
        cities=weatherMeDb.getCities(selectedProvince.getId());
        if (cities.size()>0){
            areaList.clear();
            for (City c:cities){
                areaList.add(c.getCityName());
            }
            areaAdapter.notifyDataSetChanged();
            area_list.setSelection(0);
            title_text.setText(selectedProvince.getProvinceName());
            currentLevel=LEVEL_CITY;
        }else {
            //从网络获取信息:
            LogUtil.d("AreaActivity","query City From Internet");
            queryFromServer(selectedProvince.getProvinceCode(), "city");
        }
    }

    /**
     * 初始省份信息:
     */
    private void initProvinceData() {
        provinces=weatherMeDb.getProvincies();
        if (provinces.size()>0){
            areaList.clear();
            for (Province p:provinces){
                areaList.add(p.getProvinceName());
            }
            //提示改变；
            areaAdapter.notifyDataSetChanged();
            area_list.setSelection(0);
            title_text.setText("请选择省份");
            currentLevel=LEVEL_PROVINCE;
        }else {
            //从网络获取:
            queryFromServer(null,"province");
        }
    }

    /**
     * 从网络获取地区信息:
     * @param code
     * @param type
     */
    private void queryFromServer(final String code,final String type){
        final String adress;
        if(!TextUtils.isEmpty(code)){
            adress="http://www.weather.com.cn/data/list3/city"+code+".xml";
        }else {
            adress="http://www.weather.com.cn/data/list3/city.xml";
        }
        LogUtil.d("AreaActivity","The adress is:"+adress);
        DialogManager.showProgressDialog(this);
        QueryNTManager.sendHttpRequest(adress,new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                boolean result=false;
                if ("province".equals(type)){
                    LogUtil.d("AreaActivity","handleProvinceResponsse");
                result= Utility.handleProvinceResponsse(weatherMeDb,response);
                }else if ("city".equals(type)){
                    LogUtil.d("AreaActivity","now the adress is :"+adress);
                    LogUtil.d("AreaActivity","handleCitiesResponse");
                    result=Utility.handleCitiesResponse(weatherMeDb,response,selectedProvince.getId());
                }else  if ("country".equals(type)){
                    LogUtil.d("AreaActivity","handleCountriesResponse");
                    result=Utility.handleCountriesResponse(weatherMeDb,response,selectedCity.getId());
                }
                LogUtil.d("AreaActivity","Result is"+result);
                if (result){
                    //处理成功，返回主线程处理:
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            DialogManager.closeProgressDialog();
                            LogUtil.d("AreaActivity","Get Information sucessfully");
                            if ("province".equals(type)){
                                initProvinceData();
                            }else if ("city".equals(type)){
                                initCityData();
                            }else if ("country".equals(type)){
                                initCountryData();
                            }
                        }
                    });
                }//end result

            }//end onFinish

            @Override
            public void onErro(Exception e) {
                LogUtil.e("AreaActivity","Failed to get the information");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogManager.closeProgressDialog();
                        Toast.makeText(AreaActivity.this,"加载失败",Toast.LENGTH_SHORT);
                    }
                });//end runUiThread
            }//end onErro
        });//
    }//end queryFromServer

    /**
     * 处理back键位：
     */
    @Override
    public void onBackPressed() {
        if (currentLevel==LEVEL_COUNTRY){
            initCityData();
        }else if (currentLevel==LEVEL_CITY){
            initProvinceData();
        }else {
            ActivitiesCollector.removeActivity(this);
            Intent intent=new Intent(AreaActivity.this,WeatherActivity.class);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        //移除实例信息(优化用)
        ActivitiesCollector.removeActivity(this);
        super.onDestroy();
    }
}
