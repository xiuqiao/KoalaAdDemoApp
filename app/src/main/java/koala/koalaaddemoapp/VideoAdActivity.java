package koala.koalaaddemoapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kika.pluto.ad.KoalaVideoAdView;
import com.xinmei.adsdk.nativeads.ADFactory;
import com.xinmei.adsdk.nativeads.NativeAdListener;

import koala.constants.KoalaAdDemoConstants;

/**
 * Created by leotse on 16/7/28.
 */

public class VideoAdActivity extends Activity {

    private Button mLoadVideoBtn;
    private ProgressBar mProgressBar;

    private KoalaVideoAdView mKoalaVideoAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_layout);

        initWidget();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // destroy video ad
        if (mKoalaVideoAdView != null){
            mKoalaVideoAdView.destroyVideoView();
        }
    }

    private void initWidget(){
        mLoadVideoBtn = (Button) findViewById(R.id.load_video_btn);
        mProgressBar = (ProgressBar) findViewById(R.id.video_process_bar);
        mProgressBar.setVisibility(View.GONE);

        mLoadVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                loadVideoAd();
            }
        });
    }

    private void loadVideoAd(){
        ADFactory.ADRequestSetting adRequestSetting = ADFactory.getADRequestSetting(KoalaAdDemoConstants.VIDEO_AD_OID);
        mKoalaVideoAdView = (KoalaVideoAdView) findViewById(R.id.koala_video_ad);
        mKoalaVideoAdView.loadISVideoAd(adRequestSetting, new NativeAdListener.RequestVideoAdListener() {
            @Override
            public void onSuccess() {
                Log.d(KoalaAdDemoConstants.LOG_TAG, "video ad load succeed");
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(VideoAdActivity.this, "视频广告加载成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String s, int i) {
                Log.d(KoalaAdDemoConstants.LOG_TAG, "video ad load failed, error message > " + s);
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(VideoAdActivity.this, "视频广告加载失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClick() {
                Log.d(KoalaAdDemoConstants.LOG_TAG, "video ad clicked");
                Toast.makeText(VideoAdActivity.this, "视频广告被点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Log.d(KoalaAdDemoConstants.LOG_TAG, "video ad play completed");
                // destroy video ad view when necessary
                mKoalaVideoAdView.destroyVideoView();
                Toast.makeText(VideoAdActivity.this, "视频广告播放完毕", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onClosed() {
                Log.d(KoalaAdDemoConstants.LOG_TAG, "video ad closed");
                Toast.makeText(VideoAdActivity.this, "视频广告被关闭", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
