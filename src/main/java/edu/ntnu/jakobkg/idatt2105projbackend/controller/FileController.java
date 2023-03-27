package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

@Controller
@RequestMapping(path = "/files")
public class FileController {
    @Autowired
    FileRepository fileRepo;

    @PostMapping("")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) {
        if (!file.getContentType().equals(MediaType.IMAGE_JPEG_VALUE)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        UploadedFile uploaded = new UploadedFile();
        String filename = UUID.randomUUID()
                + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        uploaded.setFilename(filename);

        try {
            uploaded.setContents(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileRepo.save(uploaded);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(filename)
                .toUriString();
        return ResponseEntity.ok(fileDownloadUri);
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity download(@PathVariable String filename) {
        UploadedFile file = fileRepo.findByFilename(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(file.getContents());
    }
}