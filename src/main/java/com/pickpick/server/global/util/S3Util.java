package com.pickpick.server.global.util;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.pickpick.server.global.file.exception.FileException;
import com.pickpick.server.global.file.exception.FileExceptionType;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@RequiredArgsConstructor
@Service
public class S3Util {

	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	// 폴더 생성
	public void createFolder(String bucketName, String folderName) {
		amazonS3Client.putObject(bucketName, folderName + "/", new ByteArrayInputStream(new byte[0]), new ObjectMetadata());
	}

	// 다중 파일 업로드
	public void fileUpload(List<MultipartFile> files, List<String> fileList) throws Exception {
		if(amazonS3Client != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Date date = new Date();
			String today = sdf.format(date);

			if(!files.isEmpty()) {
				createFolder(bucket + "/contact", today);
			}

			ObjectMetadata objectMetadata = new ObjectMetadata();
			for(int i=0; i<files.size(); i++) {
				objectMetadata.setContentType(files.get(i).getContentType());
				objectMetadata.setContentLength(files.get(i).getSize());
				objectMetadata.setHeader("filename", files.get(i).getOriginalFilename());
				amazonS3Client.putObject(new PutObjectRequest(bucket + "/contact/" + today, fileList.get(i), files.get(i).getInputStream(), objectMetadata));
			}
		} else {
			throw new FileException(FileExceptionType.FILE_CAN_NOT_SAVE);
		}
	}

	// 다중 파일 삭제
	public void fileDelete(String filePath, String fileName) {
		if(amazonS3Client != null) {
			amazonS3Client.deleteObject(new DeleteObjectRequest(filePath, fileName));
		} else {
			throw new FileException(FileExceptionType.FILE_CAN_NOT_DELETE);
		}
	}
}
