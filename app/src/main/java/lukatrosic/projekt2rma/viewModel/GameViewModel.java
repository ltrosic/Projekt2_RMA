package lukatrosic.projekt2rma.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import lukatrosic.projekt2rma.model.Game;
import lukatrosic.projekt2rma.model.dao.GameBaza;
import lukatrosic.projekt2rma.model.dao.GameDAO;

public class GameViewModel extends AndroidViewModel{

    GameDAO gameDAO;
    private Game game;

    public GameDAO getGameDAO() {
        return gameDAO;
    }

    public void setGameDAO(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    private LiveData<List<Game>> musics;

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public GameViewModel(@NonNull Application application) {
        super(application);
        gameDAO = GameBaza.getInstance(application.getApplicationContext()).musicDAO();
    }

    public LiveData<List<Game>> dohvatiPCPart(){
        musics = gameDAO.dohvatiGame();
        return musics;
    }

    public void dodajNoviGame(){ gameDAO.dodajNoviGame(game); }

    public void promjeniGame(){ gameDAO.promjeniGame(game); }

    public void obrisiGame(){
        gameDAO.obrisiGame(game);
    }
}

