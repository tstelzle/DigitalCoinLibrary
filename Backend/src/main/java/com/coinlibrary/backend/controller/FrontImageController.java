package com.coinlibrary.backend.controller;

import jakarta.servlet.annotation.WebFilter;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;

@WebFilter(urlPatterns = "/*")
@RestController
@CrossOrigin
@RequestMapping
public class FrontImageController {

    @GetMapping("/api/frontImage/{name}")
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {
        InputStream in = getClass().getResourceAsStream("/coin_fronts/" + name + ".png");
        if (in == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        byte[] image = IOUtils.toByteArray(in);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}
