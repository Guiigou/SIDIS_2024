package psoftg2.libraryapi.authorManagement.api;

import org.mapstruct.Mapper;
import psoftg2.libraryapi.authorManagement.model.Author;

@Mapper(componentModel = "spring")
public abstract class AuthorLentsViewMapper {
    public abstract Iterable<AuthorLentsView> toAuthorLentsView(Iterable<Author> author);
}