package com.android.quandar.boerzoektklant.adapters;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.quandar.boerzoektklant.R;
import com.android.quandar.boerzoektklant.models.Favorite;

public class FavoritesRecyclerAdapter extends ListAdapter<Favorite, FavoritesRecyclerAdapter.NoteHolder> {
    private OnItemClickListener listener;

    public FavoritesRecyclerAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Favorite> DIFF_CALLBACK = new DiffUtil.ItemCallback<Favorite>() {
        @Override
        public boolean areItemsTheSame(Favorite oldItem, Favorite newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(Favorite oldItem, Favorite newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getDescription().equals(newItem.getDescription()) &&
                    oldItem.getRating() == newItem.getRating();
        }
    };

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_item, parent, false);
        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Favorite currentFavorite = getItem(position);
        holder.textViewTitle.setText(currentFavorite.getTitle());
        holder.textViewDescription.setText(currentFavorite.getDescription());
        holder.favoriteRatingBar.setRating(currentFavorite.getRating());
    }

    public Favorite getNoteAt(int position) {
        return getItem(position);
    }

    class NoteHolder extends RecyclerView.ViewHolder {
        private TextView textViewTitle;
        private TextView textViewDescription;
        private RatingBar favoriteRatingBar;

        public NoteHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            favoriteRatingBar = itemView.findViewById(R.id.favorite_ratingbar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Favorite favorite);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}