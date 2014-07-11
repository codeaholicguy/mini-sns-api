/**
 * 
 */
package com.n9.mini.sns.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.n9.mini.sns.dao.po.Account;
import com.n9.mini.sns.domain.dto.AccountDto;

/**
 * @author HoangNN6
 * 
 */
public class AccountMapper {

	/**
	 * @param accountDto
	 * @return
	 */
	public Account toPO(AccountDto accountDto) throws Exception {
		Preconditions.checkNotNull(accountDto, "account info is null");
		Account account = new Account();
		account.setId(accountDto.getId());
		account.setEmail(accountDto.getEmail());
		account.setPassword(accountDto.getPassword());
		account.setScreenName(accountDto.getScreenName());
		account.setAvatar(accountDto.getAvatar());

		return account;
	}

	/**
	 * @param account
	 * @return
	 */
	public AccountDto toDTO(Account account) throws Exception {
		Preconditions.checkNotNull(account, "account is null");
		AccountDto accountDto = new AccountDto();
		accountDto.setId(account.getId());
		accountDto.setEmail(account.getEmail());
		accountDto.setPassword(account.getPassword());
		accountDto.setScreenName(account.getScreenName());
		accountDto.setAvatar(account.getAvatar());

		return accountDto;
	}

	/**
	 * @param accountDtos
	 * @return
	 * @throws Exception
	 */
	public List<Account> toPO(List<AccountDto> accountDtos) throws Exception {
		List<Account> accounts = new ArrayList<Account>();
		for (AccountDto accountDto : accountDtos) {
			accounts.add(toPO(accountDto));
		}

		return accounts;
	}

	/**
	 * @param accounts
	 * @return
	 * @throws Exception
	 */
	public List<AccountDto> toDTO(List<Account> accounts) throws Exception {
		List<AccountDto> accountDtos = new ArrayList<AccountDto>();
		for (Account account : accounts) {
			accountDtos.add(toDTO(account));
		}

		return accountDtos;
	}
}
