package demo.disordia.weatherme.model;

/**
 * Created by Disordia profaneden on 2015-03-20.
 * 此类用于存放城市信息
 */
public class City {
    private int id;//id
    private int provinceId;//省份id
    private String cityName;//城市名
    private String cityCode;

    //构造函数:
    public City() {
    }
    //有参数的构造函数:
    public City(String cityCode,String cityName,int provinceId){
        this.cityCode=cityCode;
        this.cityName=cityName;
        this.provinceId=provinceId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
