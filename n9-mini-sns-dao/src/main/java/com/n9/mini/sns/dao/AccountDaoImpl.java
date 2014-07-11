package com.n9.mini.sns.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.n9.mini.sns.dao.po.Account;

/**
 * @author HoangNN6
 * 
 */
@Repository
public class AccountDaoImpl extends BaseDao implements AccountDao {

	private final String ID_PARAMETER = "id";
	private final String EMAIL_PARAMETER = "email";
	private final String PASSWORD_PARAMETER = "password";
	private final String SCREENNAME_PARAMETER = "screenName";

	@Override
	public Integer save(Account account) throws HibernateException {
		return (Integer) getSession().save(account);
	}

	@Override
	public void update(Account account) throws HibernateException {
		getSession().update(account);
		getSession().flush();
	}

	@Override
	public Account findAccountById(Integer id) throws HibernateException {
		Criteria cr = getSession().createCriteria(Account.class);
		cr.add(Restrictions.eq(ID_PARAMETER, id));
		return CollectionUtils.isEmpty(cr.list()) ? null : (Account) cr.list().get(0);
	}

	@Override
	public Account findAccountByEmail(String email) throws HibernateException {
		Criteria cr = getSession().createCriteria(Account.class);
		cr.add(Restrictions.eq(EMAIL_PARAMETER, email));
		return CollectionUtils.isEmpty(cr.list()) ? null : (Account) cr.list().get(0);
	}

	@Override
	public Account findAccountByEmailAndPassword(String email, String password) throws HibernateException {
		Criteria cr = getSession().createCriteria(Account.class);
		cr.add(Restrictions.eq(EMAIL_PARAMETER, email));
		cr.add(Restrictions.eq(PASSWORD_PARAMETER, password));
		return CollectionUtils.isEmpty(cr.list()) ? null : (Account) cr.list().get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> findAccountsLikeScreenname(String screenname) throws HibernateException {
		Criteria cr = getSession().createCriteria(Account.class);
		cr.add(Restrictions.like(SCREENNAME_PARAMETER, "%" + screenname + "%"));
		return cr.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Account> findAllAccounts() throws HibernateException {
		Criteria cr = getSession().createCriteria(Account.class);
		return cr.list();
	}

}
