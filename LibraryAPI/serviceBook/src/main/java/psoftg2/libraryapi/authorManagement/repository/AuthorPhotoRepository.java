package psoftg2.libraryapi.authorManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import psoftg2.libraryapi.authorManagement.model.AuthorPhoto;

public interface AuthorPhotoRepository extends JpaRepository<AuthorPhoto, Long> {}