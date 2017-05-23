# Koala SDK Instructions

## 1.Notice for use
Koala advertising SDK achieves encapsulation for requests, impressions, clicks and installations transformation of Android  platform advertising, aims for providing stable, convenient and reliable services for the realization of advertising of overseas mobile Internet products.

Before using SDK, you need to apply for `App-key` and `Secret-key` which can uniquely identify a APP on Koala advertising platform.

The current supported ad formats are
>1.Native ads: icon, material big picture (1200x628)                                                            
2.Banner ads:standard size 320x50                            
3.Interstitial ads: standard interstitial ads                 
4.Video ads: 320x250 (please consult our operators before use)        
5.Advertising wall: provide rich ads (editor‘picks, games, tools)        

## 2.App-key and Oid application
App key and secret key are using uniquely to identify a APP.
Oid is using uniquely to identify specific ad position in APP.
Please contact to our operators if you need any help.

## 3.Instructions
### Add the appropriate jar package
You need to import our koala SDK jar package to your project;

If you need to use Facebook ads, you need to import Facebook audience network SDK to your project. In Android Studio, you need to join in build.gradle's dependencies
```java
	compile 'com.facebook.android:audience-network-sdk:4.+'
```

If you need to use AdMob ads, you need to add to build.gradle's dependencies in Android Studio
```java
	compile 'com.google.android.gms:play-services-ads:8.+'
```

And confirm the class under the imported `com.google.android.gms.ads.identifier` package to make sure that the user's GAID is obtained

### Configuration file (AndroidManifest.xml)
At first, you need to add permissions
```java
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```

You need to add appkey and secretkey into AndroidManifest.xml
```java
<meta-data
    android:name="AGENT_APPKEY"
    android:value="34c0ab0888e7a42c8b5882e1af3d71f9" />
<meta-data
    android:name="AGENT_SECRET"
    android:value="ca00979da70d117059562f1e85ef06ee" />
```

In order to filter the installed applications to enhance ad effects, you need to join
```java
<receiver android:name="com.kika.pluto.filter.KoalaAppInstallReceiver" >
    <intent-filter>
        <action android:name="android.intent.action.PACKAGE_ADDED" />
	   <action android:name="android.intent.action.PACKAGE_REMOVED" />
        <data android:scheme="package" />
    </intent-filter>
</receiver>
```

If you need to use the Facebook interstitial ads, you need to add
```java
<activity
    android:name="com.facebook.ads.InterstitialAdActivity"
    android:configChanges="keyboardHidden|orientation|screenSize">
</activity>
```

If you need to use the Koala interstitial ads, you need to add
```java
<activity
android:name="com.xinmei.adsdk.nativeads.NativeInterstitialAdActivity"
    android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
    android:theme="@android:style/Theme.NoTitleBar.Fullscreen" >
</activity>
```

If you need to use the Admob ads, you need to add
```java
<activity
 android:name="com.google.android.gms.ads.AdActivity"
    android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
    android:theme="@android:style/Theme.Translucent" />
```

If you need to use the Koala advertising wall, you need to add
```java
<activity
    android:name="com.xinmei.adsdk.nativeads.AppWall"
    android:configChanges="keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize"
    android:theme="@android:style/Theme.NoTitleBar.Fullscreen" >
</activity>
```

**Due to we need to get gaid for ad transformation attribution, you need to join**
```java
<meta-data
    android:name="com.google.android.gms.version"
    android:value="@integer/google_play_services_version" />
```

### Initialize SDK
Before you use the SDK request advertisement, be sure to initialize Koala SDK.
```java
KoalaADAgent.init(Context context);
```
Initialization timing is recommended in application first boot. You don't have to perform this operation every time the user opens the application.

### Request native ads
1-Needs to create ADRequestSetting objects to configure the advertisements. Other can be specified are icon's size(setIconSize，the optional parameters are 50x50, 100x100, and 200x200), Advertising creative big picture's size(setImageSize，currently only 1200x628 is supported);            
2-Load ads:KoalaADAgent.loadAd();        
3-If the ad request is successful, you can get the data for the ad and assemble it into a View as needed;       
4-Register view and ads:KoalaADAgent.registerNativeAdView();        
5-SDK handles the click event of the view and calls back;     
6-When the ad is no longer used, remove the ad bindings:KoalaADAgent.unregisterNativeAdView();     

### Request interstitial ads
1-Creates ADRequestSetting objects that configure the ads. The ad bit OID is an essential parameter;           
2-Request interstitial ads:KoalaADAgent.loadInterstitialAd();         
3-Ask for a successful ad campaign at the right time:KoalaADAgent.showInterstitialAd();              
     
### Banner ads
1.Add in the layout file that needs to display banner ads
```java
<com.xinmei.adsdk.nativeads.KoalaBannerAdView 
    android:id = "@+id/koalaAd"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:gravity="center_vertical|center_horizontal"
></com.xinmei.adsdk.nativeads.KoalaBannerAdView>
```

2.Create a KoalaBannerAdView object in the function you want to load banner ads, then load the banner ads directly
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

### Video ads
1.Add in the layout file that needs to display video ads
```java
<com.kika.pluto.ad.KoalaVideoAdView
     android:id = "@+id/koalaVideoAd"
     android:layout_width="320dp"
     android:layout_height="250dp"
     android:gravity="center_vertical|center_horizontal"
 ></com.kika.pluto.ad.KoalaVideoAdView>
```

2.Create a KoalaVideoAdView object, load video ads
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

### Advertising wall
Direct invoke
```
KoalaADAgent.startAppWall();
```

### Obfuscation
For SDK, you don't need to do any obfuscation, and you just need to keep the entire jar package through -libraryjars.

If use video ads, you need to
```java
-keep public class com.kika.pluto.ad.KoalaVideoAdView {*;}
-keep public class com.kika.pluto.ad.KoalaVideoAdView$* {*;}
```

If use Admob's ads, you need to
```java
-keep public class com.google.android.gms.ads.** {
public *;
 }
 -keep public class com.google.ads.** {
public *;
}
```

As for Facebook Audience Network, you need to
```java
-keep class com.facebook.** { *; }
-keep interface com.facebook.** { *; }
-dontwarn com.facebook.**
```

## 4.Main classes and interfaces
### KoalaADAgent
Advertising related operations. 
`init()`		   Initialize SDK 
`loadAd()`		   Request advertisement   
`loadAdList()`		   Request advertisement list  
`loadInterstitialAd()`	   Request interstitial ads       
`registerNativeAdView()`   Register native ads view（interstitial ads are not required) 
`unregisterNativeAdView()` Unregister native ads view（interstitial ads are not required)  
`showInterstitialAd()`	   Show interstitial ads    	    
`cancelAdPreload()`	   Cancel ads preload  


### KoalaConstants
Advertising related constants, including the ICON size, creative material size.
`AD_IMAGE_1200x628`  Advertising creative big picture's standard size 
`AD_ICON_SIZE_50`    Advertising icon material size,50x50
`AD_ICON_SIZE_100`   Advertising icon material size,100x100
`AD_ICON_SIZE_200`   Advertising icon material size,200x200 


### NativeAd	
Koala native ads related fields
`icon`	        String type, icon path    
`title`		String type, ads title  
`description`	String type, ads description   
`rate`		String type, may be empty,ads rate(0 to 5 points)    
`creative`	Map type, key is the size of the material map, and value is its URL    
`callToAction`	String type, ads call to action calling language  


## 5.Error code description
1001	No fill  	
1004 	Oid is empty 	
1005	No network service  	
1019    SDK is not initialized  	
1024	The same ad ad request is too frequent, the interval is shorter than 1s	

## 6.Other considerations	
1.If you use ads for Koala ad sources when testing, use the Android Google Service Play mobile phone to test, otherwise there will be no return advertising;
2.Domestic advertising offer is less,so you may frequently see a few ads when testing, we recommend using VPN to test;
3.If you have special requirements for advertising source (priority or placement ID of advertising source), please contact the operators concerned;
4.If you need AdChoicesView when using Facebook native ads, you can get it from the following code:
```java
AdChoicesView adChoicesView = new AdChoicesView(context, facebookNativeAd, true);
```
5.In order to improve the advertising effect, please do not repeat the initialization of SDK. You only need to initialize it for the first time when you start the App;
