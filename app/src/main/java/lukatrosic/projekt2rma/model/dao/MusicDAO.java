package lukatrosic.projekt2rma.model.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import lukatrosic.projekt2rma.model.Music;

@Dao
public interface MusicDAO {

    @Query("select * from music order by genre")
    LiveData<List<Music>> dohvatiMusic();

    @Insert
    void dodajNoviMusic(Music music);

    @Update
    void promjeniMusic(Music music);

    @Delete
    void obrisiMusic(Music music);
}