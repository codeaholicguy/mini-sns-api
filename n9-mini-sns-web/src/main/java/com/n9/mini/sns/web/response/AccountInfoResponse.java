/**
 * 
 */
package com.n9.mini.sns.web.response;

import com.n9.mini.sns.domain.dto.AccountDto;


/**
 * @author HoangNN6
 * 
 */
public class AccountInfoResponse extends BaseResponse {

	private AccountDto account;

	public AccountDto getAccount() {
		return account;
	}

	public void setAccount(AccountDto account) {
		this.account = account;
	}

	
}
