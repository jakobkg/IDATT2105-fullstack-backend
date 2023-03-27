package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.ntnu.jakobkg.idatt2105projbackend.model.UploadedFile;
import edu.ntnu.jakobkg.idatt2105projbackend.repo.FileRepository;
import java.nio.file.Path;

@Controller
@RequestMapping(path = "/files")
public class FileController {
    @Autowired
    FileRepository fileRepo;

    String basePath = "${java.io.tmpdir}";

    @PostMapping("")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) {
        String filename = UUID.randomUUID() + ".jpg";
        Path path = Paths.get(basePath, filename);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(filename)
                .toUriString();
        return ResponseEntity.ok(fileDownloadUri);
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity download(@PathVariable String filename) {
        UploadedFile file = fileRepo.findByFilename(filename);
        try {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(file.getContents().getBytes(1, (int) file.getContents().length()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}