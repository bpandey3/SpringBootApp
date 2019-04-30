package com.test.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.test.dao.FileSpec;
import com.test.dao.spec.SearchCriteria;
import com.test.entity.FileMetaData;
import com.test.exception.ApplicationException;
import com.test.model.ApplicationResponse;
import com.test.service.FileManagerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author pink
 *
 */
@RestController
@RequestMapping("/api")
@Api(value="File Meta-Data Upload Service")
public class FileController {

	private final static Logger LOGGER = LogManager.getLogger(FileController.class);

	@Autowired
	FileManagerService fileManagerService;

	/**
	 * To upload one or multiple files and persist meta-data in system and DB
	 * @param destination
	 * @param userId
	 * @param description
	 * @param files
	 * @return
	 */
	@PostMapping(value = "/uploadFiles", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value="Upload files", httpMethod="POST", notes="To upload one or multiple files and persist meta-data in system and DB")
	public ResponseEntity<?> uploadFiles(@RequestParam(value = "path", required = false) String destination,
			@ApiParam(value="user Id of the person who is uploading the file", required=true)
			@RequestParam(value = "userId", required = true) String userId,
			@ApiParam(value="Description about the file to be uploaded", required=false)
			@RequestParam(value = "description", required = false) String description,
			@ApiParam(value="One file or Multiple files to be chosen for upload size less than 200MB", required=true)
			@RequestParam(value = "files", required = true) MultipartFile[] files) {

		try {

			int fileCount = files.length;
			LOGGER.info("Total file to be uploaded " + fileCount);
			if (fileCount == 0) {
				return new ResponseEntity<ApplicationResponse>(
						new ApplicationResponse(ApplicationResponse.Status.PARAMETER_MISSING.value(),
								"No File(s) Selected to be upload."),
						HttpStatus.PARTIAL_CONTENT);

			}
			int fileSuccessCount = fileManagerService.uploadFiles(destination, userId, files);
		
			return new ResponseEntity<>(new ApplicationResponse(ApplicationResponse.Status.CREATED.value(),
					fileSuccessCount + " File(s) uploaded successfully. "), HttpStatus.OK);
		} catch (ApplicationException e) {
			return new ResponseEntity<>(new ApplicationResponse(ApplicationResponse.Status.SERVER_ERROR.value(),
					"Failed to process the request. "), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	/**
	 * To search the file data with search criteria 
	 * @param fileId
	 * @param fileName
	 * @param pageNo
	 * @param pageSize
	 * @param sortKey
	 * @param sortOrder
	 * @param pageable
	 * @return
	 */
	@GetMapping(value = "/getAllFilesMetaData")
	@ApiOperation(value="get files Meta-Data", httpMethod="GET", notes="To fetch meta-data with fileId and fileName as search criteria")
	public ResponseEntity<?> getFilesMetaData(@RequestParam(value = "fileId", required = false) String fileId,
			@RequestParam(value = "fileName", required = false) String fileName,
			@RequestParam(value = "pageNo", required = false, defaultValue = "0") int pageNo,
			@RequestParam(value = "pageSize", required = false, defaultValue = "5") int pageSize,
			@RequestParam(value = "sortKey", required = false, defaultValue = "crtTs") String sortKey,
			@RequestParam(value = "sortOrder", required = false, defaultValue = "DESC") String sortOrder,
			Pageable pageable) {
		Specification<FileMetaData> specification = null;

		if (!StringUtils.isEmpty(fileId)) {
			FileSpec fileSpec = new FileSpec(new SearchCriteria("id", "==", fileId));
			specification = (specification != null) ? specification.and(fileSpec) : Specification.where(fileSpec);

		}

		if (!StringUtils.isEmpty(fileName)) {
			FileSpec fileSpec = new FileSpec(new SearchCriteria("fileName", "like", fileName));
			specification = Specification.where(fileSpec);

		}
		
		
		

		Page<FileMetaData> fileMetaData = fileManagerService.findFilesMetaData(specification,
				getPageRequest(pageNo, pageSize, sortKey, sortOrder));
		return new ResponseEntity<Page<FileMetaData>>(fileMetaData, HttpStatus.OK);
	}

	private Pageable getPageRequest(int pageNo, int pageSize, String sortKey, String sortOrder) {
		Direction directionSortOrder = sortOrder.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
		return PageRequest.of(pageNo, pageSize, directionSortOrder, sortKey);
	}

	/**
	 * To Download the file content
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/downloadFile/{fileId}")
	@ApiOperation(value="download File Content", httpMethod="GET", notes="To download the file content at local system, get the file content by fileId from DB")
	public ResponseEntity<?> getFilesMetaData(@PathVariable String fileId, HttpServletResponse response)
		{
		try {
		FileMetaData file = fileManagerService.getFileMessage(fileId);
		if (file == null) {
			return new ResponseEntity<>(new ApplicationResponse(ApplicationResponse.Status.NO_CONTENT.value(),
					"No File found with file Id: " + fileId), HttpStatus.OK);
		}
		response.setContentType(file.getFileType());
		response.setContentLength(file.getAttachment().getFileMessage().length);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getFileName() + "\""));
		FileCopyUtils.copy(file.getAttachment().getFileMessage(), response.getOutputStream());
		return new ResponseEntity<>(new ApplicationResponse(ApplicationResponse.Status.SUCCESS.value(),
				"File downloaded with file Id: " + fileId), HttpStatus.OK);
		}catch(Exception ex) {
			LOGGER.error(ex.getMessage());
			return new ResponseEntity<>(new ApplicationResponse(ApplicationResponse.Status.NO_CONTENT.value(),
					"Failed to process the request. "), HttpStatus.NO_CONTENT);
		}

	}

}
