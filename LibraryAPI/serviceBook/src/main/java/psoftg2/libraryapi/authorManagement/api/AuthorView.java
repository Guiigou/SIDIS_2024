package psoftg2.libraryapi.authorManagement.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "An Author")
public class AuthorView {
    private Long id;
    private String name;
    private String shortBio;
}