package com.test.dao.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.test.entity.FileMetaData;

@Repository
public interface FileRepository extends JpaRepository<FileMetaData, String>, JpaSpecificationExecutor<FileMetaData>,
		PagingAndSortingRepository<FileMetaData, String> {

	//@Query("select fmd.id, fmd.fileName, fmd.fileType, fmd.fileSize, fmd.filePath, fmd.crtBy, fmd.crtTs from FileMetaData fmd")
	List<FileMetaData>findAll();
	
	//@Query("select fmd.id,  fm.fmd.fileName, fmd.fileType, fmd.fileSize, fmd.filePath, fmd.crtBy, fmd.crtTs from FileMetaData fmd")
	Page<FileMetaData> findAll(Specification<FileMetaData> fileDataSpec, Pageable pageable);
	
}
