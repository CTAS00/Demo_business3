package ctas.ctsdk.videoview;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import ctas.ctsdk.R;
import ctas.ctsdk.constant.SDKConstant;

/**
 * Created by CTAS on 2017/8/13.
 */
public class CustomVideoView  extends RelativeLayout implements View.OnClickListener,
        MediaPlayer.OnPreparedListener, MediaPlayer.OnInfoListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, TextureView.SurfaceTextureListener{
    /**
     * Constant
     */
    private static final String TAG = "MraidVideoView";
    private static final int TIME_MSG = 0x01;
    private static final int TIME_INVAL = 1000;
    private static final int STATE_ERROR = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_PLAYING = 1;
    private static final int STATE_PAUSING = 2;
    //加载会存在失败的情况下
    private static final int LOAD_TOTAL_COUNT = 3;

    /**
     * UI
     */
    private ViewGroup mParentContainer;
    private RelativeLayout mPlayerView;
    private TextureView mVideoView;
    private Button mMiniPlayBtn;
    private ImageView mFullBtn;
    private ImageView mLoadingBar;
    private ImageView mFrameView;
    private AudioManager audioManager;//音频播放器
    private Surface videoSurface;//真正显示视频数据的类

    /**
     * Data
     */
    private String mUrl;//要加载的视频地址
    private String mFrameURI;
    private boolean isMute;
    private int mScreenWidth, mDestationHeight;

    /**
     * Status状态保护
     */
    private boolean canPlay = true;
    private boolean mIsRealPause;//是不是真正暂停
    private boolean mIsComplete;
    private int mCurrentCount;
    private int playerState = STATE_IDLE;//当前处于原始的状态

    private MediaPlayer mediaPlayer;
    private ADVideoPlayerListener listener;//回调给应用层的
    private ScreenEventReceiver mScreenReceiver;//屏幕是否锁屏
    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TIME_MSG:
                    if (isPlaying()) {
                        //还可以在这里更新progressbar
                        //LogUtils.i(TAG, "TIME_MSG");
                        listener.onBufferUpdate(getCurrentPosition());
                        sendEmptyMessageDelayed(TIME_MSG, TIME_INVAL);
                    }
                    break;
            }
        }
    };

    public int getCurrentPosition() {
        if (this.mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public boolean isPlaying() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            return true;
        }
        return false;
    }

    public CustomVideoView(Context context, ViewGroup parentContainer) {
        super(context);
        mParentContainer = parentContainer;
        //获得音频播放器
        audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        initData();
        initView();
        registerBroadcastReceiver();
    }
    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(this.getContext());
        mPlayerView = (RelativeLayout) inflater.inflate(R.layout.xadsdk_video_player, this);
        mVideoView = (TextureView) mPlayerView.findViewById(R.id.xadsdk_player_video_textureView);
        mVideoView.setOnClickListener(this);
        mVideoView.setKeepScreenOn(true);
        mVideoView.setSurfaceTextureListener(this);
        initSmallLayoutMode(); //init the small mode
    }

    private void initData() {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        mDestationHeight = (int) (mScreenWidth * SDKConstant.VIDEO_HEIGHT_PERCENT);
    }


//    public CustomVideoView(Context context) {
//        super(context);
//    }
//
//    public CustomVideoView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//    }
//
//    public CustomVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//    }

    /**
     * 加载视频
     */
    public void load(){

    }

    /**
     * 暂停视频
     */
    public void pause(){

    }

    /**
     * 回复视频
     */
    public void resume(){

    }

    /**
     * 播放完成后回到初始状态
     */
    public void playBack(){

    }

    /**
     * 视频处于停滞状态
     */
    public void stop(){


    }

    /**
     * 销毁道歉的自定义view
     */
    public void destoy(){

    }

    /**
     * 跳转到大屏并且暂停
     */
    public void seekAndPause(){


    }






    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    /**
     * 供slot层来实现具体点击逻辑,具体逻辑还会变，
     * 如果对UI的点击没有具体监测的话可以不回调
     */
    public interface ADVideoPlayerListener {

        public void onBufferUpdate(int time);

        public void onClickFullScreenBtn();

        public void onClickVideo();

        public void onClickBackBtn();

        public void onClickPlay();

        public void onAdVideoLoadSuccess();

        public void onAdVideoLoadFailed();

        public void onAdVideoLoadComplete();
    }
}
