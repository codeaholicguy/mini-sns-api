/**
 * 
 */
package com.n9.mini.sns.web.response;

import java.util.List;

import com.n9.mini.sns.domain.dto.AccountDto;

/**
 * @author HoangNN6
 * 
 */
public class SearchAccountResponse extends BaseResponse {

	List<AccountDto> accounts;

	public List<AccountDto> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<AccountDto> accounts) {
		this.accounts = accounts;
	}

}
