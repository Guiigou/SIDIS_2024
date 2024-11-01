// LendingServiceClient
package com.authApi.psoftg2.libraryapi.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.authApi.psoftg2.libraryapi.bookManagement.api.LentBookView;

import java.util.Arrays;
import java.util.List;

@Component
public class LendingServiceClient {

    private final RestTemplate restTemplate;

    @Autowired
    public LendingServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<LentBookView> getTopBooks() {
        String url = "http://localhost:8083/api/lendings/top-books";
        ResponseEntity<LentBookView[]> response = restTemplate.getForEntity(url, LentBookView[].class);
        return Arrays.asList(response.getBody());
    }
}
