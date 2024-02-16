//package com.pickpick.server.global.file;
//
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.ObjectMetadata;
//import com.pickpick.server.global.file.exception.FileException;
//import com.pickpick.server.global.file.exception.FileExceptionType;
//import com.pickpick.server.global.util.S3Util;
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class AwsFileService implements FileService{
//
//	private final AmazonS3Client amazonS3Client;
//
//	@Value("${cloud.aws.s3.bucket}")
//	private String bucket;
//
//	private String PROFILE_IMG_DIR = "profile\\";
//	private String MEMBER_IMG_DIR = "member\\";
//	@Override
//	public String savePhoto(MultipartFile multipartFile, Long memberId) throws FileException {
//		String filePath = bucket + MEMBER_IMG_DIR;
//		createDir(filePath, memberId + "");
//
//		String fileName = multipartFile.getOriginalFilename();
//		ObjectMetadata metadata= new ObjectMetadata();
//		metadata.setContentType(multipartFile.getContentType());
//		metadata.setContentLength(multipartFile.getSize());
//		amazonS3Client.putObject(bucket,fileName,multipartFile.getInputStream(),metadata);
//
//
//		try {
//			String fileName = multipartFile.getOriginalFilename();
//			ObjectMetadata metadata= new ObjectMetadata();
//			metadata.setContentType(multipartFile.getContentType());
//			metadata.setContentLength(multipartFile.getSize());
//			amazonS3Client.putObject(bucket,fileName,multipartFile.getInputStream(),metadata);
//			return fileUrl;
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new FileException(FileExceptionType.FILE_CAN_NOT_SAVE);
//		}
//	}
//
//	@Override
//	public String saveProfileImg(MultipartFile multipartFile) throws FileException {
//		return null;
//	}
//
//	public void createDir(String bucketName, String folderName) {
//		amazonS3Client.putObject(bucketName, folderName + "/", new ByteArrayInputStream(new byte[0]), new ObjectMetadata());
//	}
//
//	@Override
//	public void delete(String filePath) {
//
//	}
//}
