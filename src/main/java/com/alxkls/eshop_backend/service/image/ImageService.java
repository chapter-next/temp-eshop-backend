package com.alxkls.eshop_backend.service.image;

import com.alxkls.eshop_backend.dto.ImageDto;
import com.alxkls.eshop_backend.model.Image;
import com.alxkls.eshop_backend.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
  Image getImageById(Long Id);

  Image updateImage(MultipartFile image, Long imageId);

  void deleteImageById(Long id);

  List<ImageDto> saveImages(List<MultipartFile> images, Long productId);
}
