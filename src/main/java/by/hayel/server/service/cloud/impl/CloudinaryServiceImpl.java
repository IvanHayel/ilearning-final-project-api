package by.hayel.server.service.cloud.impl;

import by.hayel.server.exception.cloud.CloudinaryDeleteException;
import by.hayel.server.exception.cloud.CloudinaryUploadException;
import by.hayel.server.service.cloud.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CloudinaryServiceImpl implements CloudinaryService {
  private static final int DEFAULT_IMAGE_WIDTH = 260;
  private static final int DEFAULT_IMAGE_HEIGHT = 160;

  private static final Transformation<?> DEFAULT_TRANSFORMATION =
      new Transformation<>().width(DEFAULT_IMAGE_WIDTH).height(DEFAULT_IMAGE_HEIGHT);
  private static final Map<?, ?> DEFAULT_OPTIONS =
      ObjectUtils.asMap(CLOUDINARY_TRANSFORMATION, DEFAULT_TRANSFORMATION);

  Cloudinary cloudinary;

  public Map<String, String> sendToCloud(byte[] image) {
    try {
      Map<String, String> response = new HashMap<>();
      var map = cloudinary.uploader().upload(image, DEFAULT_OPTIONS);
      response.put(CLOUDINARY_SECURE_URL, (String) map.get(CLOUDINARY_SECURE_URL));
      response.put(CLOUDINARY_PUBLIC_ID, (String) map.get(CLOUDINARY_PUBLIC_ID));
      return response;
    } catch (IOException e) {
      throw new CloudinaryUploadException();
    }
  }

  @Override
  public void removeByPublicId(String publicId) {
    try {
      cloudinary.api().deleteResources(List.of(publicId), ObjectUtils.emptyMap());
    } catch (Exception e) {
      throw new CloudinaryDeleteException();
    }
  }
}
