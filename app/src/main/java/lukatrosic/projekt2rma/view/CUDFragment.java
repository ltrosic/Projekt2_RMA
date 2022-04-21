package lukatrosic.projekt2rma.view;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import lukatrosic.projekt2rma.R;
import lukatrosic.projekt2rma.model.Game;
import lukatrosic.projekt2rma.viewModel.GameViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CUDFragment extends Fragment {

    static final int SLIKANJE = 1;

    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.genre)
    Spinner genre;
    @BindView(R.id.datumIzlaska)
    DatePicker datumIzlaska;
    @BindView(R.id.slikaCUD)
    ImageView slikaCUD;
    @BindView(R.id.novaGlazba)
    Button novaGlazba;
    @BindView(R.id.uslikajGlazbu)
    Button uslikaj;
    @BindView(R.id.promjenaGlazbe)
    Button promjenaGlazba;
    @BindView(R.id.obrisiGlazbu)
    Button obrisiGlazbu;

    GameViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cud,
                container, false);
        ButterKnife.bind(this, view);
        model = ((MainActivity) getActivity()).getModel();
        Picasso picasso = Picasso.get();
        picasso.setIndicatorsEnabled(true);
        picasso.setLoggingEnabled(true);

        picasso.load(R.drawable.nepoznato).fit().centerCrop().into(slikaCUD);


        if (model.getGame().getId() == 0) {
            definirajNovuKomponentu();
            return view;
        }
        name.setText(model.getGame().getName());
        genre.setSelection(model.getGame().getGenre());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(model.getGame().getDatumIzlaska());
        datumIzlaska.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        definirajPromjenaBrisanjeOsoba();

        return view;
    }

    private void definirajNovuKomponentu() {
        promjenaGlazba.setVisibility(View.GONE);
        obrisiGlazbu.setVisibility(View.GONE);
        uslikaj.setVisibility(View.GONE);
        novaGlazba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novaKomponenta();
            }
        });
    }

    private void novaKomponenta() {
        model.getGame().setName(name.getText().toString());
        model.getGame().setGenre(genre.getSelectedItemPosition());
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, datumIzlaska.getDayOfMonth());
        c.set(Calendar.MONTH, datumIzlaska.getMonth());
        c.set(Calendar.YEAR, datumIzlaska.getYear());
        model.getGame().setDatumIzlaska(c.getTime());
        model.dodajNoviGame();
        nazad();

    }

    private void definirajPromjenaBrisanjeOsoba() {
        Game o = model.getGame();
        novaGlazba.setVisibility(View.GONE);
        name.setText(o.getName());
        genre.setSelection(o.getGenre());
        Log.d("Putanja slika", "->" + o.getPutanjaSlika());
        if (o.getPutanjaSlika() != null) {
            Picasso.get().load(o.getPutanjaSlika()).fit().centerCrop().into(slikaCUD);
        }

        promjenaGlazba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promjenaKomponente();
            }
        });

        obrisiGlazbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obrisiKomponentu();
            }
        });

        uslikaj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uslikajKomponentu();
            }
        });
    }

    private void uslikajKomponentu() {
        Intent uslikajIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(uslikajIntent.resolveActivity(getActivity().getPackageManager())==null){
            //poruke korisniku
            return;
        }

        File slika= null;
        try{
            slika = kreirajDatotekuSlike();
        }catch (IOException e){
            //poruke korisniku
            return;
        }

        if(slika==null){
            //poruke korisniku
            return;
        }

        Uri slikaUri = FileProvider.getUriForFile(getActivity(),
                "lukatrosic.provider",
                slika);
        uslikajIntent.putExtra(MediaStore.EXTRA_OUTPUT,slikaUri);
        startActivityForResult(uslikajIntent,SLIKANJE);

    }

    private File kreirajDatotekuSlike() throws IOException {
        String naziv = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "_osoba";
        File dir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File datoteka = File.createTempFile(naziv,".jpg",dir);
        model.getGame().setPutanjaSlika("file:" + datoteka.getAbsolutePath());
        return datoteka;
    }

    private void promjenaKomponente(){
        model.getGame().setName(name.getText().toString());
        model.getGame().setGenre(genre.getSelectedItemPosition());
        //model.getPcPart().setDatumKupnje(datumKupnje.getText().toString());
        model.promjeniGame();
        nazad();
    }

    private void obrisiKomponentu(){
        model.obrisiGame();
        nazad();
    }

    @OnClick(R.id.nazad)
    public void nazad(){
        ((MainActivity)getActivity()).read();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SLIKANJE && resultCode == Activity.RESULT_OK){
            model.promjeniGame();
            Picasso.get().load(model.getGame().getPutanjaSlika()).fit().centerCrop().into(slikaCUD);
        }
    }
}