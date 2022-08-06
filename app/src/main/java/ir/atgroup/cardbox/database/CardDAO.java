package ir.atgroup.cardbox.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import ir.atgroup.cardbox.models.Card;

@Dao
public interface CardDAO {

    @Query("SELECT * FROM Card")
    List<Card> getAll();

    @Insert
    void insert(List<Card> cards);

    @Delete
    void delete(Card card);

}
