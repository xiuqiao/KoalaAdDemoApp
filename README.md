# Koala SDK 使用说明

## 1.使用须知
Koala 广告SDK针对Android平台广告的请求、展示打开以及安装转化进行了封装，为海外移动互联网产品的广告变现提供了稳定、便捷、可靠的服务。

在使用SDK前，你需要在Koala广告平台申请`App-key`以及`Secret-key`，这两者可以在Koala广告平台上唯一标识一个APP。

当前支持的广告形式有：
>1.原生广告：icon、素材大图（1200x628）  
2.横幅广告：标准尺寸320x50  
3.插屏广告：标准插屏广告  
4.视频广告：320x250（使用前请咨询我们的运营人员）  

## 2.App-key以及Oid申请
App key 以及 secret key都是用来唯一标识一款APP。  
Oid用来唯一标识APP中的具体某个广告位。  
具体可以联系我们的运营人员。

## 3.使用说明
### 添加相应的jar包
你需要导入我们的koala SDK jar包到你的工程；

如果你需要使用facebook的广告，你需要导入facebook audience network SDK到你的工程，Android Studio下你需要在build.gradle的dependencies里加入：
```java
	compile 'com.facebook.android:facebook-android-sdk:4.+'
```

如果你需要使用admob的广告，Android Studio下你需要在build.gradle的dependencies里加入：
```java
	compile 'com.google.android.gms:play-services-ads:8.+'
```

并确认导入`com.google.android.gms.ads.identifier`包下的类确保可以获取用户的GAID。

### 配置文件（AndroidManifest.xml）
首先，需要添加权限：
```java
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```

你需要将appkey和secretkey加到AndroidManifest.xml：
```java
<meta-data
    android:name="AGENT_APPKEY"
    android:value="34c0ab0888e7a42c8b5882e1af3d71f9" />
<meta-data
    android:name="AGENT_SECRET"
    android:value="ca00979da70d117059562f1e85ef06ee" />
```

为了过滤已安装应用提升广告效果，你需要加入：
```java
<receiver android:name="com.kika.pluto.filter.KoalaAppInstallReceiver" >
    <intent-filter>
        <action android:name="android.intent.action.PACKAGE_ADDED" />
	   <action android:name="android.intent.action.PACKAGE_REMOVED" />
        <data android:scheme="package" />
    </intent-filter>
</receiver>
```

如果你需要使用Facebook的插屏广告，你需要加入：
```java
<activity
    android:name="com.facebook.ads.InterstitialAdActivity"
    android:configChanges="keyboardHidden|orientation|screenSize">
</activity>
```

如果你需要使用Koala的插屏广告，你需要加入：
```java
<activity
android:name="com.xinmei.adsdk.nativeads.NativeInterstitialAdActivity"
    android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
    android:theme="@android:style/Theme.NoTitleBar.Fullscreen" >
</activity>
```

如果你需要使用Admob的广告，你需要加入：
```java
<activity
 android:name="com.google.android.gms.ads.AdActivity"
    android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
    android:theme="@android:style/Theme.Translucent" />
```

**由于我们需要获取gaid进行广告转化归因，你需要加入:**
```java
<meta-data
    android:name="com.google.android.gms.version"
    android:value="@integer/google_play_services_version" />
```

### 初始化SDK
在使用SDK请求广告前，请务必初始化Koala SDK。
```java
KoalaADAgent.init(Context context);
```
初始化时机建议在应用首次启动。你并不需要在每次用户打开应用的时候执行该操作。

### 请求原生广告
1-需要创建ADRequestSetting对象，对广告进行相关配置。广告位OID是必备参数。其他可以指定的有icon的size（setIconSize，可选的参数有50x50、100x100、200x200）、广告创意大图的size（setImageSize，当前只支持1200x628）；  
2-加载广告：KoalaADAgent.loadAd()；  
3-如果广告请求成功，你可以获取广告的数据，将其组装成需要的样式放到一个View中；  
4-绑定view与广告：KoalaADAgent.registerNativeAdView()；  
5-SDK会处理该view的点击事件，并回调；  
6-当该广告不再使用后，解除广告绑定：KoalaADAgent.unregisterNativeAdView()；  

### 请求插屏广告
1-创建ADRequestSetting对象，对广告进行相关配置。广告位OID是必备参数；  
2-请求插屏广告：KoalaADAgent.loadInterstitialAd()；  
3-请求广告成功，在你认为合适的时机展示广告：KoalaADAgent.showInterstitialAd()；

### 横幅广告
1.在需要展示banner广告的布局文件中加入：
```java
<com.xinmei.adsdk.nativeads.KoalaBannerAdView 
    android:id = "@+id/koalaAd"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:gravity="center_vertical|center_horizontal"
></com.xinmei.adsdk.nativeads.KoalaBannerAdView>
```

2.接下来在你想要加载 Banner广告的函数中创建一个KoalaBannerAdView对象，直接加载Banner广告即可：
```java
ADFactory.ADRequestSetting mAdRequestSetting = ADFactory.getADRequestSetting().setOid(TEST_OID);
KoalaBannerAdView koalaBannerAdView = (KoalaBannerAdView) findViewById(R.id.koalaAd);
koalaBannerAdView.loadBannerAd(mAdRequestSetting, new RequestBannerAdListener() {

    @Override
    public void onSuccess(KoalaBannerAdView bannerAdView) {
        Log.d("banner ad load succeed");
    }

    @Override
    public void onFailure(String msg, int errorCode) {
        Log.d("banner ad load failed");
    }

    @Override
    public void onClick() {
        Log.d("banner ad load clicked");
    }
});
```

### 视频广告
1.在需要加载视频广告的布局文件中加入
```java
<com.kika.pluto.ad.KoalaVideoAdView
     android:id = "@+id/koalaVideoAd"
     android:layout_width="320dp"
     android:layout_height="250dp"
     android:gravity="center_vertical|center_horizontal"
 ></com.kika.pluto.ad.KoalaVideoAdView>
```

2.创建一个KoalaVideoAdView对象。加载视频广告：
```java
KoalaVideoAdView koalaVideoAdView = (KoalaVideoAdView) findViewById(R.id.koalaVideoAd);
koalaVideoAdView.loadISVideoAd(mAdRequestSetting, new NativeAdListener.RequestVideoAdListener() {
    @Override
    public void onSuccess() {
        Log.d("video load succeed");
    }

    @Override
    public void onFailure(String msg, int errorCode) {
        Log.d("video load failed, error message is " + msg);
    }

    @Override
    public void onClick() {
        Log.d("video clicked");
    }

    @Override
    public void onComplete() {
        Log.d("video play completed");
    }

    @Override
    public void onClosed() {
        Log.d("video ad closed");
    }
});
```

### 混淆
对于SDK，你不需要进行任何混淆操作，只需要通过-libraryjars将整个jar包keep住。  
如果使用Admob的广告，你需要：
```java
-keep public class com.google.android.gms.ads.** {
public *;
 }
 -keep public class com.google.ads.** {
public *;
}
```

对于Facebook Audience Network，你需要：
```java
-keep class com.facebook.** { *; }
-keep interface com.facebook.** { *; }
-dontwarn com.facebook.**
```

## 4.主要类与接口
### KoalaADAgent
广告相关操作类。  
`init()`	初始化SDK  
`loadAd()`	请求广告   
`loadAdList()`	请求广告列表  
`loadInterstitialAd()`	请求插屏广告   
`registerNativeAdView()`	注册广告View（插屏广告不需要）  
`unregisterNativeAdView()`	解除原生广告绑定（插屏广告不需要）  
`showInterstitialAd()`	显示插屏广告  
`cancelAdPreload()`	取消打开广告  

### KoalaConstants
广告的相关常量，主要包括ICON的size、创意素材的size。  
`AD_IMAGE_1200x628`  广告creative大图的size，标准尺寸  
`AD_ICON_SIZE_50`    广告icon素材尺寸，50x50  
`AD_ICON_SIZE_100`   广告icon素材尺寸，100x100  
`AD_ICON_SIZE_200`   广告icon素材尺寸，200x200

### NativeAd	
Koala原生广告相关字段：  
`icon`	String类型，icon的路径  
`title`	String类型，广告的标题  
`description`	String类型，广告的描述信息  
`rate`	String类型，可能为空，广告的评分（0到5分）  
`creative`	Map类型，key为素材图的大小，value为其URL  
`callToAction`	String类型，广告CTA号召语  

## 5.错误代码说明
1001	No fill，无广告填充  
1004	Oid为空  
1005	无网络服务  
1019	SDK没有初始化  
1024	同一广告位广告请求太频繁，间隔短于1s  

