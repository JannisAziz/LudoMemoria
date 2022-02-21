package de.jannisaziz.backend.IGDB;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class IGDBAuth {
    private static final String CLIENT_ID = "vd9j5kd84998i4u65pci5f9yrgmmul";
    private static final String CLIENT_SECRET = "utjjeurh2ez91jd0n1mnmndb8j8zjb";
    private static final String IGDB_AUTH_URI = "https://id.twitch.tv/oauth2/token" +
            "?client_id=" + CLIENT_ID +
            "&client_secret=" + CLIENT_SECRET +
            "&grant_type=client_credentials";

    private static record IGDB_AUTH_TOKEN(String access_token, int expires_in, String token_type) { }

    private static IGDB_AUTH_TOKEN ACCESS_TOKEN;

    public static String getClientId() {
        return CLIENT_ID;
    }

    public static String getAccessToken() {
        return ACCESS_TOKEN == null ? (ACCESS_TOKEN = requestAccessToken()).access_token : ACCESS_TOKEN.access_token;
    }

    private static IGDB_AUTH_TOKEN requestAccessToken() {
        ResponseEntity<IGDB_AUTH_TOKEN> response = WebClient.create().post()
                .uri(IGDB_AUTH_URI)
                .retrieve()
                .onStatus(HttpStatus.BAD_REQUEST::equals, clientResponse -> Mono.empty())
                .onStatus(HttpStatus.FORBIDDEN::equals, clientResponse -> Mono.empty())
                .toEntity(IGDB_AUTH_TOKEN.class)
                .block();

        System.out.println("accesstoken expires in: " + response.getBody().expires_in);

        return response.getBody();
    }
}
