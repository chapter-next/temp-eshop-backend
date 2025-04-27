package com.alxkls.eshop_backend.service.image;

import com.alxkls.eshop_backend.dto.ImageDto;
import com.alxkls.eshop_backend.model.Image;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
  Image getImageById(Long Id);

  Image updateImage(MultipartFile image, Long imageId);

  void deleteImageById(Long id);

  List<ImageDto> saveImages(List<MultipartFile> images, Long productId);
}
