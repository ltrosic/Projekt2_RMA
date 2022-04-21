package lukatrosic.projekt2rma.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import lukatrosic.projekt2rma.R;
import lukatrosic.projekt2rma.viewModel.GameViewModel;

public class MainActivity extends AppCompatActivity {

    private GameViewModel model;

    public GameViewModel getModel() {
        return model;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        model = ViewModelProviders.of(this).get(GameViewModel.class);
        read();

    }

    public void read(){
        setFragment( new ReadFragment());
    };

    public void cud(){
        setFragment( new CUDFragment());
    };

    private void setFragment(Fragment fragment){
        //fragment.setEnterTransition(new Slide(Gravity.RIGHT));
        //fragment.setExitTransition(new Slide(Gravity.LEFT));
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        fragmentTransaction.commit();
    }
}