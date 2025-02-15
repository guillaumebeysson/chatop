package com.openclassrooms.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

@Service
public class FileService {

    private final String uploadDir = "./images";

    /**
     * Sauvegarde un fichier dans le répertoire d'upload.
     * @param file le fichier à sauvegarder
     * @return l'URL du fichier sauvegardé
     */
    public String saveFile(MultipartFile file) throws IOException {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = getFileExtension(originalFileName);
        String fileName = StringUtils.stripFilenameExtension(originalFileName) + "-" + Instant.now().toEpochMilli() + "." + fileExtension;

        Path targetLocation = Paths.get(uploadDir).resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/images/")
                .path(fileName)
                .toUriString();

        return fileDownloadUri;
    }

    /**
     * Récupère l'extension d'un fichier.
     * @param fileName le nom du fichier
     * @return l'extension du fichier
     */
    private String getFileExtension(String fileName) {
        if (fileName == null) {
            return "";
        }
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}