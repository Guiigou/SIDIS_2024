package com.authApi.psoftg2.libraryapi.authorManagement.services;

import org.mapstruct.Mapper;
import com.authApi.psoftg2.libraryapi.authorManagement.model.Author;

@Mapper(componentModel = "spring")
public abstract class EditAuthorMapper {
    public abstract Author create(EditAuthorRequest request);
}