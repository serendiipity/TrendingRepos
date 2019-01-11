package com.example.mac.trendingrepos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

public class ReposListAdapter extends RecyclerView.Adapter <ReposListAdapter.ReposViewHolder> {

    private final LinkedList <RepoInfo> reposList;
    private LayoutInflater inflater;

    public ReposListAdapter(Context context, LinkedList <RepoInfo> reposList) {
        inflater = LayoutInflater.from(context);
        this.reposList = reposList;
    }

    class ReposViewHolder extends RecyclerView.ViewHolder {

        CardView repoCardView;
        TextView nameView;
        TextView ownerView;
        TextView descriptionView;
        TextView starsView;
        ImageView icon;
        final ReposListAdapter adapter;

        public ReposViewHolder(View itemView, ReposListAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            this.repoCardView = (CardView) itemView.findViewById(R.id.repoCardView);
            this.nameView = (TextView) itemView.findViewById(R.id.name);
            this.ownerView = (TextView) itemView.findViewById(R.id.owner);
            this.descriptionView = (TextView) itemView.findViewById(R.id.description);
            this.starsView = (TextView) itemView.findViewById(R.id.stars);
            this.icon = (ImageView) itemView.findViewById(R.id.icon);
        }
    }

    @NonNull
    @Override
    public ReposListAdapter.ReposViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View itemView = inflater.from(viewGroup.getContext()).inflate(R.layout.repolist_item, viewGroup, false);
            return new ReposViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(ReposViewHolder reposViewHolder, int position) {
        RepoInfo current = reposList.get(position);
        reposViewHolder.nameView.setText(current.getName());
        reposViewHolder.ownerView.setText(current.getOwner());
        reposViewHolder.descriptionView.setText(current.getDescription());
        reposViewHolder.starsView.setText(current.getStars());

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return reposList.size();
    }
}
