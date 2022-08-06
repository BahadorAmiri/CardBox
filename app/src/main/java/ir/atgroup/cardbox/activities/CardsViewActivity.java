package ir.atgroup.cardbox.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import ir.atgroup.cardbox.R;
import ir.atgroup.cardbox.adapters.BankAdapter;
import ir.atgroup.cardbox.adapters.CardAdapter;
import ir.atgroup.cardbox.database.CardDatabase;
import ir.atgroup.cardbox.models.Card;

public class CardsViewActivity extends AppCompatActivity {

    public static String[] bank = new String[]{
            "انصار", "آرمان", "آینده", "صالحین", "صادرات",
            "تجارت", "سینا", "شهر", "سپه", "سرمایه",
            "پارسیان", "ثامن", "سامان", "رفاه", "پاسارگاد",
            "ملی", "ملت", "مهر اقتصاد", "مهر ایران", "مسکن",
            "مرکزی", "کوثر", "خاورمیانه", "کشاورزی", "کار آفرین",
            "حکمت", "ایران زمین", "دی", "اقتصاد", "گردشگری",
    };

    public static int[] logo = new int[]{
            R.drawable.bank_arman, R.drawable.bank_arman, R.drawable.bank_ayande, R.drawable.bank_salehin, R.drawable.bank_saderat,
            R.drawable.bank_tejarat, R.drawable.bank_sina, R.drawable.bank_shahr, R.drawable.bank_sepah, R.drawable.bank_sarmaye,
            R.drawable.bank_parsian, R.drawable.bank_samen, R.drawable.bank_saman, R.drawable.bank_refah, R.drawable.bank_pasargad,
            R.drawable.bank_meli, R.drawable.bank_melat, R.drawable.bank_mehr_eqtesad, R.drawable.bank_mehr, R.drawable.bank_maskan,
            R.drawable.bank_markazi, R.drawable.bank_kosar, R.drawable.bank_khavarmiane, R.drawable.bank_keshavarzi, R.drawable.bank_kar_afarin,
            R.drawable.bank_hekmat, R.drawable.bank_iranzamin, R.drawable.bank_dey, R.drawable.bank_eqtesad, R.drawable.bank_gardeshgari
    };

    RecyclerView card_recycler_view;
    CardDatabase database;
    List<Card> cardList = new ArrayList<>();
    CardAdapter cardAdapter;
    BankAdapter bankAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cards_view);
        findViews();
    }

    private void findViews() {

        card_recycler_view = findViewById(R.id.card_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        card_recycler_view.setLayoutManager(layoutManager);

        new Thread(() -> {
            database = Room.databaseBuilder(CardsViewActivity.this, CardDatabase.class, "mydb").build();
            cardList = database.dao().getAll();

            runOnUiThread(() -> {

                cardAdapter = new CardAdapter(CardsViewActivity.this, cardList, database);
                card_recycler_view.setAdapter(cardAdapter);

            });

        }).start();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void addCard(View view) {

        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_add);

        EditText edit_1 = dialog.findViewById(R.id.edit_1);
        EditText edit_2 = dialog.findViewById(R.id.edit_2);
        EditText edit_3 = dialog.findViewById(R.id.edit_3);
        EditText edit_4 = dialog.findViewById(R.id.edit_4);
        EditText edit_name = dialog.findViewById(R.id.edit_name);
        Spinner spinner1 = dialog.findViewById(R.id.spinner1);
        Button btn_confirm = dialog.findViewById(R.id.btn_confirm);

        edit_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = edit_1.getText().toString();
                if (str.length() > 4) {
                    str = str.substring(0, 4);
                    edit_1.setText(str);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edit_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = edit_2.getText().toString();
                if (str.length() > 4) {
                    str = str.substring(0, 4);
                    edit_2.setText(str);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edit_3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = edit_3.getText().toString();
                if (str.length() > 4) {
                    str = str.substring(0, 4);
                    edit_3.setText(str);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        edit_4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = edit_4.getText().toString();
                if (str.length() > 4) {
                    str = str.substring(0, 4);
                    edit_4.setText(str);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        if (bankAdapter == null) {
            bankAdapter = new BankAdapter(this, bank);
        }
        spinner1.setAdapter(bankAdapter);


        btn_confirm.setOnClickListener(view1 -> {

            dialog.dismiss();

            String code_number = edit_1.getText().toString() + edit_2.getText().toString() + edit_3.getText().toString() + edit_4.getText().toString();

            if (code_number.length() == 16) {

                List<Card> cards = new ArrayList<>();
                cards.add(new Card(edit_name.getText().toString(), spinner1.getSelectedItemPosition(), code_number));
                cardAdapter.addCard(cards);

                Toast.makeText(this, getString(R.string.cart_created), Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(this, getString(R.string.card_16num), Toast.LENGTH_SHORT).show();

            }

        });

        dialog.show();

    }
}
