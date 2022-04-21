package lukatrosic.projekt2rma.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import lukatrosic.projekt2rma.R;
import lukatrosic.projekt2rma.view.adapter.MusicAdapter;
import lukatrosic.projekt2rma.view.adapter.MusicClickListener;
import lukatrosic.projekt2rma.model.Music;
import lukatrosic.projekt2rma.viewModel.MusicViewModel;

public class ReadFragment extends Fragment{

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.lista)
    ListView listView;

    private MusicViewModel model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_read,
                container, false);
        ButterKnife.bind(this,view);

        model = ((MainActivity)getActivity()).getModel();

        defineList();
        defineSwipe();
        refeshData();

        return view;
    }

    private void refeshData(){
        model.dohvatiPCPart().observe(getViewLifecycleOwner(), new Observer<List<Music>>() {
            @Override
            public void onChanged(@Nullable List<Music> itemList) {
                swipeRefreshLayout.setRefreshing(false);
                ((MusicAdapter)listView.getAdapter()).setItemList(itemList);
                ((MusicAdapter) listView.getAdapter()).refreshItem();

            }
        });
    }

    private void defineSwipe() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refeshData();
            }
        });

    }

    private void defineList() {

        listView.setAdapter( new MusicAdapter(getActivity(), R.layout.red_liste, new MusicClickListener() {
            @Override
            public void onItemClick(Music music) {
                model.setMusic(music);
                ((MainActivity)getActivity()).cud();
            }
        }));
    }

    @OnClick(R.id.fab)
    public void addNew(){
        model.setMusic(new Music());
        ((MainActivity)getActivity()).cud();
    }
}