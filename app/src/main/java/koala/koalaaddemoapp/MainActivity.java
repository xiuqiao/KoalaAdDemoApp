package koala.koalaaddemoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kika.pluto.ad.KoalaADAgent;
import com.xinmei.adsdk.nativeads.AppWall;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mNativeAdBtn;
    private Button mBannerAdBtn;
    private Button mInterAdBtn;
    private Button mAppWallBtn;
    private Button mAboutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init koala sdk (you must init before use)
        KoalaADAgent.init(MainActivity.this);

        initWidget();
    }

    private void initWidget(){
        mNativeAdBtn = (Button) findViewById(R.id.native_ad_btn);
        mBannerAdBtn = (Button) findViewById(R.id.banner_ad_btn);
        mInterAdBtn = (Button) findViewById(R.id.inter_ad_btn);
        mAppWallBtn = (Button) findViewById(R.id.appwall_btn);
        mAboutBtn = (Button) findViewById(R.id.about_btn);

        mNativeAdBtn.setOnClickListener(this);
        mBannerAdBtn.setOnClickListener(this);
        mInterAdBtn.setOnClickListener(this);
        mAppWallBtn.setOnClickListener(this);
        mAboutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.native_ad_btn:
                intent = new Intent(MainActivity.this, NativeAdActivity.class);
                startActivity(intent);
                break;
            case R.id.banner_ad_btn:
                intent = new Intent(MainActivity.this, BannerAdActivity.class);
                startActivity(intent);
                break;
            case R.id.inter_ad_btn:
                intent = new Intent(MainActivity.this, InterstitialAdActivity.class);
                startActivity(intent);
                break;
            case R.id.appwall_btn:
                KoalaADAgent.startAppWall();
                break;
            case R.id.about_btn:
                intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            default:
        }
    }

}
