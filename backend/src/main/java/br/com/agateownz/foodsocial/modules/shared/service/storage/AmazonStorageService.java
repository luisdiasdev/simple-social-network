package br.com.agateownz.foodsocial.modules.shared.service.storage;

import br.com.agateownz.foodsocial.config.storage.StorageConfig;
import br.com.agateownz.foodsocial.modules.shared.dto.StoreObject;
import br.com.agateownz.foodsocial.modules.shared.service.StorageService;
import java.net.URL;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Slf4j
@Service
@RequiredArgsConstructor
@Primary
@Profile("!test")
public class AmazonStorageService implements StorageService {

    private final S3Client s3Client;
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
        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(storageConfig.getBucketName())
                .key(filePath)
                .build();
        s3Client.deleteObject(deleteRequest);
    }

    @Override
    public StoreObject get(String filePath) {
        GetObjectRequest getRequest = GetObjectRequest.builder()
                .bucket(storageConfig.getBucketName())
                .key(filePath)
                .build();
        var object = s3Client.getObject(getRequest);
        return new AmazonStoreObject(object);
    }

    private void uploadToBucket(
        String fileName,
        String contentType,
        byte[] fileBuffer,
        boolean isPublic) {

        var requestBuilder = PutObjectRequest.builder()
                .bucket(storageConfig.getBucketName())
                .key(fileName)
                .contentType(contentType)
                .contentLength((long) fileBuffer.length);

        if (isPublic) {
            requestBuilder.acl(ObjectCannedACL.PUBLIC_READ);
        }

        var request = requestBuilder.build();
        var requestBody = RequestBody.fromBytes(fileBuffer);

        s3Client.putObject(request, requestBody);
        log.warn("Stored file: " + fileName + " of type: " + contentType);
    }

    private String getFileName(String uuid, String folder) {
        return folder.concat("/").concat(uuid);
    }

    private URL getFileUrl(String fileName) {
        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
                .bucket(storageConfig.getBucketName())
                .key(fileName)
                .build();
        return s3Client.utilities().getUrl(getUrlRequest);
    }
}
