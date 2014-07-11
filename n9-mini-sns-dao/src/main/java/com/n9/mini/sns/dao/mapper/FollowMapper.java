/**
 * 
 */
package com.n9.mini.sns.dao.mapper;

import java.util.HashSet;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.n9.mini.sns.dao.po.Account;
import com.n9.mini.sns.domain.dto.FollowDto;

/**
 * @author HoangNN6
 * 
 */
public class FollowMapper {

	AccountMapper accountMapper = new AccountMapper();

	/**
	 * @param followDto
	 * @return
	 * @throws Exception 
	 */
	public Account toPO(FollowDto followDto) throws Exception {
		Preconditions.checkNotNull(followDto, "follow info is null");
		Account account = accountMapper.toPO(followDto.getAccount());
		account.setAccountsForFollowerId(new HashSet<Account>(accountMapper.toPO(followDto.getFollowers())));
		account.setAccountsForFollowedId(new HashSet<Account>(accountMapper.toPO(followDto.getFolloweds())));

		return account;
	}

	/**
	 * @param account
	 * @return
	 * @throws Exception
	 */
	public FollowDto toDTO(Account account) throws Exception {
		Preconditions.checkNotNull(account, "account is null");
		FollowDto followDto = new FollowDto();
		followDto.setAccount(accountMapper.toDTO(account));
		followDto.setFollowers(accountMapper.toDTO(Lists.newArrayList(account.getAccountsForFollowerId())));
		followDto.setFolloweds(accountMapper.toDTO(Lists.newArrayList(account.getAccountsForFollowedId())));

		return followDto;
	}
}
