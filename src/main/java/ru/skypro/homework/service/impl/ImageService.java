package ru.skypro.homework.service.impl;//package ru.skypro.homework.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    @Value("${image.dir.path}")
    private String imageDir;

    /**
     * Сгенерируем случайное имя для вашего вновь загруженного файла:
     * String fileName = UUID.randomUUID().toString()+ EXTENSION;
     * Проверьте, существует ли файл в каталоге, который вы загружаете
     * if (serverFile.exists())
     * Если файл существует, начните снова с шага 1, пока не получите имя файла, которого нет на сервере.
     * Класс Files - это еще один метод write() начиная с Java 7, и он работает аналогично WriteString().
     * В качестве параметров он принимает Path и массив байтов.
     * Метод write() подходит для записи двоичных данных, а также его также можно использовать для записи
     * текстовых данных.
     * Path filePath = Path.of(imageDir, filename);
     * Write bytes
     * Files.write(filePath, image.getBytes());
     *
     */
    public String uploadImage(MultipartFile image, String name) {
        String extension = StringUtils.getFilenameExtension(image.getOriginalFilename());
        String filename = UUID.randomUUID() + "." + extension;
        Path filePath = Path.of(imageDir, filename);
        try {
            Files.write(filePath, image.getBytes());
        } catch (IOException e) {
            log.error("Error writing file: {}", e.getMessage());
            throw new RuntimeException("Error writing file", e);
        }
        log.trace("Loaded file, name: ", filename);
        return name + "/image/" + filename;
    }

    public byte[] downloadImage(String name) throws IOException {
        String fullPath = imageDir + "/" + name;
        File file = new File(fullPath);
        log.info("Downloading image " + fullPath);
        if (file.exists()) {
            return Files.readAllBytes(Path.of(fullPath));
        }
        log.info("File is not found " + fullPath);
        return null;
    }

    public void deleteFile(String path) {

        if (path == null) {
            return;
        }

        String fileName = path.substring(path.lastIndexOf('/'));
        File fileToDelete = new File(imageDir + fileName);
        if (fileToDelete.exists()) {
            if (fileToDelete.delete()) {
                log.trace("File successfully deleted");
            } else {
                log.trace("Failed to delete file");
            }
        } else {
            log.trace("File not found");
        }
    }
}



