package edu.ntnu.jakobkg.idatt2105projbackend.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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

import java.nio.file.Path;

@Controller
@RequestMapping(path = "/files")
public class FileController {

    @Value("${filebucket.path}")
    String basePath;

    @PostMapping("")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        if (!file.getContentType().equals(MediaType.IMAGE_JPEG_VALUE)) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
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
        Path path = Paths.get(basePath + "/" + filename);
	    Resource file = null;

	    try {
		    file = new UrlResource(path.toUri());
	    } catch (MalformedURLException e) {
		    e.printStackTrace();
	    }
        
	    return ResponseEntity.ok()
			.contentType(MediaType.IMAGE_JPEG)
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
			.body(file);
    }
}