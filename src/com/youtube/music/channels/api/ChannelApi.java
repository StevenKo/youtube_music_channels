package com.youtube.music.channels.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.youtube.music.channels.entity.Channel;

public class ChannelApi {

    final static String         HOST  = "http://106.187.103.131";
    public static final String  TAG   = "CHANNEL_API";
    public static final boolean DEBUG = true;

    public static ArrayList<Channel> getPromotionNews(int area) {
        ArrayList<Channel> channels = new ArrayList();
        String message = getMessageFromServer("GET", "/api/v1/channels.json?area_id=" + area, null);
        if (message == null) {
            return null;
        } else {
            try {
                JSONArray newsArray;
                newsArray = new JSONArray(message.toString());
                for (int i = 0; i < newsArray.length(); i++) {
                    String name = newsArray.getJSONObject(i).getString("name");
                    String link = newsArray.getJSONObject(i).getString("link");

                    Channel n = new Channel("", "");
                    channels.add(n);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }
        return channels;
    }

    public static String getMessageFromServer(String requestMethod, String apiPath, JSONObject json) {
        URL url;
        try {
            url = new URL(HOST + apiPath);
            if (DEBUG)
                Log.d(TAG, "URL: " + url);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestMethod);

            connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            if (requestMethod.equalsIgnoreCase("POST"))
                connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.connect();

            if (requestMethod.equalsIgnoreCase("POST")) {
                OutputStream outputStream;

                outputStream = connection.getOutputStream();
                if (DEBUG)
                    Log.d("post message", json.toString());

                outputStream.write(json.toString().getBytes());
                outputStream.flush();
                outputStream.close();
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder lines = new StringBuilder();
            ;
            String tempStr;

            while ((tempStr = reader.readLine()) != null) {
                lines = lines.append(tempStr);
            }
            if (DEBUG)
                Log.d("MOVIE_API", lines.toString());

            reader.close();
            connection.disconnect();

            return lines.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
