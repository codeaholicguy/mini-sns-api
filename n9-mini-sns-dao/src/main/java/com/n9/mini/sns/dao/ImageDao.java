package com.n9.mini.sns.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.n9.mini.sns.dao.po.Image;


/**
 * @author HoangNN6
 * 
 */
public interface ImageDao {

	Integer save(Image image) throws HibernateException;
	
	Image findImageById(Integer id) throws HibernateException;
	
	List<Image> findAllImages() throws HibernateException;
	
	List<Image> findImagesByEmail(String email) throws HibernateException;
	
	List<Image> findImagesByAccount(Integer accountId, Integer quantity) throws HibernateException;
	
}