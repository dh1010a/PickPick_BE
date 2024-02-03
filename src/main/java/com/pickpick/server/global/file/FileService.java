package com.pickpick.server.global.file;

import com.pickpick.server.global.file.exception.FileException;
import java.nio.file.FileSystemException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	String save(MultipartFile multipartFile) throws FileException;

	void delete(String filePath);
}
