package com.test.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.test.entity.FileAttachment;

@Repository
public interface FileAttachmentRepository extends JpaRepository<FileAttachment, String>, JpaSpecificationExecutor<FileAttachment>,
		PagingAndSortingRepository<FileAttachment, String> {

	
	
}
