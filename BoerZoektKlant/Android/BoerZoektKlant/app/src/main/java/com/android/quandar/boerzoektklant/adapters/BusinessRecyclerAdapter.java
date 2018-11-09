package com.android.quandar.boerzoektklant.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.android.quandar.boerzoektklant.R;
import com.android.quandar.boerzoektklant.services.BackendUrl;
import com.android.quandar.boerzoektklant.services.DownloadImageTask;
import com.android.quandar.boerzoektklant.models.Business;
import com.android.quandar.boerzoektklant.models.Category;

import java.util.List;

public class BusinessRecyclerAdapter extends RecyclerView.Adapter<BusinessRecyclerAdapter.MyViewHolder> {
    private List<Business> mDataset;
    private Context context;
    private RecyclerViewClickListener mListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView titleListItemTextView, excerptTextView, categoriesTextView;
        public ImageView imageImageView;
        private RecyclerViewClickListener mListener;

        public MyViewHolder(View v, RecyclerViewClickListener listener) {
            super(v);

            // set the onclick listener, so it works when the recycleview is click in other classes
            mListener = listener;
            v.setOnClickListener(this);
            this.imageImageView = v.findViewById(R.id.imageImageView);
            this.titleListItemTextView = v.findViewById(R.id.titleListItemTextView);
            this.excerptTextView = v.findViewById(R.id.excerptTextView);
            this.categoriesTextView = v.findViewById(R.id.categoriesTextView);
        }

        @Override
        public void onClick(View v) {
            mListener.recyclerViewListClicked(v, getAdapterPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public BusinessRecyclerAdapter(List<Business> myDataset, RecyclerViewClickListener listener) {
        mDataset = myDataset;
        mListener = listener;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BusinessRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_list_view_farmer_item, parent, false);

        return new MyViewHolder(itemView, mListener);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Business business = mDataset.get(position);
        holder.titleListItemTextView.setText(business.getTitle());
        holder.excerptTextView.setText(business.getExcerpt());
        String categoriesString = "";

        if(business.getCategories() != null){
            for(Category category : business.getCategories()){
                categoriesString += category.getName() + " | ";
            }
            if(!categoriesString.equals("")){
                categoriesString = categoriesString.substring(0, categoriesString.length() -2);
            }
        }
        holder.categoriesTextView.setText(categoriesString);
        new DownloadImageTask((ImageView) holder.imageImageView)
                .execute(BackendUrl.url + business.getImageUrl());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}