/**
 * 
 */
package com.n9.mini.sns.services;

import java.util.List;

import org.hibernate.HibernateException;

import com.n9.mini.sns.domain.dto.AccountDto;
import com.n9.mini.sns.domain.dto.FollowDto;
import com.n9.mini.sns.domain.po.AccessToken;

/**
 * @author HoangNN6
 * 
 */
public interface AccountServices {

	Integer save(AccountDto accountDto) throws HibernateException, Exception;

	void update(AccountDto accountDto) throws HibernateException, Exception;

	List<Integer> getFollowedIds(Integer accountId) throws HibernateException, Exception;

	List<AccountDto> getAllAccounts() throws HibernateException, Exception;

	List<AccountDto> getAccountsLikeScreenname(String screenname, Integer accountId) throws HibernateException, Exception;

	AccountDto getAccountById(Integer id) throws HibernateException, Exception;

	AccountDto getAccountByEmail(String email) throws HibernateException, Exception;

	AccessToken login(String email, String password) throws HibernateException, Exception;

	FollowDto getFollowInfoByAccount(Integer accountId) throws HibernateException, Exception;

	void follow(Integer followedId, Integer followerId) throws HibernateException, Exception;

	void unfollow(Integer followedId, Integer followerId) throws HibernateException, Exception;

	void checkPassword(Integer accountId, String password) throws HibernateException, Exception;

}
