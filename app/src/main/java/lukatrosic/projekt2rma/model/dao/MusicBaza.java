package lukatrosic.projekt2rma.model.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import lukatrosic.projekt2rma.model.Music;

@Database(entities = {Music.class},version = 1,exportSchema = false)
@TypeConverters({Converter.class})
public abstract class MusicBaza extends RoomDatabase{

    public abstract MusicDAO musicDAO();

    private static MusicBaza instance;

    public static MusicBaza getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    MusicBaza.class,
                    "music-baza"
            ).allowMainThreadQueries().build();
        }
        return instance;
    }
}
