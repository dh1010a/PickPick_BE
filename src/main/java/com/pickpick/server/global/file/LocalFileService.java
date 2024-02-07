package com.pickpick.server.global.file;

import com.pickpick.server.global.file.exception.FileExceptionType;
import com.pickpick.server.global.file.exception.FileException;
import com.pickpick.server.photo.domain.Photo;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class LocalFileService implements FileService {

	@Value("${file.dir}")
	private String fileDir;

	private String PROFILE_IMG_DIR = "profile\\";
	private String MEMBER_IMG_DIR = "member\\";

	public LocalFileService(String fileDir) {
		createDir(fileDir + PROFILE_IMG_DIR);
		createDir(fileDir + MEMBER_IMG_DIR);
	}

	@Override
	public String savePhoto(MultipartFile multipartFile, Photo photo) throws FileException {
		String filePath = fileDir + MEMBER_IMG_DIR;

		return filePath;
	}

	@Override
	public String saveProfileImg(MultipartFile multipartFile) throws FileException {
		String filePath = fileDir + PROFILE_IMG_DIR + UUID.randomUUID() + multipartFile.getOriginalFilename();
		try {
			multipartFile.transferTo(new File(filePath));
		} catch (IOException e) {
			throw new FileException(FileExceptionType.FILE_CAN_NOT_SAVE);
		}
		return filePath;
	}

	private static void createDir(String filePath){
		try {
			Files.createDirectory(Paths.get(filePath));
		} catch (FileAlreadyExistsException e) {

		} catch (NoSuchFileException e) {
			throw new FileException(FileExceptionType.FILE_CAN_NOT_SAVE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(String filePath) {

	}
}
