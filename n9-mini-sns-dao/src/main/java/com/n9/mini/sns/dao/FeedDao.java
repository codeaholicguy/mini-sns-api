package com.n9.mini.sns.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.n9.mini.sns.dao.po.Feed;

/**
 * @author HoangNN6
 * 
 */
public interface FeedDao {

	Integer save(Feed feed) throws HibernateException;

	void update(Feed feed) throws HibernateException;

	void delete(Feed feed) throws HibernateException;

	Feed findFeedById(Integer id) throws HibernateException;

	List<Feed> findAllFeeds() throws HibernateException;

	List<Feed> findNewFeeds(Integer quantity, List<Integer> followeds) throws HibernateException;

	List<Feed> findFeedsByAccount(Integer accountId) throws HibernateException;

}