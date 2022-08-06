/*
 * ~  Copyright (c) 8/4/21 12:37 PM
 * ~  Dev : Amir Bahador , Amiri
 * ~  City : Iran / Abadan
 * ~  time & date : 5/27/21 7:13 PM
 * ~  email : abadan918@gmail.com
 */

package ir.atgroup.cardbox.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ir.atgroup.cardbox.R;

public class BankAdapter extends ArrayAdapter {

    Context context;
    String[] banks;

    public BankAdapter(Context context, String[] banks) {
        super(context, 0, banks);
        this.context = context;
        this.banks = banks;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MyHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bank, parent, false);
            holder = new MyHolder();
            holder.bank_name = convertView.findViewById(R.id.bank_name);
            convertView.setTag(holder);
        } else {
            holder = (MyHolder) convertView.getTag();
        }
        holder.bank_name.setText(banks[position]);

        return convertView;
    }

    private static class MyHolder {

        TextView bank_name;

    }

}
