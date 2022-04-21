package lukatrosic.projekt2rma.model.dao;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import lukatrosic.projekt2rma.model.Game;

@Dao
public interface GameDAO {

    @Query("select * from Game order by genre")
    LiveData<List<Game>> dohvatiGame();

    @Insert
    void dodajNoviGame(Game game);

    @Update
    void promjeniGame(Game game);

    @Delete
    void obrisiGame(Game game);
}