package com.authApi.psoftg2.libraryapi.readerManagement.api;

import org.mapstruct.Mapper;
import com.authApi.psoftg2.libraryapi.readerManagement.model.Reader;

@Mapper(componentModel = "spring")
public abstract class ReaderLentsViewMapper {
    public abstract Iterable<ReaderLentsView> toReaderLentsView (Iterable<Reader> readers);
}
