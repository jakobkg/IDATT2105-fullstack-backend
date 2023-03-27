package edu.ntnu.jakobkg.idatt2105projbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ntnu.jakobkg.idatt2105projbackend.model.UploadedFile;

@Repository
public interface FileRepository extends JpaRepository<UploadedFile, Integer> {
    public UploadedFile findByFilename(String filename);
}
