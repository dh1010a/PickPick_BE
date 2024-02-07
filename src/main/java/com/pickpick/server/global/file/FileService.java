package com.pickpick.server.global.file;

import com.pickpick.server.global.file.exception.FileException;
import com.pickpick.server.photo.domain.Photo;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	String savePhoto(MultipartFile multipartFile, Photo photo) throws FileException;

	String saveProfileImg(MultipartFile multipartFile ) throws FileException;

	void delete(String filePath);
}
