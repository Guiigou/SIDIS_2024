package com.authApi.psoftg2.libraryapi.readerManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.authApi.psoftg2.libraryapi.readerManagement.model.ReaderPhoto;

public interface ReaderPhotoRepository extends JpaRepository<ReaderPhoto, Long> {
}