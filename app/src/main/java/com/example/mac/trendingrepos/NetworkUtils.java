package com.example.mac.trendingrepos;

import android.net.Uri;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

public class NetworkUtils {

    private static final String BASE_URL = "https://api.github.com/search/repositories?";
    private static final String QUERY_PARAM = "q";
    private static final String SORT = "sort";
    private static final String ORDER = "order";
    private static final String PAGE = "page";

    static String getReposInfo() {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String reposJSONString = null;
        String date = getFormattedDate();

        try {
            Uri builtURI = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, "created:>" + date)
                    .appendQueryParameter(SORT, "stars")
                    .appendQueryParameter(ORDER, "desc")
                    .appendQueryParameter(PAGE, "1") // TODO: multiple pages
                    .build();

            URL requestURL = new URL(builtURI.toString());
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

            if (builder.length() == 0) {
                return null;
            }

            reposJSONString = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return reposJSONString;
    }

    public static String getFormattedDate() {
        Calendar calendar = Calendar.getInstance();
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        int m = calendar.get(Calendar.MONTH) + 1; // TODO: fix explanation below
        int year = calendar.get(Calendar.YEAR) - 1; // API is discontinued so used the current month and day but last year
        String day, month;
        if (Integer.toString(d).length() == 1) {
            day = '0' + Integer.toString(d);
        } else {
            day = Integer.toString(d);
        }
        if (Integer.toString(m).length() == 1) {
            month = '0' + Integer.toString(m);
        } else {
            month = Integer.toString(m);
        }

        String date = Integer.toString(year) + '-' + month + '-' + day;

        return date;
    }
}
