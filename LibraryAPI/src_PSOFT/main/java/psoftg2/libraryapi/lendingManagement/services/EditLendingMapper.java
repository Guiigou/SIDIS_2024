package psoftg2.libraryapi.lendingManagement.services;

import org.mapstruct.Mapper;
import psoftg2.libraryapi.lendingManagement.model.Lending;

@Mapper(componentModel = "spring")
public abstract class EditLendingMapper {
    public abstract Lending create(CreateLendingRequest request);
}
