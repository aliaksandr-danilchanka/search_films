package myproject.searchfilm.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import myproject.searchfilm.R;
import myproject.searchfilm.model.Genre;

;

/**
 * Created by Aliaksandr on 8/18/2017.
 */

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.GenreViewHolder> {

    ArrayList<Genre> mGenres;
    OnGenreClickListener mOnGenreClickListener;

    public GenreAdapter(ArrayList<Genre> genres, OnGenreClickListener onGenreClickListener) {
        this.mGenres = genres;
        this.mOnGenreClickListener = onGenreClickListener;
    }

    @Override
    public GenreViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_genre, viewGroup, false);
        GenreViewHolder pvh = new GenreViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(GenreViewHolder genreViewHolder, final int i) {
        genreViewHolder.mGenre.setText(mGenres.get(i).getName());
        genreViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnGenreClickListener.onGenreClicked(mGenres.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mGenres.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public interface OnGenreClickListener {
        void onGenreClicked(Genre genre);
    }

    public static class GenreViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView mGenre;

        GenreViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.card_view_for_search_by_genre);
            ButterKnife.bind(this, cv);
            mGenre = itemView.findViewById(R.id.title_for_search_by_genre);
        }
    }
}
