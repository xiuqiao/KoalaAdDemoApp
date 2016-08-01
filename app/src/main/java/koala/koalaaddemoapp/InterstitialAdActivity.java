package koala.koalaaddemoapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kika.pluto.ad.KoalaADAgent;
import com.kika.pluto.constants.KoalaConstants;
import com.xinmei.adsdk.nativeads.ADFactory;
import com.xinmei.adsdk.nativeads.NativeAd;
import com.xinmei.adsdk.nativeads.NativeAdListener;

import koala.constants.KoalaAdDemoConstants;

/**
 * Created by leotse on 16/7/28.
 */

public class InterstitialAdActivity extends Activity implements View.OnClickListener{

    private Button mLoadAdBtn;
    private Button mShowAdBtn;
    private ProgressBar mProgressBar;

    private NativeAd mNativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inter_layout);

        initWidget();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // destroy interstitial ad if necessary
        if (mNativeAd != null){
            KoalaADAgent.destroyInterstitialAd(mNativeAd);
        }
    }

    private void initWidget(){
        mLoadAdBtn = (Button) findViewById(R.id.load_inter_btn);
        mShowAdBtn = (Button) findViewById(R.id.show_inter_btn);
        mProgressBar = (ProgressBar) findViewById(R.id.inter_process_bar);

        mProgressBar.setVisibility(View.GONE);
        mLoadAdBtn.setOnClickListener(this);
        mShowAdBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.load_inter_btn:
                loadInterAd();
                mProgressBar.setVisibility(View.VISIBLE);
                break;
            case R.id.show_inter_btn:
                showInterAd();
                break;
            default:
        }
    }

    private void loadInterAd(){
        ADFactory.ADRequestSetting adRequestSetting = ADFactory.getADRequestSetting(KoalaAdDemoConstants.INTER_AD_OID)
                .setImageSize(KoalaConstants.AD_IMAGE_1200x628);
        KoalaADAgent.loadInterstitialAd(adRequestSetting, new NativeAdListener.RequestAdListener() {
            @Override
            public void onSuccess(NativeAd nativeAd) {
                mNativeAd = nativeAd;
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(InterstitialAdActivity.this, "插屏广告加载成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String s, int i) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(InterstitialAdActivity.this, "插屏广告加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showInterAd(){
        if (mNativeAd != null){
            KoalaADAgent.showInterstitialAd(mNativeAd, new NativeAdListener.PreloadAdListener() {
                @Override
                public void onComplete(String s) {
                }

                @Override
                public void onClosed(String s) {

                }
            });
        } else {
            Toast.makeText(this, "请先加载广告", Toast.LENGTH_SHORT).show();
        }

    }
}
