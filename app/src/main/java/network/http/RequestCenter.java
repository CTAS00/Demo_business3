package network.http;

import ctas.ctsdk.CommonOkHttpClient;
import ctas.ctsdk.okhttp.listener.DisposeDataHandler;
import ctas.ctsdk.okhttp.listener.DisposeDataListener;
import ctas.ctsdk.okhttp.request.CommonRequest;
import ctas.ctsdk.okhttp.request.RequestParams;
import module.recommoned.BaseRecommandModel;

/**
 * Created by CTAS on 2017/8/12.
 * 为什么要在应用层也封装这个呢  不可能每次调用都要去做commomokhttpclient  为了以后的替换
 */
public class RequestCenter {

    //根据参数发送所有post请求
    public static void postRequest(String url, RequestParams params, DisposeDataListener listener, Class<?> clazz) {
        CommonOkHttpClient.sendRequest(CommonRequest.
                createGetRequest(url, params), new DisposeDataHandler(listener, clazz));
    }

    public static void requestRecommandData(DisposeDataListener listener) {
        RequestCenter.postRequest(HttpConstants.HOME_RECOMMAND, null, listener, BaseRecommandModel.class);
    }
}
