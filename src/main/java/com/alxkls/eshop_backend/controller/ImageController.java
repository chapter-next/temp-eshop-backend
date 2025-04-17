package com.alxkls.eshop_backend.controller;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.parseMediaType;

import com.alxkls.eshop_backend.dto.ImageDto;
import com.alxkls.eshop_backend.exceptions.ResourceNotFoundException;
import com.alxkls.eshop_backend.model.Image;
import com.alxkls.eshop_backend.response.ApiResponse;
import com.alxkls.eshop_backend.service.image.ImageService;
import java.sql.SQLException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {
  private final ImageService imageService;

  @PostMapping("/save")
  public ResponseEntity<ApiResponse> saveImage(
      @RequestParam List<MultipartFile> files, @RequestParam Long productId) {
    try {
      List<ImageDto> uploadImages = imageService.saveImages(files, productId);
      return ResponseEntity.ok(new ApiResponse("Upload successful", uploadImages));
    } catch (Exception e) {
      return ResponseEntity.status(INTERNAL_SERVER_ERROR)
          .body(new ApiResponse("Error occurred during upload", null));
    }
  }

  @GetMapping("/image/download/{imageId}")
  public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) {
    try {
      Image image = imageService.getImageById(imageId);
      if (image != null) {

        ByteArrayResource resource =
            new ByteArrayResource(image.getBlob().getBytes(1, (int) image.getBlob().length()));

        return ResponseEntity.ok()
            .contentType(parseMediaType(image.getFileType()))
            .header(CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
            .body(resource);
      } else {
        return ResponseEntity.noContent().build();
      }
    } catch (SQLException e) {
      return ResponseEntity.internalServerError().build();
    }
  }

  @PutMapping("/image/{imageId}/update")
  public ResponseEntity<ApiResponse> updateImage(
      @PathVariable Long imageId, @RequestParam MultipartFile file) {

      try {
          Image image = imageService.getImageById(imageId);
          if (image != null) {
            imageService.updateImage(file, imageId);
            return ResponseEntity.ok(new ApiResponse("Update successful", null));
          }
      } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND).body (new ApiResponse(e.getMessage(), null));
      }

      return ResponseEntity.internalServerError().body(new ApiResponse("Update failed", null));
  }
  @DeleteMapping("/image/{imageId}/delete")
  public ResponseEntity<ApiResponse> deleteImage(
          @PathVariable Long imageId) {

    try {
      Image image = imageService.getImageById(imageId);
      if (image != null) {
        imageService.deleteImageById(imageId);
        return ResponseEntity.ok(new ApiResponse("Delete successful", null));
      }
    } catch (ResourceNotFoundException e) {
      return ResponseEntity.status(NOT_FOUND).body (new ApiResponse(e.getMessage(), null));
    }

    return ResponseEntity.internalServerError().body(new ApiResponse("Delete failed", null));
  }
}
