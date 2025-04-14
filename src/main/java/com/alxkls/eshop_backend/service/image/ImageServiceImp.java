package com.alxkls.eshop_backend.service.image;

import com.alxkls.eshop_backend.dto.ImageDto;
import com.alxkls.eshop_backend.exceptions.ResourceNotFoundException;
import com.alxkls.eshop_backend.model.Image;
import com.alxkls.eshop_backend.model.Product;
import com.alxkls.eshop_backend.repository.image.ImageRepository;
import com.alxkls.eshop_backend.service.product.ProductService;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.rowset.serial.SerialBlob;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageServiceImp implements ImageService {
  private static final String DOWNLOAD_PATH_PATTERN = "api/v1/images/image/download/%s";
  private final ImageRepository imageRepo;
  private final ProductService productService;

  @Override
  public Image getImageById(Long id) {
    return imageRepo
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Cannot find image!"));
  }

  @Override
  public Image updateImage(MultipartFile image, Long imageId) {
    return Optional.ofNullable(getImageById(imageId))
        .map(
            existingImage -> {
              existingImage.setFileName(image.getOriginalFilename());
              existingImage.setFileType(image.getContentType());

              try {
                existingImage.setBlob(new SerialBlob(image.getBytes()));
              } catch (SQLException | IOException e) {
                throw new RuntimeException(e);
              }
              return imageRepo.save(existingImage);
            })
        .orElseThrow(() -> new ResourceNotFoundException("Cannot find image!"));
  }

  @Override
  public void deleteImageById(Long id) {

    imageRepo
        .findById(id)
        .ifPresentOrElse(
            imageRepo::delete,
            () -> {
              new ResourceNotFoundException("Cannot find image!");
            });
  }

  @Override
  public List<ImageDto> saveImages(List<MultipartFile> images, Long productId) {
    List<ImageDto> savedImages = new ArrayList<>();
    Product product = productService.getProductById(productId);

    for (MultipartFile image : images) {
      Image savedImage;
      try {
        savedImage =
            imageRepo.save(
                Image.builder()
                    .fileName(image.getOriginalFilename())
                    .blob(new SerialBlob(image.getBytes()))
                    .fileType(image.getContentType())
                    .product(product)
                    .build());
      } catch (SQLException | IOException e) {
        throw new RuntimeException(e);
      }
      savedImage.setDownloadUrl(String.format(DOWNLOAD_PATH_PATTERN, savedImage.getId()));
      savedImage = imageRepo.save(savedImage);
      savedImages.add(
          ImageDto.builder()
              .imageName(savedImage.getFileName())
              .downloadPath(savedImage.getDownloadUrl())
              .id(savedImage.getId())
              .build());
    }

    return savedImages;
  }
}
