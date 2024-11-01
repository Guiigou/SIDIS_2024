package com.authApi.psoftg2.libraryapi.lendingManagement.services;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateLendingRequest {
    private Long readerId;
    private Long bookId;
}
