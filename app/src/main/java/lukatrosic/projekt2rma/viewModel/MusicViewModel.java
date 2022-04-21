package lukatrosic.projekt2rma.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import lukatrosic.projekt2rma.model.Music;
import lukatrosic.projekt2rma.model.dao.MusicBaza;
import lukatrosic.projekt2rma.model.dao.MusicDAO;

public class MusicViewModel extends AndroidViewModel{

    MusicDAO musicDAO;
    private Music music;

    public MusicDAO getMusicDAODAO() {
        return musicDAO;
    }

    public void setMusicDAO(MusicDAO musicDAO) {
        this.musicDAO = musicDAO;
    }

    private LiveData<List<Music>> musics;

    public void setMusic(Music music) {
        this.music = music;
    }

    public Music getMusic() {
        return music;
    }

    public MusicViewModel(@NonNull Application application) {
        super(application);
        musicDAO = MusicBaza.getInstance(application.getApplicationContext()).musicDAO();
    }

    public LiveData<List<Music>> dohvatiPCPart(){
        musics = musicDAO.dohvatiMusic();
        return musics;
    }

    public void dodajNoviMusic(){ musicDAO.dodajNoviMusic(music); }

    public void promjeniMusic(){ musicDAO.promjeniMusic(music); }

    public void obrisiMusic(){
        musicDAO.obrisiMusic(music);
    }
}

