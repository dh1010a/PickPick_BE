package com.pickpick.server.global.file;

import com.pickpick.server.global.file.exception.FileException;
import com.pickpick.server.global.file.exception.FileExceptionType;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class FileServiceImpl implements FileService {

	@Value("${file.dir}")
	private String fileDir;

	@Override
	public String save(MultipartFile multipartFile) throws FileException {
		String filePath = fileDir + UUID.randomUUID();
		try {
			multipartFile.transferTo(new File(filePath));
		} catch (IOException e) {
			throw new FileException(FileExceptionType.FILE_CAN_NOT_SAVE);
		}
		return filePath;
	}

	@Override
	public void delete(String filePath) {

	}
}
