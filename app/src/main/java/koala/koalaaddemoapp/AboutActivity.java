package koala.koalaaddemoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.widget.TextView;

/**
 * Created by leotse on 16/7/28.
 */

public class AboutActivity extends AppCompatActivity {

    private TextView mAboutTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_layout);

        initWidget();
    }

    private void initWidget(){
        mAboutTxt = (TextView) findViewById(R.id.about_txt);

        String html = "<font color='#86B404'><strong>功能简介</strong></font><br>";
        html += "<font>Koala SDK由Koala广告平台开发,提供了原生广告、横幅广告、插屏广告以及视频广告等丰富的广告形式。<br>" +
                "Koala平台广告资源覆盖全球240+国家和地区,并强力聚合了Facebook Audience Network和Admob广告,使用非常便捷," +
                "一小时内可以完成接入。<br>" +
                "在使用Koala广告资源前,你需要注册平台账号以及申请广告id,并获取最新的广告SDK。</font><br><br>";

        html += "<font color='#86B404'><strong>联系我们</strong></font><br>";
        html += "<font>QQ:1479348217</font><br>";
        html += "<font>Email:februus90@gmail.com</font>";

        mAboutTxt.setText(Html.fromHtml(html));
    }
}
