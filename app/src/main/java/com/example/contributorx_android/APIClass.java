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
    private static String loginJson = "";
    private static String loginURL = "";
    private static String loginVerb = "";
    private static String sessionId = "";

    public static Contributor LoggedOnUser = null;

    public static APIResponse SendMessage(String verb, String url, String jsonData, boolean isLogin, int depth) {
        APIResponse result;

        try {
            if (jsonData != null && !jsonData.isEmpty()) {
                android.util.Log.d("API jsonData!!!", jsonData);
            }

            HttpURLConnection connection = getHttpURLConnection(verb, url, jsonData);

            ObjectMapper objectMapper = new ObjectMapper();

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
                    loginJson = jsonData;
                    loginURL = url;
                    loginVerb = verb;
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

                android.util.Log.d("API Response!!!", response.toString());
                result = objectMapper.readValue(response.toString(), APIResponse.class);
            } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {
                android.util.Log.e("API Failure!!!", verb + " bad request");
                result = new APIResponse(verb + " bad request");
            } else if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                android.util.Log.e("API Failure!!!", verb + " not authorized");
                result = new APIResponse(verb + " not authorized");
            } else if (responseCode == HttpURLConnection.HTTP_FORBIDDEN) {
                android.util.Log.e("API Failure!!!", verb + " forbidden");
                result = new APIResponse(verb + " forbidden");
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                android.util.Log.e("API Failure!!!", verb + " request Not found");
                result = new APIResponse(verb + " request Not found");
            } else if (responseCode == HttpURLConnection.HTTP_CONFLICT) {
                android.util.Log.e("API Failure!!!", verb + " conflict");
                result = new APIResponse(verb + " conflict");
            } else if (responseCode == HttpURLConnection.HTTP_INTERNAL_ERROR) {
                android.util.Log.e("API Failure!!!", verb + " internal server error");
                result = new APIResponse(verb + " internal server error");
            } else if (responseCode == HttpURLConnection.HTTP_BAD_GATEWAY) {
                android.util.Log.e("API Failure!!!", verb + " bad gateway");
                result = new APIResponse(verb + " bad gateway");
            } else if (responseCode == HttpURLConnection.HTTP_UNAVAILABLE) {
                android.util.Log.e("API Failure!!!", verb + " service unavailable");
                result = new APIResponse(verb + " service unavailable");
            } else if (responseCode == HttpURLConnection.HTTP_GATEWAY_TIMEOUT) {
                android.util.Log.e("API Failure!!!", verb + " gateway timeout");
                result = new APIResponse(verb + " gateway timeout");
            } else {
                android.util.Log.e("API Failure!!!", verb + " request failed with code: " + responseCode);
                result = new APIResponse(verb + " request failed with code: " + responseCode);
            }
        } catch (Exception e) {
            android.util.Log.e("API Error!!!", e.toString());
            result = new APIResponse(e.toString());
        }

        if (result != null && !result.getIsSuccess() &&
                (result.getMessage().contains(" not authorized")) &&
                !isLogin && depth < 2 &&
                !loginJson.isEmpty() && !loginURL.isEmpty() && !loginVerb.isEmpty()) {
            //Persist login beyond server's time limit
            android.util.Log.d("Persist Login!!!", "Re-login");
            APIResponse tempResponse = SendMessage(loginVerb, loginURL, loginJson, true, depth + 1);
            if (tempResponse != null && tempResponse.getIsSuccess()) {
                result = SendMessage(verb, url, jsonData, false, depth + 1);
            }
        }

        return result;
    }

    @NonNull
    private static HttpURLConnection getHttpURLConnection(String verb, String uri, String jsonData) throws IOException {
        URL url = new URL( "http://192.168.0.123:3000/" + uri);
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

    public static void LoadURL() {
        /*
        Intent browseUrl = new Intent(Intent.ACTION_VIEW, Uri.parse("www.google.com"));
        if (browseUrl.resolveActivity(getPackageManager()) != null) {
            startActivity(browseUrl);
        }
        */
    }
}
