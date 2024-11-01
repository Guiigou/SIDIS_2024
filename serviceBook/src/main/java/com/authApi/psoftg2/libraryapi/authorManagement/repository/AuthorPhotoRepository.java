package com.authApi.psoftg2.libraryapi.authorManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.authApi.psoftg2.libraryapi.authorManagement.model.AuthorPhoto;

public interface AuthorPhotoRepository extends JpaRepository<AuthorPhoto, Long> {}