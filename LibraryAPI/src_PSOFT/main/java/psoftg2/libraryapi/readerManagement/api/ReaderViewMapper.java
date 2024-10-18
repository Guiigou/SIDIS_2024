package psoftg2.libraryapi.readerManagement.api;

import org.mapstruct.Mapper;
import psoftg2.libraryapi.readerManagement.model.Reader;

@Mapper(componentModel = "spring")
public abstract class ReaderViewMapper {
    public abstract ReaderView toReaderView(Reader reader);
    public abstract Iterable<ReaderView> toReaderView(Iterable<Reader> readers);
}
