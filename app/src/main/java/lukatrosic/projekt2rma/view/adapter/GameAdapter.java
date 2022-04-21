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
import lukatrosic.projekt2rma.model.Game;

public class GameAdapter extends ArrayAdapter<Game>{

    private List<Game> itemList;
    private GameClickListener gameClickListener;
    private int resource;
    private Context context;

    public GameAdapter(@NonNull Context context, int resource, GameClickListener gameClickListener) {
        super(context, resource);

        this.resource = resource;
        this.context = context;
        this.gameClickListener = gameClickListener;
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
        Game game;
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

            game = getItem(position);

            if (game != null) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(game.getDatumIzlaska());

                viewHolder.id.setText(String.valueOf(game.getId()));
                viewHolder.name.setText(game.getName());
                viewHolder.genre.setText(context.getResources().getStringArray(R.array.genre)[game.getGenre()]);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd. MM. yyyy.");
                try {
                    viewHolder.datumIzlaska.setText(simpleDateFormat.format(game.getDatumIzlaska()));
                }catch (Exception e){
                    viewHolder.datumIzlaska.setText("");
                }


                //viewHolder.datumKupnje.setOnTouchListener(this::onTouch);
                //viewHolder.datumKupnje.setMovementMethod(ScrollingMovementMethod.getInstance());

                if (game.getPutanjaSlika() == null) {
                    Picasso.get().load(R.drawable.nepoznato).fit().centerCrop().into(viewHolder.putanjaSlika);
                } else {
                    Picasso.get().load(game.getPutanjaSlika()).fit().centerCrop().into(viewHolder.putanjaSlika);
                }
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) { gameClickListener.onItemClick(game); }
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
    public Game getItem(int position) {
        return itemList.get(position);
    }

    public void setItemList(List<Game> itemList) {
        this.itemList = itemList;
    }

    public void refreshItem() {
        notifyDataSetChanged();
    }

}

