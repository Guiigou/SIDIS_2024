package com.authApi.psoftg2.libraryapi.bookManagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "GenreView")
public class GenreView {
    private Long id;
    private String name;
    private int bookCount;
}
