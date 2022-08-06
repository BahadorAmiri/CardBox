package ir.atgroup.cardbox.models;

import android.graphics.drawable.Drawable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Card {

    @PrimaryKey(autoGenerate = true)
    int id;
    @ColumnInfo(name = "name")
    String name;
    @ColumnInfo(name = "bank")
    int bank;
    @ColumnInfo(name = "code")
    String code;

    public Card(String name, int bank, String code) {
        this.name = name;
        this.bank = bank;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBank() {
        return bank;
    }

    public String getCode() {
        return code;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBank(int type) {
        this.bank = type;
    }

    public void setCode(String number) {
        this.code = number;
    }

}
