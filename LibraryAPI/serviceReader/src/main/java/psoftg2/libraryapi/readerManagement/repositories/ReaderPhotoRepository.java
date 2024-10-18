package psoftg2.libraryapi.readerManagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import psoftg2.libraryapi.readerManagement.model.ReaderPhoto;

public interface ReaderPhotoRepository extends JpaRepository<ReaderPhoto, Long> {
}