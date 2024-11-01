package com.authApi.psoftg2.libraryapi.lendingManagement.services;

import org.mapstruct.Mapper;
import com.authApi.psoftg2.libraryapi.lendingManagement.model.Lending;

@Mapper(componentModel = "spring")
public abstract class EditLendingMapper {
    public abstract Lending create(CreateLendingRequest request);
}
