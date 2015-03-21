package demo.disordia.weatherme.net;

public interface HttpCallbackListener{
    void onFinish(String response);
    void onErro(Exception e);
}
