package ctas.ctsdk.okhttp.listener;

/**
 * Created by CTAS on 2017/8/10.
 * zijiema deal
 */
public class DisposeDataHandler {

    public DisposeDataListener mListener =null;
    public Class<?> mClass=null;


    public DisposeDataHandler( DisposeDataListener mListener){
     this.mListener =mListener;

    }

    public DisposeDataHandler(DisposeDataListener mListener, Class<?> clazz){

        this.mListener =mListener;
        this.mClass = clazz;
    }

}
