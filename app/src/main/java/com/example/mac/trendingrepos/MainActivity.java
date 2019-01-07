package com.example.mac.trendingrepos;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks <String> {

    private LinkedList <RepoInfo> reposList = new LinkedList <> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
        getRepos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @NonNull
    @Override
    public Loader <String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new ReposLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader <String> loader, String data) {

        final String LOG_TAG = MainActivity.class.getSimpleName();

        try {
            JSONObject jsonObject = new JSONObject(data);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            int i = 0;
            String name = null;
            int stars;
            String description = null;
            String login = null;
            while (i < itemsArray.length()) {
                JSONObject repo = itemsArray.getJSONObject(i);
                JSONObject owner = repo.getJSONObject("owner");

                try {
                    login = owner.getString("login");
                    description = repo.getString("description");
                    name = repo.getString("name");
                    stars = repo.getInt("stargazers_count");
                    Log.d(LOG_TAG, "repo info " + name + " " + login + " " + description + " " + stars + "\n");
                    reposList.add(new RepoInfo(name, login, description, stars));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                i++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader <String> loader) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getRepos() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;

        if (manager != null)
            networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Bundle bundle = new Bundle();
            getSupportLoaderManager().restartLoader(0, bundle, this);
        } else
            Toast.makeText(this, R.string.no_network, Toast.LENGTH_LONG);
    }
}
