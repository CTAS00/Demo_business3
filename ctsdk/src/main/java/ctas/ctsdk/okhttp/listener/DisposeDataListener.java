package ctas.ctsdk.okhttp.listener;

/**
 * Created by CTAS on 2017/8/10.
 */
public interface DisposeDataListener {

    public void onSuccess(Object respnseObj);

    public void onFailure(Object responseObj);
}
