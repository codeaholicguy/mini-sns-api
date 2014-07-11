/**
 * 
 */
package com.n9.mini.sns.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.n9.mini.sns.dao.AccountDao;
import com.n9.mini.sns.dao.mapper.AccountMapper;
import com.n9.mini.sns.dao.mapper.FollowMapper;
import com.n9.mini.sns.dao.po.Account;
import com.n9.mini.sns.domain.dto.AccountDto;
import com.n9.mini.sns.domain.dto.FollowDto;
import com.n9.mini.sns.domain.po.AccessToken;

/**
 * @author HoangNN6
 * 
 */
public class AccountServicesImpl implements AccountServices {

	@Autowired
	AccountDao accountDao;

	AccountMapper accountMapper = new AccountMapper();
	FollowMapper followMapper = new FollowMapper();

	@Transactional(rollbackFor = Exception.class)
	public Integer save(AccountDto accountDto) throws HibernateException, Exception {
		return accountDao.save(accountMapper.toPO(accountDto));
	}

	@Transactional(rollbackFor = Exception.class)
	public void update(AccountDto accountDto) throws HibernateException, Exception {
		Account account = accountDao.findAccountById(accountDto.getId());
		account.setPassword(accountDto.getPassword() == null ? account.getPassword() : accountDto.getPassword());
		account.setScreenName(accountDto.getScreenName() == null ? account.getScreenName() : accountDto.getScreenName());
		account.setAvatar(accountDto.getAvatar() == null ? account.getAvatar() : accountDto.getAvatar());

		accountDao.update(account);
	}

	@Transactional(readOnly = true)
	public List<AccountDto> getAllAccounts() throws HibernateException, Exception {
		return accountMapper.toDTO(accountDao.findAllAccounts());
	}

	@Transactional(readOnly = true)
	public List<Integer> getFollowedIds(Integer accountId) {
		List<Account> followeds = Lists.newArrayList(accountDao.findAccountById(accountId).getAccountsForFollowedId());
		List<Integer> followedIds = new ArrayList<Integer>();
		for (Account account : followeds) {
			followedIds.add(account.getId());
		}

		return followedIds;
	}

	@Transactional(readOnly = true)
	public List<AccountDto> getAccountsLikeScreenname(String screenname, Integer accountId) throws HibernateException, Exception {
		List<AccountDto> accountDtos = accountMapper.toDTO(accountDao.findAccountsLikeScreenname(screenname));
		List<Integer> followedIds = getFollowedIds(accountId);
		for (AccountDto accountDto : accountDtos) {
			if (followedIds.contains(accountDto.getId())) {
				accountDto.setFollowed(true);
			} else {
				accountDto.setFollowed(false);
			}
		}
		return accountDtos;
	}

	@Transactional(readOnly = true)
	public AccountDto getAccountById(Integer id) throws HibernateException, Exception {
		return accountMapper.toDTO(accountDao.findAccountById(id));
	}

	@Transactional(readOnly = true)
	public AccountDto getAccountByEmail(String email) throws HibernateException, Exception {
		return accountMapper.toDTO(accountDao.findAccountByEmail(email));
	}

	@Transactional(readOnly = true)
	public FollowDto getFollowInfoByAccount(Integer accountId) throws HibernateException, Exception {
		return followMapper.toDTO(accountDao.findAccountById(accountId));
	}

	@Transactional(readOnly = true)
	public AccessToken login(String email, String password) {
		AccessToken accessToken = null;
		Account account = accountDao.findAccountByEmailAndPassword(email, password);
		if (account != null) {
			accessToken = new AccessToken();
			accessToken.setAccessToken(UUID.randomUUID().toString());
			accessToken.setRefreshToken(UUID.randomUUID().toString());
			accessToken.setAccountId(account.getId());
		}
		return accessToken;
	}

	@Transactional(rollbackFor = Exception.class)
	public void follow(Integer followedId, Integer followerId) {
		Account followed = accountDao.findAccountById(followedId);
		followed.getAccountsForFollowerId().add(accountDao.findAccountById(followerId));

		accountDao.update(followed);
	}

	@Transactional(rollbackFor = Exception.class)
	public void unfollow(Integer followedId, Integer followerId) {
		Account followed = accountDao.findAccountById(followedId);
		followed.getAccountsForFollowerId().remove(accountDao.findAccountById(followerId));

		accountDao.update(followed);
	}

	@Transactional(readOnly = true)
	public void checkPassword(Integer accountId, String password) throws HibernateException, Exception {
		AccountDto accountDto = getAccountById(accountId);
		if (!accountDto.getPassword().equals(password)) {
			throw new Exception("wrong password");
		}
	}

}
