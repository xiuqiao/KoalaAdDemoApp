package koala.koalaaddemoapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kika.pluto.ad.KoalaADAgent;
import com.kika.pluto.constants.KoalaConstants;
import com.xinmei.adsdk.nativeads.ADFactory;
import com.xinmei.adsdk.nativeads.NativeAd;
import com.xinmei.adsdk.nativeads.NativeAdListener;

import koala.constants.KoalaAdDemoConstants;
import koala.utils.KoalaAdDemoUtils;

/**
 * Created by leotse on 16/7/28.
 */

public class NativeAdActivity extends Activity {

    private RelativeLayout mAdView;
    private ImageView mIconImg;
    private TextView mTitleTxt;
    private TextView mDescTxt;
    private ImageView mCreativeImg;
    private Button mCtaBtn;

    private Button mLoadAdBtn;
    private Button mShowAdBtn;
    private ProgressBar mProgressBar;

    private NativeAd mNativeAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.native_layout);

        initWidget();
        KoalaAdDemoUtils.getUIHandler();
    }

    private void initWidget(){
        mAdView = (RelativeLayout) findViewById(R.id.native_ad_view);
        mAdView.setVisibility(View.GONE);

        mIconImg = (ImageView) findViewById(R.id.icon_img);
        mTitleTxt = (TextView) findViewById(R.id.title_txt);
        mDescTxt = (TextView) findViewById(R.id.desc_txt);
        mCreativeImg = (ImageView) findViewById(R.id.creative_img);
        mCtaBtn = (Button) findViewById(R.id.cta_btn);

        mLoadAdBtn = (Button) findViewById(R.id.load_native_btn);
        mShowAdBtn = (Button) findViewById(R.id.show_native_btn);
        mShowAdBtn.setClickable(false);

        mProgressBar = (ProgressBar) findViewById(R.id.native_process_bar);
        mProgressBar.setVisibility(View.GONE);

        mLoadAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                loadNativeAd();
            }
        });

        mShowAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNativeAd();
            }
        });
    }

    private void loadNativeAd(){
        ADFactory.ADRequestSetting adRequestSetting = ADFactory.getADRequestSetting(KoalaAdDemoConstants.NATIVE_AD_OID)
                .setImageSize(KoalaConstants.AD_IMAGE_1200x628)
                .setAdSource(KoalaConstants.AD_SOURCE_XM);  // you don't have to set ad source generally
        KoalaADAgent.loadAd(adRequestSetting, new NativeAdListener.RequestAdListener() {
            @Override
            public void onSuccess(final NativeAd nativeAd) {
                mProgressBar.setVisibility(View.GONE);
                mShowAdBtn.setClickable(true);
                mNativeAd = nativeAd;
                Toast.makeText(NativeAdActivity.this, "原生广告加载成功", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(String s, int i) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(NativeAdActivity.this, "原生广告加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNativeAd(){
        if (mNativeAd == null){
            Toast.makeText(this, "请先加载广告", Toast.LENGTH_SHORT).show();
            return;
        }

        KoalaAdDemoUtils.getBackHandler().post(new Runnable() {
            @Override
            public void run() {
                // load icon and creative image
                final Bitmap iconBitmap = KoalaAdDemoUtils.loadBitmap(mNativeAd.getIcon());
                final Bitmap creativeBitmap = KoalaAdDemoUtils.loadBitmap(mNativeAd.getCreatives().get(KoalaConstants.AD_IMAGE_1200x628));

                KoalaAdDemoUtils.getUIHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        mIconImg.setImageBitmap(iconBitmap);
                        mCreativeImg.setImageBitmap(creativeBitmap);

                        // set ad info
                        mTitleTxt.setText(mNativeAd.getTitle());
                        mDescTxt.setText(mNativeAd.getDescription());
                        mCtaBtn.setText(mNativeAd.getCallToAction());

                        // register ad view
                        mAdView.setVisibility(View.VISIBLE);
                        KoalaADAgent.registerNativeAdView(mNativeAd, mAdView, new NativeAdListener.NativeAdClickedListener() {
                            @Override
                            public void onAdClicked(String s) {
                                Toast.makeText(NativeAdActivity.this, "点击原生广告", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onAdOpened(String s) {
                                Toast.makeText(NativeAdActivity.this, "打开原生广告", Toast.LENGTH_SHORT).show();
                                // unregister ad when necessary
                                KoalaADAgent.unregisterNativeAdView(mNativeAd);
                            }
                        });
                    }
                });

            }
        });
    }

}
