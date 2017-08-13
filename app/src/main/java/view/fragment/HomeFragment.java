package view.fragment;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import adapter.CourseAdapter;
import ctas.ctsdk.okhttp.listener.DisposeDataListener;
import ctas.demo_business.R;
import module.recommoned.BaseRecommandModel;
import network.http.RequestCenter;
import view.BaseFragment;
import view.home.HomeHeaderLayout;

/**
 * Created by CTAS on 2017/8/10.
 */
public class HomeFragment extends BaseFragment  implements View.OnClickListener, AdapterView.OnItemClickListener {
    /**
     * UI
     */
    private View mContentView;
    private ListView mListView;
    private TextView mQRCodeView;
    private TextView mCategoryView;
    private TextView mSearchView;
    private ImageView mLoadingView;


    private BaseRecommandModel mRecommandData;
    private CourseAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestRecommandData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        mContentView = inflater.inflate(R.layout.fragment_home_layout, container, false);
        initView();
        return mContentView;
    }

    private void initView() {
        mQRCodeView = (TextView) mContentView.findViewById(R.id.qrcode_view);
        mQRCodeView.setOnClickListener(this);
        mCategoryView = (TextView) mContentView.findViewById(R.id.category_view);
        mCategoryView.setOnClickListener(this);
        mSearchView = (TextView) mContentView.findViewById(R.id.search_view);
        mSearchView.setOnClickListener(this);
        mListView = (ListView) mContentView.findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
        mLoadingView = (ImageView) mContentView.findViewById(R.id.loading_view);
        AnimationDrawable anim = (AnimationDrawable) mLoadingView.getDrawable();
        anim.start();
    }

    //发送推荐产品请求
    private void requestRecommandData() {
        RequestCenter.requestRecommandData(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                mRecommandData = (BaseRecommandModel) responseObj;
                Log.e("ctas", "===" + responseObj.toString());
                //更新UI
                showSuccessView();
            }

            @Override
            public void onFailure(Object reasonObj) {
                Log.e("ctas", "======" + reasonObj.toString());
            }
        });
    }

    private void showSuccessView() {
        if (mRecommandData.data.list != null && mRecommandData.data.list.size() > 0) {
            mLoadingView.setVisibility(View.GONE);
            mListView.setVisibility(View.VISIBLE);
            //为listview添加头
//            mListView.addHeaderView(new HomeHeaderLayout(mContext, mRecommandData.data.head));
            mAdapter = new CourseAdapter(mContext, mRecommandData.data.list);
            mListView.setAdapter(mAdapter);
//            mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//                @Override
//                public void onScrollStateChanged(AbsListView view, int scrollState) {
//                }
//
//                @Override
//                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                    mAdapter.updateAdInScrollView();
//                }
//            });
        } else {
            showErrorView();
        }

    }
    private void showErrorView() {
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
