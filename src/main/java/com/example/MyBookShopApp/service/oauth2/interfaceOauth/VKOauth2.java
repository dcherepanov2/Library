//package com.example.MyBookShopApp.service.oauth2.interfaceOauth;
//
//import org.springframework.beans.factory.annotation.Value;
//
//import java.io.BufferedReader;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
//import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.*;
//
//public class VKOauth2 implements Oauth2 {
//    TransportClient transportClient = new HttpTransportClient();
//    VkApiClient vk = new VkApiClient(transportClient);
//
//    @Value("${domen}")
//    private String domen;
//
//    private String oauthDomen = "https://oauth.vk.com/authorize?";
//
//    public HttpURLConnection sendRequest() throws IOException {
//        String urlString = oauthDomen + "clientId=" + CLIENT_ID + "&redirect_uri=" + domen + "&response_type=code&v=5.131";
//        URL url = new URL(urlString);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//        connection.setRequestProperty("Content-Type", "application/json");
//        connection.setConnectTimeout(30000);
//        connection.setDoOutput(true);
//        try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
//            writer.flush();
//        }
//        return connection;
//    }
//
//    public String getResponse(HttpURLConnection connection) throws IOException {
//        StringBuilder content;
//        try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//            String line;
//            content = new StringBuilder();
//            while ((line = input.readLine()) != null) {
//                content.append(line);
//                content.append(System.lineSeparator());
//            }
//        } finally {
//            connection.disconnect();
//        }
//        return connection.getURL().toString();
//    }
//
//
//    public String getCode() throws IOException{
//        HttpURLConnection httpURLConnection = this.sendRequest();
//        String code = this.getResponse(httpURLConnection).replace(domen + "?code=", "");
//        return code;
//    }
//
//    public String getToken() throws IOException, ClientException, ApiException {
//        UserAuthResponse authResponse = vk.oAuth()
//                .userAuthorizationCodeFlow(Integer.valueOf(CLIENT_ID), CLIENT_SECRET, REDIRECT_URI, this.getCode())
//                .execute();
//        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
//        return authResponse.getAccessToken();
//    }
//}
