package com.n9.mini.sns.dao;

import java.util.List;

import org.hibernate.HibernateException;

import com.n9.mini.sns.dao.po.Account;

/**
 * @author HoangNN6
 * 
 */
public interface AccountDao {

	Integer save(Account account) throws HibernateException;

	void update(Account account) throws HibernateException;

	Account findAccountById(Integer id) throws HibernateException;

	Account findAccountByEmail(String email) throws HibernateException;

	Account findAccountByEmailAndPassword(String email, String password) throws HibernateException;

	List<Account> findAllAccounts() throws HibernateException;

	List<Account> findAccountsLikeScreenname(String screenname) throws HibernateException;

}