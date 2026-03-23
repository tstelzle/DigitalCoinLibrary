package com.coinlibrary.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.util.UUID;

@RestController
@RequestMapping("/api/images")
@CrossOrigin(origins = "*")
public class ImageController {

    @Autowired
    private RestTemplate restTemplate;

    private static final String UPLOAD_DIR = "uploads/images";

    /**
     * Downloads an image from the provided URL, stores it on the server, and returns it.
     * If the same URL is requested again, returns the cached image.
     *
     * @param imageUrl The URL of the image to download (query parameter)
     * @return ResponseEntity containing the downloaded image
     */
    @GetMapping("/download")
    public ResponseEntity<?> downloadAndStoreImage(@RequestParam(name = "imageUrl")  String imageUrl) {
        try {
            // Validate URL
            if (imageUrl == null || imageUrl.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Image URL is required");
            }

            // Create uploads directory if it doesn't exist
            Path uploadPath = Paths.get(UPLOAD_DIR);
            Files.createDirectories(uploadPath);

            // Generate cache key from URL hash
            String cacheKey = generateUrlHash(imageUrl);
            String fileExtension = getFileExtension(imageUrl);
            String filename = cacheKey + fileExtension;
            Path filePath = uploadPath.resolve(filename);

            // Check if image already exists in cache
            if (Files.exists(filePath)) {
                Resource resource = new FileSystemResource(filePath);
                return ResponseEntity.ok()
                        .contentType(getMediaType(filename))
                        .contentLength(Files.size(filePath))
                        .header("X-Cache", "HIT")
                        .body(resource);
            }

            // Download image from the provided URL
            byte[] imageData = restTemplate.getForObject(imageUrl, byte[].class);

            if (imageData == null || imageData.length == 0) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Failed to download image from URL");
            }

            // Save image to disk
            Files.write(filePath, imageData);

            // Return the downloaded image
            Resource resource = new FileSystemResource(filePath);
            return ResponseEntity.ok()
                    .contentType(getMediaType(filename))
                    .contentLength(imageData.length)
                    .header("X-Cache", "MISS")
                    .body(resource);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid URL format: " + e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to save image: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing image: " + e.getMessage());
        }
    }

    /**
     * Generates MD5 hash of the URL to use as a consistent cache key
     */
    private String generateUrlHash(String imageUrl) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(imageUrl.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            // Fallback to UUID if hashing fails
            return UUID.randomUUID().toString();
        }
    }

    /**
     * Extracts file extension from URL or defaults to .jpg
     */
    private String getFileExtension(String imageUrl) {
        if (imageUrl.contains(".")) {
            return imageUrl.substring(imageUrl.lastIndexOf("."));
        }
        return ".jpg";
    }

    /**
     * Determines appropriate MediaType based on file extension
     */
    private MediaType getMediaType(String filename) {
        String extension = filename.substring(filename.lastIndexOf(".")).toLowerCase();
        return switch (extension) {
            case ".png" -> MediaType.IMAGE_PNG;
            case ".jpg", ".jpeg" -> MediaType.IMAGE_JPEG;
            case ".gif" -> MediaType.IMAGE_GIF;
            case ".webp" -> MediaType.valueOf("image/webp");
            default -> MediaType.IMAGE_JPEG;
        };
    }
}
