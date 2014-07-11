package com.n9.mini.sns.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.n9.mini.sns.dao.po.Comment;

/**
 * @author HoangNN6
 * 
 */
public interface CommentDao {

	Integer save(Comment comment) throws HibernateException;

	void update(Comment comment) throws HibernateException;

	Comment findCommentById(Integer id) throws HibernateException;

	List<Comment> findCommentsByFeed(Integer feedId) throws HibernateException;

}