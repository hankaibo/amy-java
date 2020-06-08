package cn.mypandora.springboot.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import cn.mypandora.springboot.config.exception.StorageException;

/**
 * 
 * 文件工具类，保存。
 * 
 * @author hankaibo
 * @date 2020/6/8
 */
public class FileUtil {

    private static final String FILE_NAME = "..";

    /**
     * 保存文件。
     * 
     * @param file
     *            要保存的文件
     * @param dirPath
     *            要存储的路径
     * @param remoteUrl
     *            客户端通过浏览器访问的路径前缀
     * @return 客户端通过浏览器访问的全路径
     */
    public static String saveFile(MultipartFile file, String dirPath, String remoteUrl) {
        Path rootLocation = Paths.get(dirPath);
        String filename =
            org.springframework.util.StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + filename);
            }
            if (filename.contains(FILE_NAME)) {
                throw new StorageException(
                    "Cannot store file with relative path outside current directory " + filename);
            }
            String name = UUID.randomUUID().toString();
            String suffix = filename.substring((filename.lastIndexOf('.')));
            String newFilename = name + suffix;
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, rootLocation.resolve(newFilename), StandardCopyOption.REPLACE_EXISTING);
            }
            return remoteUrl + newFilename;
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + filename, e);
        }
    }

}
