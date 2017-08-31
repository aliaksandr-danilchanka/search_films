package myproject.searchfilm.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import myproject.searchfilm.R;
import myproject.searchfilm.activity.base.BaseActivity;
import myproject.searchfilm.fragment.InformationFilmFragment;

public class InformationFilmActivity extends BaseActivity {

    public static final String ID_FILM_KEY = "ID_FILM_KEY";
    @BindView(R.id.toolbar_actionbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_film);
        ButterKnife.bind(this);
        setUpAppBar();

        if (savedInstanceState == null) {
            Fragment myFragment = InformationFilmFragment.newInstance(getIntent().getIntExtra(ID_FILM_KEY, 0));
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container_information_film, myFragment)
                        .commit();
        }
    }

    private void setUpAppBar() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle("Information about film");
    }
}
