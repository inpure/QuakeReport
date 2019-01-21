package com.example.cf.quakereport;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2016-01-01&endtime=2016-05-02&minfelt=50&minmagnitude=5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
        new EarthquakeTask().execute(USGS_REQUEST_URL);
    }

    private class EarthquakeTask extends AsyncTask<String, Void, ArrayList<Earthquake>> {
        @Override
        protected ArrayList<Earthquake> doInBackground(String... strings) {
            URL url = createUrl(strings[0]);
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<Earthquake> earthquakes = extractEarthquakes(jsonResponse);
            return earthquakes;
        }

        @Override
        protected void onPostExecute(ArrayList<Earthquake> earthquakes) {
            super.onPostExecute(earthquakes);
            // 创建一个 Earthquake 列表的适配器 EarthquakeAdaper
            // 用它为每个列表项创建列表项视图
            EarthquakeAdapter adapter = new EarthquakeAdapter(MainActivity.this, earthquakes);

            // 获得一个 ListView 的引用，并为其配置 adapter
            ListView earthquakeListView = findViewById(R.id.list);
            earthquakeListView.setAdapter(adapter);
        }
        /**
         * @param stringUrl 需要转换为URL的String对象
         * @return 返回新的URL对象
         */
        private URL createUrl(String stringUrl) {
            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
            return url;
        }

        /**
         * 将包含有 JSON 响应的 InputStream 转换为 String
         */
        private String readFromSteam(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = bufferedReader.readLine();
                while (line != null) {
                    output.append(line);
                    line = bufferedReader.readLine();
                }
            }
            return output.toString();
        }

        /**
         * 对给定的URL发出HTTP请求，并返回一个String作为响应。
         */
        private String makeHttpRequest(URL url) throws IOException {
            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            String jsonResponse = "";
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /*milliseconds*/);
                urlConnection.setConnectTimeout(15000 /*milliseconds*/);
                urlConnection.connect();
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromSteam(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        private   ArrayList<Earthquake> extractEarthquakes(String jsonResponse) {

            // Create an empty ArrayList that we can start adding earthquakes to
            ArrayList<Earthquake> earthquakes = new ArrayList<>();

            // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
            try {
                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
                // build up a list of Earthquake objects with the corresponding data.
                JSONObject baseJsonResponse = new JSONObject(jsonResponse);
                JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

                for (int i = 0; i < earthquakeArray.length(); i++) {
                    JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                    JSONObject properties = currentEarthquake.getJSONObject("properties");
                    String magnitude = properties.getString("mag");
                    String place = properties.getString("place");
                    String time = properties.getString("time");
                    earthquakes.add(new Earthquake(magnitude, place, time));
                }


            } catch (JSONException e) {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
            }

            // Return the list of earthquakes
            return earthquakes;
        }
    }
}
