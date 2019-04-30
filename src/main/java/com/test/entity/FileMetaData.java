package com.test.entity;


import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="T_FILE_META_DATA")
public class FileMetaData implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="FILE_ID")
	private String id;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name="FILE_NAME")
	private String fileName;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name="FILE_TYPE")
	private String fileType;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name="FILE_SIZE")
	private long fileSize;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name="FILE_PATH")
	private String filePath;

	
	@JsonInclude(Include.NON_NULL)
	@Column(name="CREATE_TIME")
	private LocalDateTime crtTs;
	
	@JsonInclude(Include.NON_NULL)
	@Column(name="CREATED_BY")
	private String crtBy;
	
	@OneToOne(mappedBy="fileMetaData", cascade=CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
	//@LazyToOne(LazyToOneOption.NO_PROXY)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	@JsonInclude(Include.NON_NULL)
    private FileAttachment attachment;
	
		
	/**
	 * @return the attachment
	 */
	public FileAttachment getAttachment() {
		return attachment;
	}


	/**
	 * @param attachment the attachment to set
	 */
	public void setAttachment(FileAttachment attachment) {
		this.attachment = attachment;
	}


	public FileMetaData(String id, String fileName, String fileType, long fileSize, String filePath,
			FileAttachment fileMessage, String crtBy) {
		super();
		this.id = id;
		this.fileName = fileName;
		this.fileType=fileType;
		this.fileSize = fileSize;
		this.filePath = filePath;
		this.attachment = fileMessage;
		this.attachment.setFileData(this);
		this.crtBy = crtBy;
		this.crtTs=LocalDateTime.now();
	}
	
	
	public FileMetaData() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the crtTs
	 */
	public LocalDateTime getCrtTs() {
		return crtTs;
	}
	/**
	 * @param crtTs the crtTs to set
	 */
	public void setCrtTs(LocalDateTime crtTs) {
		this.crtTs = crtTs;
	}
	/**
	 * @return the crtBy
	 */
	public String getCrtBy() {
		return crtBy;
	}
	/**
	 * @param crtBy the crtBy to set
	 */
	public void setCrtBy(String crtBy) {
		this.crtBy = crtBy;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}
	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	/**
	 * @return the fileSize
	 */
	public long getFileSize() {
		return fileSize;
	}
	/**
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	

}
