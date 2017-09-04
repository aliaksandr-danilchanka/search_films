package myproject.searchfilm.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import myproject.searchfilm.R;
import myproject.searchfilm.api.RestHelper;
import myproject.searchfilm.model.InformationAboutFilm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Aliaksandr on 8/8/2017.
 */

public class InformationFilmFragment extends Fragment {

    public static final String ID_KEY = "ID_KEY";
    private static final String API_KEY = "349f0282b34402e866888a09b5d49fb5";
    private static final String FILM_KEY = "FILM_KEY";
    @BindView(R.id.bar_information)
    ProgressBar mInfBar;
    @BindView(R.id.view_information)
    RelativeLayout mInformationAboutFilm;
    @BindView(R.id.view_error_information)
    LinearLayout mErrorView;
    @BindView(R.id.img_information_poster)
    ImageView mPoster;
    @BindView(R.id.txt_original_title)
    TextView mTitle;
    @BindView(R.id.txt_genres)
    TextView mGenres;
    @BindView(R.id.txt_popularity)
    TextView mPopularity;
    @BindView(R.id.txt_release_date)
    TextView mReleaseDate;
    @BindView(R.id.txt_production_companies)
    TextView mProductionCompanies;
    @BindView(R.id.txt_production_countries)
    TextView mProductionCountries;
    @BindView(R.id.txt_overview_information)
    TextView mOverview;

    private int mIdFilm;
    private View mV;
    private InformationAboutFilm mFilm;

    public static InformationFilmFragment newInstance(int id) {

        Bundle args = new Bundle();

        args.putInt(ID_KEY, id);
        InformationFilmFragment fragment = new InformationFilmFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mV = inflater.inflate(R.layout.fragment_information_film, null);
        ButterKnife.bind(this, mV);
        mIdFilm = getArguments().getInt(ID_KEY);
        if (savedInstanceState != null) {
            mFilm = savedInstanceState.getParcelable(FILM_KEY);
            if(mFilm != null) {
                initializeData();
            }else{
                showProgressBar();
                loadData();
            }
        } else {
            showProgressBar();
            loadData();
        }
        return mV;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(FILM_KEY, mFilm);
    }

    private void loadData() {
        RestHelper.getInterface().getFilmInformation(mIdFilm, API_KEY).enqueue(new Callback<InformationAboutFilm>() {
            @Override
            public void onResponse(Call<InformationAboutFilm> call, Response<InformationAboutFilm> response) {
                mFilm = response.body();
                initializeData();
            }

            @Override
            public void onFailure(Call<InformationAboutFilm> call, Throwable t) {
                showErrorView();
            }
        });
    }

    private void initializeData() {
        Picasso.with(mV.getContext()).load("https://image.tmdb.org/t/p/w500" + mFilm.getPoster_path())
                .placeholder(R.drawable.no_foto)
                .into(mPoster);
        mTitle.setText(mFilm.getOriginal_title());
        String genres = getActivity().getBaseContext()
                .getResources()
                .getString(R.string.information_film_genres);
        for (int i = 0; i < mFilm.getGenres().size(); i++) {
            genres += mFilm.getGenres().get(i).getName() + " ";
        }
        mGenres.setText(genres);
        mPopularity.setText(String.format("%s", mFilm.getPopularity()));
        mReleaseDate.setText(mFilm.getRelease_date());
        String productionCompanies = getActivity().getBaseContext()
                .getResources()
                .getString(R.string.information_film_production_companies);
        for (int i = 0; i < mFilm.getProduction_companies().size(); i++) {
            productionCompanies += mFilm.getProduction_companies().get(i).getName() + " ";
        }
        mProductionCompanies.setText(productionCompanies);
        String productionCountries = getActivity().getBaseContext()
                .getResources()
                .getString(R.string.information_film_production_countries);
        for (int i = 0; i < mFilm.getProduction_countries().size(); i++) {
            productionCountries += mFilm.getProduction_countries().get(i).getName() + " ";
        }
        mProductionCountries.setText(productionCountries);
        mOverview.setText(mFilm.getOverview());
        showInformationAboutFilm();
    }

    private void showErrorView() {
        mInfBar.setVisibility(View.GONE);
        mInformationAboutFilm.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

    private void showInformationAboutFilm() {
        mInfBar.setVisibility(View.GONE);
        mInformationAboutFilm.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
    }

    private void showProgressBar() {
        mInfBar.setVisibility(View.VISIBLE);
        mInformationAboutFilm.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
    }
}
