package ctas.ctsdk.okhttp.request;

import java.text.Normalizer;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * Created by CTAS on 2017/8/10.
 * 只是提供request  就是工具类
 */
public class CommonRequest {
    /**
     *
     * @param url
     * @param params
     * @return 返回一个创建好的request对象
     * */
    public static Request createPostRequest(String url ,RequestParams params){

        FormBody.Builder mFormBuild = new FormBody.Builder();
        if(params !=null){
            for (Map.Entry<String, String> stringStringEntry : params.urlParams.entrySet()) {
                mFormBuild.add(stringStringEntry.getKey(),stringStringEntry.getValue());
            }


        }
        FormBody formbody=mFormBuild.build();

        return new Request.Builder().url(url).post(formbody).build();
    }

    /**
     *
     * @param url
     * @param params
     * @return  返回一个get类型的请求    */
    public static Request createGetRequest(String url,RequestParams  params){
      StringBuilder urlbuilder = new StringBuilder(url).append("?");
        if(params !=null){

            for (Map.Entry<String, String> stringStringEntry : params.urlParams.entrySet()) {
                urlbuilder.append(stringStringEntry.getKey()).append("=")
                        .append(stringStringEntry.getValue()).append("&");
            }
        }

        return new Request.Builder().
                url(urlbuilder.substring(0, urlbuilder.length() - 1))
                .get()
                .build();
    }


}
