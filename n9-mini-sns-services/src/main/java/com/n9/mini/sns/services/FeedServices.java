/**
 * 
 */
package com.n9.mini.sns.services;

import java.util.List;

import org.hibernate.HibernateException;

import com.n9.mini.sns.domain.dto.AccountDto;
import com.n9.mini.sns.domain.dto.FeedDto;

/**
 * @author HoangNN6
 * 
 */
public interface FeedServices {

	Integer save(FeedDto feedDto) throws HibernateException, Exception;

	Integer save(Integer imageId, Integer accountId, String status, String url) throws HibernateException, Exception;

	FeedDto getFeedById(Integer id, Integer accountId) throws HibernateException, Exception;

	List<FeedDto> getAllFeeds() throws HibernateException, Exception;

	List<FeedDto> getNewFeeds(Integer quantity, Integer accountId) throws HibernateException, Exception;

	List<FeedDto> getFeedsByAccount(Integer accountId) throws HibernateException, Exception;

	List<Integer> getLikerIds(Integer feedId) throws HibernateException, Exception;

	List<AccountDto> getLikers(Integer feedId) throws HibernateException, Exception;

	Boolean isLiked(Integer feedId, Integer accountId) throws HibernateException, Exception;

	void like(Integer id, Integer accountId) throws HibernateException, Exception;

	void unlike(Integer id, Integer accountId) throws HibernateException, Exception;

	void remove(Integer id, Integer accountId) throws HibernateException, Exception;

}
