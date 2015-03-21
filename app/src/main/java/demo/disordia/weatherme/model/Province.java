package demo.disordia.weatherme.model;

/**
 * Created by Disordia profaneden on 2015-03-20.
 * 此类用于存放省份的信息：
 */
public class Province {
    private int id;
    private String provinceName;
    private String provinceCode;
//构造函数:
    public Province(){

    }
//有参数构造函数:
    public Province(String provinceCode,String provinceName){
        this.provinceCode=provinceCode;
        this.provinceName=provinceName;
    }

//获取参数：
    public int getId() {
        return id;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }
//设置参数:
    public void setId(int id) {
        this.id = id;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }
}
