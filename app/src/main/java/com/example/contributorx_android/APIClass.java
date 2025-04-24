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

                return response.toString();
            } else {
                return "GET request failed";
            }
        } catch (Exception e) {
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

    public static APIContributorResponse GetContributorResponse(String jsonResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonResponse, APIContributorResponse.class);
        } catch (IOException e) {
            android.util.Log.e("JSON Error", "Error deserializing APIContributorResponse", e);
        }
        return null;
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
