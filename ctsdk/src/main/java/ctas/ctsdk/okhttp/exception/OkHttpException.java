package ctas.ctsdk.okhttp.exception;

/**
 * Created by CTAS on 2017/8/11.
 */
public class OkHttpException {
    private static final long serialVersionUID = 1L;

    /**
     * the server return code
     */
    private int ecode;

    /**
     * the server return error message
     */
    private Object emsg;

    public OkHttpException(int ecode, Object emsg) {
        this.ecode = ecode;
        this.emsg = emsg;
    }

    public int getEcode() {
        return ecode;
    }

    public Object getEmsg() {
        return emsg;
    }
}
