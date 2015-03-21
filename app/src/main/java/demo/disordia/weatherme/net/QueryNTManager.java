package demo.disordia.weatherme.net;

import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Disordia profaneden on 2015-03-20.
 * 此类用于建立连接并从网络获取初始原生信息：
 */
public class QueryNTManager {
public static void sendHttpRequest(final String address, final HttpCallbackListener callbackListener){
new Thread(new Runnable() {
    @Override
    public void run() {
        //建立连接：
        HttpURLConnection connection=null;
        try {
            URL url=new URL(address);
            connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in=connection.getInputStream();
            //对获取的流进行读取
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            StringBuilder response=new StringBuilder();
            String line="";
            while ((line=reader.readLine())!=null){
                response.append(line);
            }
            //如果存在侦听器
            if (callbackListener!=null){
                Log.d("QueryNTManager","The response is :"+response);
                callbackListener.onFinish(response.toString());
            }

        }catch (Exception e){
            if (callbackListener!=null){
                callbackListener.onErro(e);
            }
        }finally {
            if (connection!=null){
                connection.disconnect();
            }
        }
    }
}).start();

}
}
