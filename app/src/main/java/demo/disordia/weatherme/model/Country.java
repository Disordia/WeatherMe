package demo.disordia.weatherme.model;

/**
 * Created by Disordia profaneden on 2015-03-20.
 * 此类用于存放县级信息:
 */
public class Country {
    private int id;
    private int cityId;
    private String countryName;
    private String countryCode;

    //默认构造函数:
    public Country() {
    }

    //有参数构造函数:
    public Country(String countryCode, String countryName, int cityId) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.cityId = cityId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }
}
