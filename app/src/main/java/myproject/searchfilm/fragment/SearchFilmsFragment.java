package myproject.searchfilm.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Aliaksandr on 8/6/2017.
 */

public class SearchFilmsFragment extends Fragment {

    public static final String FILMS_KEY = "FILMS_KEY";
    private static final String API_KEY = "349f0282b34402e866888a09b5d49fb5";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.edit_text)
    EditText editText;
    @BindView(R.id.search_button)
    Button searchButton;
    @BindView(R.id.search_bar)
    ProgressBar searchBar;
    @BindView(R.id.view_error)
    LinearLayout viewError;
    @BindView(R.id.view_no_results)
    LinearLayout viewNoResults;

    private ArrayList<Film> films;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_search_film, null);
        ButterKnife.bind(this, v);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        showRecyclerView();

        if (savedInstanceState != null) {
            films = savedInstanceState.getParcelableArrayList(FILMS_KEY);
            if(films != null) {
                initializeAdapter();
            }else if(films == null && editText.getText().length() != 0){
                showProgressBarView();
                loadData();
            }
        }

        if (films != null) {
            initializeAdapter();
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().length() != 0) {
                    editText.clearFocus();
                    InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    showProgressBarView();
                    loadData();
                }
            }
        });
        return v;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(FILMS_KEY, films);
    }

    private void loadData() {
        RestHelper.getInterface().getFilmList(API_KEY, editText.getText().toString()).enqueue(new Callback<FilmListResponse>() {
            @Override
            public void onResponse(Call<FilmListResponse> call, Response<FilmListResponse> response) {
                if (films == null) films = new ArrayList<>();
                films.clear();
                films.addAll(response.body().getFilms());
                if (films.isEmpty()) {
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
        FilmAdapter rvAdapter = new FilmAdapter(films, new FilmAdapter.OnFilmClickListener() {
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
