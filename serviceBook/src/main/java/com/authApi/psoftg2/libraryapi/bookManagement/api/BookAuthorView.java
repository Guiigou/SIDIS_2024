package com.authApi.psoftg2.libraryapi.bookManagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "BookAuthorView")
public class BookAuthorView {
    String author;
    String shortBio;
}
