package com.example.mac.trendingrepos;

import android.os.AsyncTask;

public class FetchRepos extends AsyncTask <Void, Void, String> {

    public FetchRepos() {

    }

    @Override
    protected String doInBackground(Void... voids) {
        return NetworkUtils.getReposInfo();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
