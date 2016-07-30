package koala.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by leotse on 16/7/29.
 */

public class KoalaAdDemoUtils {

    private static Handler BackHandler = null;
    private static Handler UIHandler = null;

    public static Handler getBackHandler() {
        if (BackHandler != null) {
            return BackHandler;
        }
        HandlerThread localHandlerThread = new HandlerThread("KoalaAdDemoBack");
        localHandlerThread.start();
        BackHandler = new Handler(localHandlerThread.getLooper());
        return BackHandler;
    }

    public static Handler getUIHandler(){
        if (UIHandler != null){
            return UIHandler;
        }
        UIHandler = new Handler();
        return UIHandler;
    }

    public static Bitmap loadBitmap(String url) {
        Bitmap bitmap = null;

        InputStream in;
        try {
            in = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
