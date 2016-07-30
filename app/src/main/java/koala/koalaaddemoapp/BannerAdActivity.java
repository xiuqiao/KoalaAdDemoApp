package koala.koalaaddemoapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kika.pluto.constants.KoalaConstants;
import com.xinmei.adsdk.nativeads.ADFactory;
import com.xinmei.adsdk.nativeads.KoalaBannerAdView;
import com.xinmei.adsdk.nativeads.NativeAdListener;

import koala.constants.KoalaAdDemoConstants;

/**
 * Created by leotse on 16/7/28.
 */

public class BannerAdActivity extends Activity {

    private Button mLoadBannerBtn;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banner_layout);

        initWidget();
    }

    private void initWidget(){
        mLoadBannerBtn = (Button) findViewById(R.id.load_banner_btn);
        mProgressBar = (ProgressBar) findViewById(R.id.banner_process_bar);
        mProgressBar.setVisibility(View.GONE);

        mLoadBannerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                loadBannerAd();
            }
        });
    }

    private void loadBannerAd(){
        ADFactory.ADRequestSetting adRequestSetting = ADFactory.getADRequestSetting(KoalaAdDemoConstants.BANNER_AD_OID)
                .setAdSource(KoalaConstants.AD_SOURCE_XM);
        KoalaBannerAdView koalaBannerAdView = (KoalaBannerAdView) findViewById(R.id.koala_banner_ad);
        koalaBannerAdView.loadBannerAd(adRequestSetting, new NativeAdListener.RequestBannerAdListener() {
            @Override
            public void onSuccess(KoalaBannerAdView koalaBannerAdView) {
                Log.d(KoalaAdDemoConstants.LOG_TAG, "koala banner ad load succeed");
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(BannerAdActivity.this, "横幅广告加载成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String s, int i) {
                Log.d(KoalaAdDemoConstants.LOG_TAG, "koala banner ad load failed, error msg > " + s);
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(BannerAdActivity.this, "横幅广告加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
