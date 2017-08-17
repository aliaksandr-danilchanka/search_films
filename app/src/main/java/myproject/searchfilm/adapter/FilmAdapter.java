package myproject.searchfilm.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.ButterKnife;
import myproject.searchfilm.R;
import myproject.searchfilm.model.Film;

/**
 * Created by Aliaksandr on 8/6/2017.
 */

public class FilmAdapter extends RecyclerView.Adapter<FilmAdapter.FilmViewHolder> {

    OnFilmClickListener mOnFilmClickListener;
    ArrayList<Film> mFilms;

    public FilmAdapter(ArrayList<Film> films, OnFilmClickListener onFilmClickListener) {
        this.mFilms = films;
        this.mOnFilmClickListener = onFilmClickListener;
    }

    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_film, viewGroup, false);
        FilmViewHolder pvh = new FilmViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(FilmViewHolder filmViewHolder, final int i) {
        Picasso.with(filmViewHolder.cv.getContext())
                .load("https://image.tmdb.org/t/p/w500" + mFilms.get(i).getPoster_path())
                .placeholder(R.drawable.no_foto)
                .into(filmViewHolder.poster);
        filmViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnFilmClickListener.onFilmClicked(mFilms.get(i));
            }
        });
        filmViewHolder.title.setText(mFilms.get(i).getTitle());
        filmViewHolder.originalLanguage.setText(mFilms.get(i).getOriginal_language());
        filmViewHolder.releaseDate.setText(mFilms.get(i).getRelease_date());
        filmViewHolder.overview.setText(mFilms.get(i).getOverview());
    }

    @Override
    public int getItemCount() {
        return mFilms.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface OnFilmClickListener {

        void onFilmClicked(Film film);
    }

    public static class FilmViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        ImageView poster;
        TextView title;
        TextView originalLanguage;
        TextView releaseDate;
        TextView overview;

        FilmViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card_view);
            ButterKnife.bind(this, cv);
            poster = itemView.findViewById(R.id.img_poster);
            title = itemView.findViewById(R.id.title);
            originalLanguage = itemView.findViewById(R.id.txt_original_language);
            releaseDate = itemView.findViewById(R.id.txt_release_date);
            overview = itemView.findViewById(R.id.txt_overview);
        }
    }
}
