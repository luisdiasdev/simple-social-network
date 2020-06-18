package br.com.agateownz.foodsocial.modules.shared.service.storage;

import br.com.agateownz.foodsocial.config.storage.StorageConfig;
import br.com.agateownz.foodsocial.modules.shared.dto.StoreObject;
import br.com.agateownz.foodsocial.modules.shared.service.StorageService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Primary
public class AmazonStorageService implements StorageService {

    private final AmazonS3 s3Client;
    private final StorageConfig storageConfig;

    @Override
    @SneakyThrows
    public Optional<String> store(
        String uuid,
        String folder,
        String contentType,
        byte[] fileBuffer,
        boolean isPublic) {
        var fileName = getFileName(uuid, folder);
        uploadToBucket(fileName, contentType, fileBuffer, isPublic);
        var returnValue = getFileUrl(fileName).toString();
        return Optional.of(returnValue);
    }

    @Override
    public void delete(String filePath) {
        s3Client.deleteObject(new DeleteObjectRequest(storageConfig.getBucketName(), filePath));
    }

    @Override
    public StoreObject get(String filePath) {
        var object = s3Client.getObject(storageConfig.getBucketName(), filePath);
        return new AmazonStoreObject(object);
    }

    private void uploadToBucket(
        String fileName,
        String contentType,
        byte[] fileBuffer,
        boolean isPublic) {
        var objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(fileBuffer.length);
        objectMetadata.setContentType(contentType);
        var request = new PutObjectRequest(
            storageConfig.getBucketName(),
            fileName,
            new ByteArrayInputStream(fileBuffer),
            objectMetadata);

        if (isPublic) {
            request.withCannedAcl(CannedAccessControlList.PublicRead);
        }

        s3Client.putObject(request);
        log.warn("Stored file: " + fileName + " of type: " + contentType);
    }

    private String getFileName(String uuid, String folder) {
        return folder.concat("/").concat(uuid);
    }

    private URL getFileUrl(String fileName) {
        return s3Client.getUrl(storageConfig.getBucketName(), fileName);
    }
}
