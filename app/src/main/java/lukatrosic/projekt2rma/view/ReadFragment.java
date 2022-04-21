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
import lukatrosic.projekt2rma.model.Game;
import lukatrosic.projekt2rma.view.adapter.GameAdapter;
import lukatrosic.projekt2rma.view.adapter.GameClickListener;
import lukatrosic.projekt2rma.viewModel.GameViewModel;

public class ReadFragment extends Fragment{

    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.lista)
    ListView listView;

    private GameViewModel model;

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
        model.dohvatiPCPart().observe(getViewLifecycleOwner(), new Observer<List<Game>>() {
            @Override
            public void onChanged(@Nullable List<Game> itemList) {
                swipeRefreshLayout.setRefreshing(false);
                ((GameAdapter)listView.getAdapter()).setItemList(itemList);
                ((GameAdapter) listView.getAdapter()).refreshItem();

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

        listView.setAdapter( new GameAdapter(getActivity(), R.layout.red_liste, new GameClickListener() {
            @Override
            public void onItemClick(Game game) {
                model.setGame(game);
                ((MainActivity)getActivity()).cud();
            }
        }));
    }

    @OnClick(R.id.fab)
    public void addNew(){
        model.setGame(new Game());
        ((MainActivity)getActivity()).cud();
    }
}