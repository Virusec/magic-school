package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarService {

    Logger logger = LoggerFactory.getLogger(AvatarService.class);
    @Value("${path.to.avatars.folder}")
    private String avatarsDir;
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;

    public AvatarService(AvatarRepository avatarRepository,
                         StudentRepository studentRepository) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for upload avatar");
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> {
                    logger.warn("There is no student with id = " + studentId);
                    throw new IllegalArgumentException("Student with id = " + studentId + " not found");
                });
        Path filePath = Path
                .of(avatarsDir
                        , studentId
                                + "."
                                + getExtension(Objects.requireNonNull(avatarFile.getOriginalFilename())));
        logger.debug("Create path = " + filePath);
        Files.createDirectories(filePath.getParent());
        if (Files.exists(filePath)) {
            logger.warn("Avatar file already exists, deleting...");
            Files.deleteIfExists(filePath);
        }

        try (InputStream is = avatarFile.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)) {
            logger.debug("Starting transfer of avatar file data...");
            bis.transferTo(bos);
            logger.debug("Avatar file data transfer complete");
        } catch (IOException e) {
            logger.error("Error occurred while transferring avatar file data", e);
            throw e;
        }



        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        logger.debug("Saving avatar ...");
        avatarRepository.save(avatar);
        logger.debug("Avatar saved successfully");
    }

    private String getExtension(String originalFilename) {
        logger.debug("Extracting extension from filename: " + originalFilename);
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        logger.debug("Extracted extension: " + extension);
        return extension;
    }

    public Avatar findAvatar(Long studentId) {
        logger.info("Was invoked method for find avatar");
        return avatarRepository.findByStudentId(studentId)
                .map(avatar -> {
                    logger.debug("Avatar with studentId: {} found", studentId);
                    return avatar;
                })
                .orElseGet(() -> {
                    logger.debug("Avatar with studentId: {} not found", studentId);
                    return new Avatar();
                });
    }

    public List<Avatar> getAllAvatars(Integer pageNumber, Integer pageSize) {
        logger.info("Was invoked method for get all avatars by page");
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        logger.debug("Create page requestwith pageNumber: {} and pageSize: {}", pageNumber, pageSize);
        List<Avatar> avatars = avatarRepository.findAll(pageRequest).getContent();
        logger.debug("Found {} avatars", avatars.size());
        return avatars;
    }
}
