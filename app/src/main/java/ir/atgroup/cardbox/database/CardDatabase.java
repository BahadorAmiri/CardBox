package ir.atgroup.cardbox.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ir.atgroup.cardbox.models.Card;

@Database(entities = {Card.class}, version = 1,exportSchema = false)
public abstract class CardDatabase extends RoomDatabase {

    public abstract CardDAO dao();

}