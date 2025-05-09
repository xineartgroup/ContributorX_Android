package com.example.contributorx_android;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class APIClass {
    private static String sessionId = "";

    public static Contributor LoggedOnUser = null;

    public static String SendMessage(String verb, String method, String params, String jsonData, boolean isLogin) {
        try {
            if (jsonData != null && !jsonData.isEmpty()) {
                android.util.Log.d("API jsonData!!!", jsonData);
            }

            HttpURLConnection connection = getHttpURLConnection(verb, method, params, jsonData);

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                if (isLogin) {
                    SetSessionCookie(connection);
                }

                android.util.Log.d("API SUCCESS!!!", response.toString());
                return response.toString();
            } else {
                android.util.Log.e("API FAILURE!!!", "GET request failed");
                return "GET request failed";
            }
        } catch (Exception e) {
            android.util.Log.e("API ERROR!!!", e.toString());
            return e.toString();
        }
    }

    private static void SetSessionCookie(HttpURLConnection connection) {
        List<String> setCookieHeaders = connection.getHeaderFields().get("Set-Cookie");
        if (setCookieHeaders != null) {
            for (String cookieHeader : setCookieHeaders) {
                if (cookieHeader.startsWith("connect.sid=")) {
                    sessionId = cookieHeader.split(";")[0];
                    break; // Assuming only one session cookie
                }
            }
        }
    }

    @NonNull
    private static HttpURLConnection getHttpURLConnection(String verb, String method, String params, String jsonData) throws IOException {
        URL url = new URL( "http://192.168.0.123:3000/" + method + params);
        android.util.Log.d("API url", url.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod(verb);

        if (!sessionId.isEmpty()) {
            connection.setRequestProperty("Cookie", sessionId + "; path=/; httponly"); // Construct the Cookie header
        }

        if (!jsonData.isEmpty()) {
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            try (OutputStream os = connection.getOutputStream();
                 OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
                osw.write(jsonData);
                osw.flush();
            }
        }

        return connection;
    }

    public static APIResponse GetResponse(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonResponse, APIResponse.class);
        } catch (IOException e) {
            return new APIResponse(e.getMessage());
        }
    }

    public static void LoadURL() {
        /*
        Intent browseUrl = new Intent(Intent.ACTION_VIEW, Uri.parse("www.google.com"));
        if (browseUrl.resolveActivity(getPackageManager()) != null) {
            startActivity(browseUrl);
        }
        */
    }
}
