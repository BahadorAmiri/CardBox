package ir.atgroup.cardbox.adapters;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import ir.atgroup.cardbox.R;
import ir.atgroup.cardbox.activities.CardsViewActivity;
import ir.atgroup.cardbox.database.CardDatabase;
import ir.atgroup.cardbox.models.Card;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.MyHolder> {

    Context context;
    List<Card> cards;
    CardDatabase database;

    public CardAdapter(Context context, List<Card> cards, CardDatabase database) {
        this.context = context;
        this.cards = cards;
        this.database = database;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.item_card, parent, false));
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.txt_name.setText(cards.get(position).getName());
        holder.txt_number_code.setText(getCardNumber(cards.get(position).getCode()));

        holder.icon_logo.setImageResource(CardsViewActivity.logo[cards.get(position).getBank()]);

        holder.icon_copy.setOnClickListener(view -> {

            ClipData data = ClipData.newPlainText(context.getString(R.string.app_name), cards.get(position).getCode());
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboardManager.setPrimaryClip(data);

            Toast.makeText(context, context.getString(R.string.copied), Toast.LENGTH_SHORT).show();

        });

        holder.icon_share.setOnClickListener(view -> {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/*");
            String share_text = "حساب به نام : " +
                    cards.get(position).getName() +
                    "\n" +
                    "شماره کارت : " +
                    "\n" +
                    holder.txt_number_code.getText().toString() +
                    "\n" +
                    "بانک : " +
                    CardsViewActivity.bank[cards.get(position).getBank()] +
                    "\n" +
                    "\n" +
                    "دانلود نرم افزار کارت من از مایکت" +
                    "\n" +
                    "link";
            intent.putExtra(Intent.EXTRA_TEXT, share_text);
            context.startActivity(Intent.createChooser(intent, context.getString(R.string.app_name)));

        });

        holder.txt_pic.setOnClickListener(view -> createPicture(holder));

        holder.txt_delete.setOnClickListener(view -> deleteCard(position));

    }

    private void createPicture(MyHolder holder) {

        holder.txt_delete.setVisibility(View.INVISIBLE);
        holder.icon_share.setVisibility(View.INVISIBLE);
        holder.icon_copy.setVisibility(View.INVISIBLE);
        holder.txt_pic.setVisibility(View.INVISIBLE);

        holder.app_logo.setVisibility(View.VISIBLE);
        holder.app_name.setVisibility(View.VISIBLE);
        holder.hoshdar.setVisibility(View.VISIBLE);

        holder.cards_items_CardView.setDrawingCacheEnabled(true);
        holder.cards_items_CardView.buildDrawingCache(true);

        Bitmap bitmap = Bitmap.createBitmap(holder.cards_items_CardView.getDrawingCache());
        holder.cards_items_CardView.setDrawingCacheEnabled(false);
        if (bitmap != null) {
            saveBitmapToStorage(bitmap);
        }
        holder.txt_delete.setVisibility(View.VISIBLE);
        holder.icon_share.setVisibility(View.VISIBLE);
        holder.icon_copy.setVisibility(View.VISIBLE);
        holder.txt_pic.setVisibility(View.VISIBLE);
        holder.app_logo.setVisibility(View.INVISIBLE);
        holder.app_name.setVisibility(View.INVISIBLE);
        holder.hoshdar.setVisibility(View.INVISIBLE);
        holder.cards_items_CardView.setDrawingCacheEnabled(false);

    }

    private void saveBitmapToStorage(Bitmap bitmap) {

        String FILE_NAME = context.getString(R.string.app_en_name) + " - ";
        String MAINROOTDIR = Environment.getExternalStorageDirectory()
                + File.separator + context.getString(R.string.app_name);


        Calendar c = Calendar.getInstance();
        Random r = new Random();
        String number = String.valueOf(r.nextInt(1000));
        String FULLNAME = FILE_NAME + c.get(Calendar.MILLISECOND) + number + ".png";

        File pat = new File(MAINROOTDIR + File.separator);
        if (!pat.exists()) {
            pat.mkdir();
        }
        File file = new File(pat, FULLNAME);
        try {
            OutputStream os = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
            MediaScannerConnection.scanFile(context, new String[]{file.toString()}, null, null);

            Toast.makeText(context, context.getString(R.string.image_created), Toast.LENGTH_SHORT).show();

        } catch (FileNotFoundException e) {
            Toast.makeText(context, context.getString(R.string.image_notcreated), Toast.LENGTH_SHORT).show();
        }

    }

    private String getCardNumber(String str) {

        StringBuilder _4code = new StringBuilder();

        for (int i = 0; i < str.length(); ) {
            _4code.append(str.substring(i, (i + 4)));
            if (i < 11) {
                _4code.append("  -  ");
            }
            i = i + 4;
        }

        return _4code.toString();
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }


    public static class MyHolder extends RecyclerView.ViewHolder {

        AppCompatTextView txt_number_code, txt_name, txt_delete, txt_pic, app_name, hoshdar;
        AppCompatImageView icon_logo, icon_copy, icon_share, app_logo;
        CardView cards_items_CardView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            cards_items_CardView = itemView.findViewById(R.id.cards_items_CardView);
            txt_number_code = itemView.findViewById(R.id.txt_number_code);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_delete = itemView.findViewById(R.id.txt_delete);
            txt_pic = itemView.findViewById(R.id.txt_pic);
            icon_logo = itemView.findViewById(R.id.bank_logo);
            icon_copy = itemView.findViewById(R.id.icon_copy);
            icon_share = itemView.findViewById(R.id.icon_share);
            app_logo = itemView.findViewById(R.id.app_logo);
            app_name = itemView.findViewById(R.id.app_name);
            hoshdar = itemView.findViewById(R.id.hoshdar);
        }
    }

    public void deleteCard(int position) {
        new Thread(() -> {

            database.dao().delete(cards.get(position));
            cards.remove(position);
            ((CardsViewActivity) context).runOnUiThread(() -> notifyItemRemoved(position));

        }).start();

    }

    public void addCard(List<Card> cards_1) {
        new Thread(() -> {

            database.dao().insert(cards_1);
            this.cards.addAll(cards_1);
            ((CardsViewActivity) context).runOnUiThread(this::notifyDataSetChanged);

        }).start();
    }

}
