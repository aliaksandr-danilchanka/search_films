package myproject.searchfilm.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import myproject.searchfilm.R;
import myproject.searchfilm.fragment.InformationFilmFragment;

public class InformationFilmActivity extends AppCompatActivity {

    public static final String ID_FILM_KEY = "ID_FILM_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_film);

        if (savedInstanceState == null) {
            Fragment myFragment = InformationFilmFragment.newInstance(getIntent().getIntExtra(ID_FILM_KEY, 0));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_information_film, myFragment)
                        .commit();
        }
    }
}
