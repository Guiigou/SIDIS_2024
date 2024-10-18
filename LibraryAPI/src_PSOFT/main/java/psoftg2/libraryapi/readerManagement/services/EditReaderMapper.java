package psoftg2.libraryapi.readerManagement.services;

import org.mapstruct.Mapper;
import psoftg2.libraryapi.readerManagement.model.Reader;

@Mapper(componentModel = "spring")
public abstract class EditReaderMapper {
    public abstract Reader create(EditReaderRequest request);
}
