package by.hayel.server.service.cloud;

import java.util.Map;

public interface CloudinaryService {
  String CLOUDINARY_SECURE_URL = "secure_url";
  String CLOUDINARY_PUBLIC_ID = "public_id";
  String CLOUDINARY_TRANSFORMATION = "transformation";

  Map<String, String> sendToCloud(byte[] image);

  void removeByPublicId(String publicId);
}
