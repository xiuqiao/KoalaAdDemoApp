package koala.koalaaddemoapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by leotse on 16/7/28.
 */

public class AboutActivity extends Activity {

    private TextView mAboutTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);

        initWidget();
    }

    private void initWidget(){
        mAboutTxt = (TextView) findViewById(R.id.about_txt);

        mAboutTxt.setText("Koala SDK\nKoala SDK支持原生广告、横幅广告、插屏广告以及视频广告。");
    }
}
