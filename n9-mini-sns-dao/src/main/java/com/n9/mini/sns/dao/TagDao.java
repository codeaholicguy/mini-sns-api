package com.n9.mini.sns.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.n9.mini.sns.dao.po.Tag;

/**
 * @author HoangNN6
 * 
 */
public interface TagDao {

	Integer save(Tag tag) throws HibernateException;

	Tag findTagById(Integer id) throws HibernateException;

	List<Tag> findAllTags() throws HibernateException;
	
	List<Tag> findTagsByImage(Integer imageId) throws HibernateException;

}