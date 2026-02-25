package cx.excite.template.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Component
public class AjaxGateway {
    @Value("${excite.url}")
    public String exciteURL;

    public String login(String username, String password) {
        var httpClient = HttpClient.newHttpClient();

        String body = "{\n" +
                "  \"username\": \"" + username + "\",\n" +
                "  \"password\": \"" + password + "\",\n" +
                "  \"requestapikey\": 1\n" +
                "}";
        var httpPost = HttpRequest.newBuilder()
                .uri(URI.create(exciteURL + "pmws.dll/?Ajaxmodule=user&ajax=login&requestmetod=get"))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            var httpResponse = httpClient.send(httpPost, HttpResponse.BodyHandlers.ofString());
            return httpResponse.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String accessUserLog() {
        var httpClient = HttpClient.newHttpClient();
        String body = "{\n" +
                "                \"ajaxtemplate\": \"Excite/statistics/accessuserlogstat.get.js\",\n" +
                "                \"params\": {\n" +
                "            \"okeys\": [],\n" +
                "            \"groups\": [\n" +
                "            \"okey\",\n" +
                "                    \"odkeylevel\"\n" +
                "        ],\n" +
                "            \"dateto\": \"2022-06-01\",\n" +
                "                    \"usesequence\": false,\n" +
                "                    \"periodtype\": \"w\",\n" +
                "                    \"ojetype\": [\n" +
                "            30,\n" +
                "                    50,\n" +
                "                    100,\n" +
                "                    180,\n" +
                "                    190,\n" +
                "                    200,\n" +
                "                    300,\n" +
                "                    400\n" +
                "        ],\n" +
                "            \"datefrom\": \"2022-05-14\"\n" +
                "        }\n" +
                "}";

        var httpPost = HttpRequest.newBuilder()
                .uri(URI.create(exciteURL + "pmws.dll/ajax/Excite/statistics/accessuserlogstat.get"))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            var httpResponse = httpClient.send(httpPost, HttpResponse.BodyHandlers.ofString());
            return httpResponse.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }
}
