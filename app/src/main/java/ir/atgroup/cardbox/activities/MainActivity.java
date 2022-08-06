package ir.atgroup.cardbox.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pushpole.sdk.PushPole;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.atgroup.cardbox.G;
import ir.atgroup.cardbox.R;
import ir.atgroup.cardbox.utils.PermissionChecker.PermissionChecker;
import ir.tapsell.sdk.Tapsell;
import ir.tapsell.sdk.TapsellAdRequestListener;
import ir.tapsell.sdk.TapsellAdRequestOptions;
import ir.tapsell.sdk.TapsellAdShowListener;
import ir.tapsell.sdk.TapsellShowOptions;

public class MainActivity extends AppCompatActivity {

    String[] permission = new String[]{Manifest.permission.INTERNET, Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PushPole.initialize(getApplicationContext(), true);

        PermissionChecker.onCheckPermissions(this, permission, (permissionGranted, permissionDenied) -> {

            if (!permissionDenied.isEmpty()) {
                PermissionChecker.onRequestPermissions(this, permission, 0);
            }

        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    public void goSettings(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void showTab(View view) {

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_show_tab);

        Button btn_tamasha = dialog.findViewById(R.id.btn_confirm);
        btn_tamasha.setOnClickListener(view1 -> {

            dialog.dismiss();

            TapsellAdRequestOptions options = new TapsellAdRequestOptions();
            options.setCacheType(TapsellAdRequestOptions.CACHE_TYPE_CACHED);

            Tapsell.requestAd(MainActivity.this, G.TAPSELL_ZONE_ID, options, new TapsellAdRequestListener() {
                @Override
                public void onAdAvailable(String adID) {

                    TapsellShowOptions showOptions = new TapsellShowOptions();
                    showOptions.setBackDisabled(true);

                    Tapsell.showAd(MainActivity.this, G.TAPSELL_ZONE_ID, adID, showOptions, new TapsellAdShowListener() {

                        @Override
                        public void onRewarded(boolean b) {
                            Toast.makeText(MainActivity.this, getString(R.string.tnx), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String s) {
                            Toast.makeText(MainActivity.this, getString(R.string.tnx), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onClosed() {
                            Toast.makeText(MainActivity.this, getString(R.string.tnx), Toast.LENGTH_SHORT).show();
                        }
                    });


                }

                @Override
                public void onError(String s) {
                    Toast.makeText(MainActivity.this, getString(R.string.tnx), Toast.LENGTH_SHORT).show();
                }
            });

        });

        dialog.show();

    }

    @SuppressLint("IntentReset")
    public void about_me(View view) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_about);

        View view2 = dialog.findViewById(R.id.edit_name);
        view2.setOnClickListener(view1 -> {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("email"));
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{G.GMAIL_SENDER});
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            startActivity(Intent.createChooser(intent, "Send Email"));

        });

        dialog.show();
    }

    public void mycard(View view) {
        startActivity(new Intent(this, CardsViewActivity.class));
    }
}