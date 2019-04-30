package com.test.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.test.dao.spec.SearchCriteria;
import com.test.entity.FileMetaData;



public class FileSpec implements Specification<FileMetaData>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private SearchCriteria criteria;

	public FileSpec(SearchCriteria searchCriteria) {
		this.criteria=searchCriteria;
	}

	@Override
	public Predicate toPredicate(Root<FileMetaData> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if(criteria.getOperation().equals("like")) {
			if(root.get(criteria.getKey()).getJavaType()==String.class) {
				return builder.like(root.<String>get(criteria.getKey()), "%"+criteria.getValue()+"%");
			}			
		}
		if(criteria.getOperation().equals("==")) {
			if(root.get(criteria.getKey()).getJavaType()==String.class) {
				return builder.equal(root.<String>get(criteria.getKey()),criteria.getValue() );
			}			
		}
		return null;
	}

}
