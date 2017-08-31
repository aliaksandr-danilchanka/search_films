package myproject.searchfilm.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import myproject.searchfilm.R;
import myproject.searchfilm.activity.base.BaseActivity;
import myproject.searchfilm.fragment.ListOfFilmsByGenreFragment;


public class ListOfFilmsByGenreActivity extends BaseActivity {

    public static final String NAME_GENRE_KEY = "NAME_GENRE_KEY";
    public static final String ID_GENRE_KEY = "ID_GENRE_KEY";
    @BindView(R.id.toolbar_actionbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_films_by_genre);
        ButterKnife.bind(this);
        setUpAppBar();

        if (savedInstanceState == null) {
            Fragment myFragment = ListOfFilmsByGenreFragment.newInstance(getIntent().getIntExtra(ID_GENRE_KEY, 0));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container_list_of_films_by_genre, myFragment)
                    .commit();
        }
    }

    private void setUpAppBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getIntent().getStringExtra(NAME_GENRE_KEY));
    }
}
