package ir.atgroup.cardbox.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

public class PasswordActivity extends AppCompatActivity {

    TextView txt_pin, txt_err;
    SharedHelper sharedHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        findViews();
    }

    private void findViews() {
        sharedHelper = new SharedHelper(this, G.PASSWORD_KEY);
        txt_err = findViewById(R.id.txt_err);
        txt_pin = findViewById(R.id.txt_pin);

        if (sharedHelper.read(G.PASSWORD_KEY).equals("")) {
            txt_err.setText(R.string.create_pass);
        } else {
            txt_err.setText(R.string.insert_pass);
        }

        txt_pin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txt_err.setText("");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
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
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    public void ok(View view) {

        if (txt_pin.getText().length() > 3) {

            String key = G._getHash(sharedHelper.read(G.PASSWORD_KEY));

            if (key.equals("")) {
                sharedHelper.insert(G.PASSWORD_KEY, G._setHash(txt_pin.getText().toString()));
                goToMain();
            } else {
                if (key.equals(txt_pin.getText().toString())) {
                    goToMain();
                } else {
                    txt_pin.setText("");
                    txt_err.setText(R.string.pass_eshtebah);
                }
            }
        } else {
            txt_err.setText(R.string.pass_plus4);
        }

    }

    private void goToMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public void remove(View view) {
        if (txt_pin.getText().length() > 0) {
            txt_pin.setText(txt_pin.getText().subSequence(0, (txt_pin.getText().length() - 1)));
        }
    }
}
