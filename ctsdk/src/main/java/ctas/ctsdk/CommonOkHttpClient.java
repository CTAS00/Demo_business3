package ctas.ctsdk;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import ctas.ctsdk.okhttp.https.Utils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by CTAS on 2017/8/10.
 */
public class CommonOkHttpClient {

    private static final int TIME_OUT=30;//
    private static OkHttpClient mOkHttpClient;
    //can shu  jiben yizhi
    static {
        OkHttpClient.Builder okHttpBuilder=new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);

        okHttpBuilder.followRedirects(true);//yun xu chong ding xiang

        //add https provider
        okHttpBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
        okHttpBuilder.sslSocketFactory(Utils.getSslSocketFactory());

        //create clien  object

        mOkHttpClient =okHttpBuilder.build();

    }




    public static Call sendRequest(Request request, Callback commonCallback){
         Call call =mOkHttpClient.newCall(request);
         call.enqueue(commonCallback);
         return call;

    }


}
