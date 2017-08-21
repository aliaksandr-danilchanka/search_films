package myproject.searchfilm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import myproject.searchfilm.R;
import myproject.searchfilm.activity.InformationFilmActivity;
import myproject.searchfilm.adapter.FilmAdapter;
import myproject.searchfilm.api.RestHelper;
import myproject.searchfilm.model.Film;
import myproject.searchfilm.model.FilmListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static myproject.searchfilm.activity.InformationFilmActivity.ID_FILM_KEY;

/**
 * Created by Aliaksandr on 8/19/2017.
 */

public class ListOfFilmsByGenreFragment extends Fragment {

    public static final String ID_KEY = "ID_KEY";
    private static final String API_KEY = "349f0282b34402e866888a09b5d49fb5";
    private static final String FILMS_KEY = "FILMS_KEY";

    @BindView(R.id.recycler_view_for_search_by_genre)
    RecyclerView recyclerView;
    @BindView(R.id.search_by_genre_bar)
    ProgressBar searchBar;
    @BindView(R.id.view_error_for_search_by_genre)
    LinearLayout viewError;
    @BindView(R.id.view_no_results_for_search_by_genre)
    LinearLayout viewNoResults;

    private ArrayList<Film> mFilms;
    private int mIdGenre;

    public static ListOfFilmsByGenreFragment newInstance(int id) {

        Bundle args = new Bundle();
        args.putInt(ID_KEY, id);
        ListOfFilmsByGenreFragment fragment = new ListOfFilmsByGenreFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_film_by_genre, null);
        ButterKnife.bind(this, v);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        showRecyclerView();
        mIdGenre = getArguments().getInt(ID_KEY);

        if (savedInstanceState != null) {
            mFilms = savedInstanceState.getParcelableArrayList(FILMS_KEY);
            if(mFilms != null) {
                initializeAdapter();
            }
        }else{
            showProgressBarView();
            loadData();
        }

        if (mFilms != null) {
            initializeAdapter();
        }

        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(FILMS_KEY, mFilms);
    }

    private void loadData() {
        RestHelper.getInterface().getFilmListByGenre(mIdGenre, API_KEY).enqueue(new Callback<FilmListResponse>() {
            @Override
            public void onResponse(Call<FilmListResponse> call, Response<FilmListResponse> response) {
                if (mFilms == null) mFilms = new ArrayList<>();
                mFilms.clear();
                mFilms.addAll(response.body().getFilms());
                if (mFilms.isEmpty()) {
                    showNoResultsView();
                } else {
                    initializeAdapter();
                    showRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<FilmListResponse> call, Throwable t) {
                showErrorView();
            }
        });
    }

    private void initializeAdapter() {
        FilmAdapter rvAdapter = new FilmAdapter(mFilms, new FilmAdapter.OnFilmClickListener() {
            @Override
            public void onFilmClicked(Film film) {
                Intent intent = new Intent(getActivity(), InformationFilmActivity.class);
                intent.putExtra(ID_FILM_KEY, film.getId());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(rvAdapter);
    }


    private void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
        searchBar.setVisibility(View.GONE);
        viewError.setVisibility(View.GONE);
        viewNoResults.setVisibility(View.GONE);
    }

    private void showProgressBarView() {
        recyclerView.setVisibility(View.GONE);
        searchBar.setVisibility(View.VISIBLE);
        viewError.setVisibility(View.GONE);
        viewNoResults.setVisibility(View.GONE);
    }

    private void showErrorView() {
        recyclerView.setVisibility(View.GONE);
        searchBar.setVisibility(View.GONE);
        viewError.setVisibility(View.VISIBLE);
        viewNoResults.setVisibility(View.GONE);
    }

    private void showNoResultsView() {
        recyclerView.setVisibility(View.GONE);
        searchBar.setVisibility(View.GONE);
        viewError.setVisibility(View.GONE);
        viewNoResults.setVisibility(View.VISIBLE);
    }
}
