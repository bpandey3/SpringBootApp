package com.test.entity;



import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


@Entity
@Table(name="T_FILE_ATTACHMENT")
public class FileAttachment implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ATTACHMENT_ID")
	private String attachmentId;

	@Column(name="ATTACHMENT")
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] fileMessage;
	
	@OneToOne
	@PrimaryKeyJoinColumn
    private FileMetaData fileMetaData;
	
	

	public FileAttachment(byte[] fileMessage, FileMetaData fileMetaData) {
		super();
		this.fileMessage = fileMessage;
		this.fileMetaData = fileMetaData;
		this.fileMetaData.setAttachment(this);
		
	}

	public FileAttachment(byte[] fileMessage) {
		super();
		this.fileMessage = fileMessage;
	}
	
	public FileAttachment() {
		super();
	}


	/**
	 * @return the fileData
	 */
	public FileMetaData getFileData() {
		return fileMetaData;
	}
	/**
	 * @param fileData the fileData to set
	 */
	public void setFileData(FileMetaData fileMetaData) {
		this.fileMetaData = fileMetaData;
	}
	
	/**
	 * @return the fileMessage
	 */
	
	public byte[] getFileMessage() {
		return fileMessage;
	}
	/**
	 * @param fileMessage the fileMessage to set
	 */
	public void setFileMessage(byte[] fileMessage) {
		this.fileMessage = fileMessage;
	}

	/**
	 * @return the attachmentId
	 */
	public String getAttachmentId() {
		return attachmentId;
	}

	/**
	 * @param attachmentId the attachmentId to set
	 */
	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}
	
	

}
