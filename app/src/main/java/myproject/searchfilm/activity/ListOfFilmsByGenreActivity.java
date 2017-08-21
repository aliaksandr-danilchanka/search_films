package myproject.searchfilm.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import myproject.searchfilm.R;
import myproject.searchfilm.fragment.ListOfFilmsByGenreFragment;


public class ListOfFilmsByGenreActivity extends AppCompatActivity {

    public static final String ID_GENRE_KEY = "ID_GENRE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_films_by_genre);

        if (savedInstanceState == null) {
            Fragment myFragment = ListOfFilmsByGenreFragment.newInstance(getIntent().getIntExtra(ID_GENRE_KEY, 0));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_list_of_films_by_genre, myFragment)
                    .commit();
        }
    }
}
