package com.pickpick.server.global.file;

import com.pickpick.server.global.file.exception.FileException;
import com.pickpick.server.photo.domain.Photo;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	String savePhoto(MultipartFile multipartFile, Long memberId) throws IOException;

	String saveProfileImg(MultipartFile multipartFile, Long memberId) throws IOException;

	void delete(String filePath);
}
