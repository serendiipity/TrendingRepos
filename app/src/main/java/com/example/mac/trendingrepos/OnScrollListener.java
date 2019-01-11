package com.example.mac.trendingrepos;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class OnScrollListener extends RecyclerView.OnScrollListener {

        private int previousTotal = 0;
        private boolean loading = true;

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = recyclerView.getChildCount();
            int totalItemCount = recyclerView.getLayoutManager().getItemCount();
            int firstVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            int visibleThreshold = 5;
            if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                onLoadMore();
                loading = true;
            }
        }

        public abstract void onLoadMore();
    }

