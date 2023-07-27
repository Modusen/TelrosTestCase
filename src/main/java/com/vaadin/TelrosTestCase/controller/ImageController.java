package com.vaadin.TelrosTestCase.controller;

import com.vaadin.TelrosTestCase.model.Image;
import com.vaadin.TelrosTestCase.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@RestController
@RequestMapping("/image")
@AllArgsConstructor
public class ImageController {

    private final ImageRepository imageRepository;

    @GetMapping(value = "/{id}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable int id) throws IOException {

        Optional<Image> imageOptional = imageRepository.findById(id);
        if (imageOptional.isPresent()) {
            Image image = imageOptional.get();
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.valueOf(image.getMediaType()))
                    .contentLength(image.getFileSize())
                    .body(Files.readAllBytes(Path.of(image.getFilePath())));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
