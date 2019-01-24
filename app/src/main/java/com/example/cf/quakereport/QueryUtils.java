package com.example.cf.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * 创建时间：2019/1/23
 * 编 写 人：inpure
 * 邮    箱：chfjinqh@gmail.com
 * 功能描述：
 */
public final class QueryUtils {

    /**
     * 创建一个私有构造函数，因为不需要创建{@link QueryUtils}对象。
     * 这个类只是为了保存静态变量和方法，可以直接从类名QueryUtils访问（并且不需要QueryUtils的对象实例））。
     */
    private QueryUtils() {
    }

    /**
     * 将stringUrl转换成URI对象
     */
    private static URL createUrl(String stringUrl) {
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
    private static String readFromSteam(InputStream inputStream) throws IOException {
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
    private static String makeHttpRequest(URL url) throws IOException {
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

    /**
     * 返回一个{@link Earthquake}对象列表，
     * 这些对象是通过解析给定的JSON响应构建的。
     */
    private static ArrayList<Earthquake> extractEarthquakes(String jsonResponse) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONArray earthquakeArray = baseJsonResponse.getJSONArray("features");

            for (int i = 0; i < earthquakeArray.length(); i++) {
                JSONObject currentEarthquake = earthquakeArray.getJSONObject(i);
                JSONObject properties = currentEarthquake.getJSONObject("properties");
                Double magnitude = properties.getDouble("mag");
                String Location = properties.getString("place");
                Long TimeInMilliseconds = properties.getLong("time");
                earthquakes.add(new Earthquake(magnitude, Location, TimeInMilliseconds));
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

    /**
     * 查询USGS数据集并返回{@link Earthquake}对象列表
     */
    public static ArrayList<Earthquake> fetchEarthquakeData(String requestUri) {
        URL url = QueryUtils.createUrl(requestUri);
        String jsonResponse = "";
        try {
            jsonResponse = QueryUtils.makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Earthquake> earthquakes = extractEarthquakes(jsonResponse);
        return earthquakes;
    }
}
