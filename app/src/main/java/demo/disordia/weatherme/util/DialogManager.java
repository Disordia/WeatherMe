package demo.disordia.weatherme.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Project: WeatherMe
 *
 * @author: Disrodia
 * create time: 2015-03-21.
 * e-mail:1482219895@qq.com
 * Package:demo.disordia.weatherme.util
 * Descibe:此类用于管理和显示各种对话框:
 */
public class DialogManager {
    public static ProgressDialog progressDialog;
public static void showProgressDialog(Context context){
    if (progressDialog==null){
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("正在加载...");
        progressDialog.setCanceledOnTouchOutside(false);
    }
    progressDialog.show();
}
    public static void closeProgressDialog(){
        if (progressDialog!=null){
            progressDialog.dismiss();
            progressDialog=null;
        }
    }

}
