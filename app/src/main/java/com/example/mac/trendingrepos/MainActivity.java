package com.example.mac.trendingrepos;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReposListAdapter adapter;
    private LinkedList <RepoInfo> reposList = new LinkedList <> ();
    String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getRepos();

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new ReposListAdapter(this, reposList);
        recyclerView.setAdapter(adapter);
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(new OnScrollListener() {
            @Override
            public void onLoadMore() {
                getRepos();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
            new FetchRepos().execute();
        }
        else
            Toast.makeText(this, R.string.no_network, Toast.LENGTH_LONG).show();
    }

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
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray itemsArray = jsonObject.getJSONArray("items");

                int i = 0;

                while (i < itemsArray.length()) {
                    JSONObject repoObject = itemsArray.getJSONObject(i);
                    JSONObject owner = repoObject.getJSONObject("owner");

                    try {
                        String name = repoObject.getString("name");
                        String login = owner.getString("login");
                        String description = repoObject.getString("description");
                        String stars = Integer.toString(repoObject.getInt("stargazers_count"));
                        RepoInfo repoInfo = new RepoInfo(name, login, description, stars);
                        reposList.add(repoInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    i++;
                }

                adapter.notifyDataSetChanged();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
