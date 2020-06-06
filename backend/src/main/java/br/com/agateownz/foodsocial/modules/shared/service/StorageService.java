package br.com.agateownz.foodsocial.modules.shared.service;

import br.com.agateownz.foodsocial.config.storage.StorageConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Slf4j
@Service
public class StorageService {

    @Autowired
    private StorageConfig storageConfig;

    public Optional<String> store(String uuid, String folder, MultipartFile file) {
        if (StringUtils.isEmpty(file.getName())) {
            return Optional.empty();
        }

        var filePath = getTargetFilePath(uuid, folder, file.getName());
        try (var is = file.getInputStream();
             var os = new FileOutputStream(filePath)) {
            os.write(is.readAllBytes());
            return Optional.of(filePath);
        } catch (IOException e) {
            log.error("An error ocurred while uploading file [" + filePath + "]", e);
        }
        return Optional.empty();
    }

    public Optional<File> find(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return Optional.empty();
        }

        var file = new File(filePath);
        if (!file.exists() || !file.canRead()) {
            return Optional.empty();
        }

        return Optional.of(file);
    }

    public void delete(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return;
        }

        var file = new File(filePath);

        if (!file.delete()) {
            log.warn("could not delete file: " + filePath);
        }
    }

    private String getTargetFilePath(String uuid, String folder, String fileName) {
        return getTargetFolder()
                .concat(folder)
                .concat(File.separator)
                .concat(getCurrentTimestamp())
                .concat("-")
                .concat(uuid)
                .concat(".")
                .concat(FilenameUtils.getExtension(fileName));
    }

    private String getTargetFolder() {
        return storageConfig.getPath().endsWith(File.separator)
                ? storageConfig.getPath()
                : storageConfig.getPath().concat(File.separator);
    }

    private String getCurrentTimestamp() {
        var dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-mm-ss");
        return LocalDateTime.now().format(dateTimeFormatter);
    }
}
