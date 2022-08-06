package ir.atgroup.cardbox;

import android.app.Application;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import ir.tapsell.sdk.Tapsell;

public class G extends Application {


    public static String TAPSELL_KEY = "dejmtdffbgkahkdlhgmpkikpgbrmrmjdlrejaehmpjamcrjratebmmgcimjrlnsgtdnmhl";
    public static String TAPSELL_ZONE_ID = "6103472924c82d5311e633b4";
    public static String PASSWORD_KEY = "PASSWORD_KEY";

    public static String GMAIL_SENDER = "bahadoramiri.report@gmail.com";

    @Override
    public void onCreate() {
        super.onCreate();
        Tapsell.initialize(this, TAPSELL_KEY);

        setFont();
    }

    public static String _getHash(String pass) {

        pass = pass.replace("!", "1");
        pass = pass.replace("@@", "2");
        pass = pass.replace("QQ", "9");
        pass = pass.replace("O", "0");
        pass = pass.replace("TT", "7");

        return pass;
    }

    public static String _setHash(String pass) {

        pass = pass.replace("1", "!");
        pass = pass.replace("2", "@@");
        pass = pass.replace("9", "QQ");
        pass = pass.replace("0", "O");
        pass = pass.replace("7", "TT");

        return pass;
    }

    private void setFont() {
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Dana-Light.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
    }

}
