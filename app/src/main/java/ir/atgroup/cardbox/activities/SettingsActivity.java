package ir.atgroup.cardbox.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.atgroup.cardbox.G;
import ir.atgroup.cardbox.R;
import ir.atgroup.cardbox.utils.SharedHelper.SharedHelper;

public class SettingsActivity extends AppCompatActivity {

    TextView txt_pin, txt_err;
    SharedHelper sharedHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findViews();
    }

    private void findViews() {
        sharedHelper = new SharedHelper(this, G.PASSWORD_KEY);
        txt_err = findViewById(R.id.txt_err);
        txt_pin = findViewById(R.id.txt_pin);

        txt_pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    public void printNum(View view) {
        switch (view.getId()) {
            case R.id.v1:
                txt_pin.setText(txt_pin.getText().toString() + "1");
                break;
            case R.id.v2:
                txt_pin.setText(txt_pin.getText().toString() + "2");
                break;
            case R.id.v3:
                txt_pin.setText(txt_pin.getText().toString() + "3");
                break;
            case R.id.v4:
                txt_pin.setText(txt_pin.getText().toString() + "4");
                break;
            case R.id.v5:
                txt_pin.setText(txt_pin.getText().toString() + "5");
                break;
            case R.id.v6:
                txt_pin.setText(txt_pin.getText().toString() + "6");
                break;
            case R.id.v7:
                txt_pin.setText(txt_pin.getText().toString() + "7");
                break;
            case R.id.v8:
                txt_pin.setText(txt_pin.getText().toString() + "8");
                break;
            case R.id.v9:
                txt_pin.setText(txt_pin.getText().toString() + "9");
                break;
            case R.id.v0:
                txt_pin.setText(txt_pin.getText().toString() + "0");
                break;
            default:
                break;
        }
    }

    public void ok(View view) {

        if (txt_pin.getText().length() > 3) {

            sharedHelper.insert(G.PASSWORD_KEY, G._setHash(txt_pin.getText().toString()));
            finish();

        } else {
            txt_err.setText(R.string.pass_plus4);
        }

    }

    public void remove(View view) {
        if (txt_pin.getText().length() > 0) {
            txt_pin.setText(txt_pin.getText().subSequence(0, (txt_pin.getText().length() - 1)));
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

}
