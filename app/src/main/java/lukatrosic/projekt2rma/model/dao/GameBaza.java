package lukatrosic.projekt2rma.model.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import lukatrosic.projekt2rma.model.Game;

@Database(entities = {Game.class},version = 1,exportSchema = false)
@TypeConverters({Converter.class})
public abstract class GameBaza extends RoomDatabase{

    public abstract GameDAO musicDAO();

    private static GameBaza instance;

    public static GameBaza getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    GameBaza.class,
                    "game-baza"
            ).allowMainThreadQueries().build();
        }
        return instance;
    }
}
