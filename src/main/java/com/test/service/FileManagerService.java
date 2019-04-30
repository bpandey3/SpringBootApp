package com.test.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.test.dao.repository.FileRepository;
import com.test.entity.FileAttachment;
import com.test.entity.FileMetaData;
import com.test.exception.ApplicationException;

@Service
public class FileManagerService {

	public final Logger LOGGER = LogManager.getLogger(FileManagerService.class);

	@Autowired
	FileRepository fileRepository;
	
	@Value(value = "${file.upload.path}")
	private String uploadPath;

	@Value(value = "${file.upload.maxSize}")
	private int fileSize;

	private String fileTypes[] = { "pdf", "jpeg", "jpg", "doc", "docx" };

	public int uploadFiles(String destination, String userId, MultipartFile[] files) throws ApplicationException {
		int counter=0;
		if (StringUtils.isEmpty(destination)) {
			destination = uploadPath;
		}

		for (MultipartFile file : files) {
			String fileName = file.getOriginalFilename();
			long fileSizeInKB = file.getSize() / 1024;	
			if(!(Arrays.stream(fileTypes).parallel().anyMatch(file.getContentType()::contains))) {
				LOGGER.error("File type is not valid in (pdf, jpeg, jpg, doc, docx) "+fileName);
				continue;
			}
			if(fileSizeInKB > fileSize) {
				LOGGER.error("File Size is exceeding the max size limit"+fileName);
						continue;
			}
			try {
				if(saveFilesInSystem(destination, file)) {
					saveFilesInDB(file, destination, userId);
					counter++;
				}

			} catch (IOException e) {
				LOGGER.error(e.getStackTrace());
				throw new ApplicationException(e.getMessage());
			}

			LOGGER.info(
					"File " + fileName + " being uploaded to system at destination: " + destination + " and database");

		}
		return counter;
	}

	private void saveFilesInDB(MultipartFile file, String destination, String userId) throws IOException {
		
		FileMetaData data = new FileMetaData(UUID.randomUUID().toString(), file.getOriginalFilename(),
				file.getContentType(), file.getSize(), destination, new FileAttachment(file.getBytes()), userId);

		fileRepository.save(data);

	
		

	}

	private boolean saveFilesInSystem(String destination, MultipartFile file) throws ApplicationException {

		String fileName = file.getOriginalFilename();
		String outFileName = destination + File.separator + getFileName(fileName);
		try {
			Path path = Paths.get(outFileName);
			byte[] bytes = file.getBytes();
			Files.write(path, bytes);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.error(e.toString());
			throw new ApplicationException(e.toString());
		}

	}

	/**
	 * Separates the filename from absolute file path
	 * 
	 * @param fileName
	 * @return only file Name excluding path
	 */
	private String getFileName(String fileName) {
		return FilenameUtils.getName(fileName);
	}

	public List<FileMetaData> getFileMetaData() {
		return fileRepository.findAll();
	}

	public FileMetaData getFileMessage(String fileId) {
		return fileRepository.findById(fileId).orElse(null);
	}

	public Page<FileMetaData> findFilesMetaData(Specification<FileMetaData> fileDataSpec, Pageable pageable) {
		return fileRepository.findAll(fileDataSpec, pageable);
	}

}
