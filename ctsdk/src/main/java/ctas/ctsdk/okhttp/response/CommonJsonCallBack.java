package ctas.ctsdk.okhttp.response;

import android.os.Handler;
import android.os.Looper;

import org.json.JSONObject;

import java.io.IOException;

import ctas.ctsdk.okhttp.exception.OkHttpException;
import ctas.ctsdk.okhttp.listener.DisposeDataHandler;
import ctas.ctsdk.okhttp.listener.DisposeDataListener;
import ctas.ctsdk.okhttp.utils.jsonToObject;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by CTAS on 2017/8/10.
 */
public class CommonJsonCallBack implements Callback {

    /**
     * the logic layer exception, may alter in different app
     */
    protected final String RESULT_CODE = "ecode"; // 有返回则对于http请求来说是成功的，但还有可能是业务逻辑上的错误
    protected final int RESULT_CODE_VALUE = 0;
    protected final String ERROR_MSG = "emsg";
    protected final String EMPTY_MSG = "";
    protected final String COOKIE_STORE = "Set-Cookie"; // decide the server it
    // can has the value of
    // set-cookie2

    /**
     * the java layer exception, do not same to the logic error
     */
    protected final int NETWORK_ERROR = -1; // the network relative error
    protected final int JSON_ERROR = -2; // the JSON relative error
    protected final int OTHER_ERROR = -3; // the unknow error

    private Handler mDeliveryHandler;
    private DisposeDataListener mListener;
    private Class<?> mClass;

    /**
     * zijiema deal
     * exception deal
     * thread to ui deal
     */
    public CommonJsonCallBack(DisposeDataHandler handle) {
        this.mListener = handle.mListener;
        this.mClass = handle.mClass;
        this.mDeliveryHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, final IOException e) {
        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                mListener.onFailure(new OkHttpException(NETWORK_ERROR,e));
            }
        });


    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
      final String result = response.body().string();

        mDeliveryHandler.post(new Runnable() {
            @Override
            public void run() {
                     handlerResponse(result);
            }
        });
    }

    /**
     * deal response
     * @param result
     */
    private void handlerResponse(Object result) {
        if(result ==null && result.toString().trim().equals("")){
            mListener.onFailure(new OkHttpException(NETWORK_ERROR,EMPTY_MSG));
            return ;
        }


        try {
            JSONObject json=new JSONObject(result.toString());
            if(json.has(RESULT_CODE)) {
                if(json.getInt(RESULT_CODE) == RESULT_CODE_VALUE){

                    if(mClass == null ){
                        mListener.onSuccess(result);
                    }
                    else {
                        //1. jie xi cheng object
                       Object object=   jsonToObject.toObject(result.toString(),mClass);
                        if(object!=null){
                            mListener.onSuccess(object);
                        }else {
                            mListener.onFailure(new OkHttpException(JSON_ERROR,EMPTY_MSG));

                        }
                    }


                }
            } else {

                mListener.onFailure(new OkHttpException(OTHER_ERROR,json.getInt(RESULT_CODE)));
            }

        }catch (Exception e){

            mListener.onFailure(new OkHttpException(OTHER_ERROR,e.getMessage()));
        }


    }
}

