/**
 * 
 */
package com.n9.mini.sns.services;

import java.util.List;

import org.hibernate.HibernateException;

import com.n9.mini.sns.domain.dto.AccountDto;
import com.n9.mini.sns.domain.dto.CommentDto;

/**
 * @author HoangNN6
 * 
 */
public interface CommentServices {

	Integer save(CommentDto commentDto, Integer feedId) throws Exception;

	CommentDto getCommentById(Integer id, Integer accountId) throws HibernateException, Exception;

	List<Integer> getLikerIds(Integer commentId) throws HibernateException, Exception;

	Boolean isLiked(Integer commentId, Integer accountId) throws HibernateException, Exception;

	List<AccountDto> getLikers(Integer commentId) throws HibernateException, Exception;

	List<CommentDto> getCommentsByFeed(Integer feedId, Integer accountId) throws HibernateException, Exception;

	void like(Integer id, Integer accountId) throws HibernateException, Exception;

	void unlike(Integer id, Integer accountId) throws HibernateException, Exception;

}
