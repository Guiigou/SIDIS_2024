package psoftg2.libraryapi.authorManagement.api;

import org.mapstruct.Mapper;
import psoftg2.libraryapi.authorManagement.model.Author;

@Mapper(componentModel = "spring")
public abstract class AuthorViewMapper {
    public abstract AuthorView toAuthorView(Author author);
    public abstract Iterable<AuthorView> toAuthorView(Iterable<Author> author);
}