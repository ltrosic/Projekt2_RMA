package lukatrosic.projekt2rma.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import lukatrosic.projekt2rma.R;
import lukatrosic.projekt2rma.model.Music;

public class MusicAdapter extends ArrayAdapter<Music>{

    private List<Music> itemList;
    private MusicClickListener musicClickListener;
    private int resource;
    private Context context;

    public MusicAdapter(@NonNull Context context, int resource, MusicClickListener musicClickListener) {
        super(context, resource);

        this.resource = resource;
        this.context = context;
        this.musicClickListener = musicClickListener;
    }

    private static class ViewHolder {

        private TextView id;
        private TextView name;
        private TextView genre;
        private ImageView putanjaSlika;
        private TextView datumIzlaska;


    }

    @androidx.annotation.NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @androidx.annotation.NonNull ViewGroup parent) {

        View view = convertView;
        Music music;
        ViewHolder viewHolder;

        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (inflater != null) {
                view = inflater.inflate(this.resource, null);

                viewHolder.id = view.findViewById(R.id.id);
                viewHolder.name = view.findViewById(R.id.name);
                viewHolder.putanjaSlika = view.findViewById(R.id.putanjaSlika);
                viewHolder.genre = view.findViewById(R.id.genre);
                viewHolder.datumIzlaska = view.findViewById(R.id.datumIzlaska);

                //viewHolder.component.setOnTouchListener(this::onTouch);
                //viewHolder.component.setMovementMethod(ScrollingMovementMethod.getInstance());

            } else {
                viewHolder = (ViewHolder) view.getTag();

            }

            music = getItem(position);

            if (music != null) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(music.getDatumIzlaska());

                viewHolder.id.setText(String.valueOf(music.getId()));
                viewHolder.name.setText(music.getName());
                viewHolder.genre.setText(context.getResources().getStringArray(R.array.genre)[music.getGenre()]);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd. MM. yyyy.");
                try {
                    viewHolder.datumIzlaska.setText(simpleDateFormat.format(music.getDatumIzlaska()));
                }catch (Exception e){
                    viewHolder.datumIzlaska.setText("");
                }


                //viewHolder.datumKupnje.setOnTouchListener(this::onTouch);
                //viewHolder.datumKupnje.setMovementMethod(ScrollingMovementMethod.getInstance());

                if (music.getPutanjaSlika() == null) {
                    Picasso.get().load(R.drawable.nepoznato).fit().centerCrop().into(viewHolder.putanjaSlika);
                } else {
                    Picasso.get().load(music.getPutanjaSlika()).fit().centerCrop().into(viewHolder.putanjaSlika);
                }
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { musicClickListener.onItemClick(music); }
            });
        }
        return view;
    }

    @Override
    public int getCount() {
        return itemList == null ? 0 : itemList.size();
    }

    @Nullable
    @Override
    public Music getItem(int position) {
        return itemList.get(position);
    }

    public void setItemList(List<Music> itemList) {
        this.itemList = itemList;
    }

    public void refreshItem() {
        notifyDataSetChanged();
    }

}

