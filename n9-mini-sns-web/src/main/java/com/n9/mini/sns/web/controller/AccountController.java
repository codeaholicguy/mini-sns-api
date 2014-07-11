/**
 * 
 */
package com.n9.mini.sns.web.controller;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.n9.mini.sns.domain.commons.utils.StringUtils;
import com.n9.mini.sns.domain.dto.AccountDto;
import com.n9.mini.sns.domain.po.AccessToken;
import com.n9.mini.sns.services.AccountServices;
import com.n9.mini.sns.web.config.SessionConfiguration;
import com.n9.mini.sns.web.response.AccountInfoResponse;
import com.n9.mini.sns.web.response.BaseResponse;
import com.n9.mini.sns.web.response.FollowInfoResponse;
import com.n9.mini.sns.web.response.LoginResponse;
import com.n9.mini.sns.web.response.SearchAccountResponse;

/**
 * @author HoangNN6
 * 
 */
@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	AccountServices accountServices;

	@RequestMapping(value = "/info", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object info(@RequestParam(value = "accessToken", required = true) String accessToken) {
		BaseResponse response;
		Integer accountId;
		try {
			accountId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		try {
			AccountInfoResponse accountInfoResponse = new AccountInfoResponse();

			accountInfoResponse.setResult(BaseResponse.SUCCESS);
			accountInfoResponse.setAccount(accountServices.getAccountById(accountId));
			return accountInfoResponse;
		} catch (HibernateException e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}
	}

	@RequestMapping(value = "/get/followInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object getFollowers(@RequestParam(value = "accountId", required = true) Integer accountId, @RequestParam(value = "accessToken", required = true) String accessToken) {
		try {
			SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			BaseResponse response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		FollowInfoResponse followInfoResponse = new FollowInfoResponse();
		try {
			followInfoResponse.setResult(BaseResponse.SUCCESS);
			followInfoResponse.setInfo(accountServices.getFollowInfoByAccount(accountId));
		} catch (HibernateException e) {
			followInfoResponse.setResult(BaseResponse.FAIL);
			followInfoResponse.setError(e.getMessage());
		} catch (Exception e) {
			followInfoResponse.setResult(BaseResponse.FAIL);
			followInfoResponse.setError(e.getMessage());
		}
		return followInfoResponse;
	}

	@RequestMapping(value = "/follow", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object follow(@RequestParam(value = "followedId", required = true) Integer followedId, @RequestParam(value = "accessToken", required = true) String accessToken) {
		BaseResponse response;
		Integer followerId;
		try {
			followerId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		try {
			accountServices.follow(followedId, followerId);
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		response = new BaseResponse(BaseResponse.SUCCESS, null);
		return response;
	}

	@RequestMapping(value = "/unfollow", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object unfollow(@RequestParam(value = "followedId", required = true) Integer followedId, @RequestParam(value = "accessToken", required = true) String accessToken) {
		BaseResponse response;
		Integer followerId;
		try {
			followerId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		try {
			accountServices.unfollow(followedId, followerId);
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		response = new BaseResponse(BaseResponse.SUCCESS, null);
		return response;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	BaseResponse register(@RequestParam(value = "email", required = true) String email, @RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "screenname", required = false) String screenName, @RequestParam(value = "avatar", required = false) String avatar) {
		BaseResponse response = new BaseResponse();
		try {
			AccountDto accountDto = new AccountDto(email, password, screenName, avatar);
			accountServices.save(accountDto);
			response.setResult(BaseResponse.SUCCESS);
		} catch (Exception ex) {
			response.setResult(BaseResponse.FAIL);
			response.setError(ex.getMessage());
		}

		return response;
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object search(@RequestParam(value = "screenname", required = true) String screenname, @RequestParam(value = "accessToken", required = true) String accessToken) {
		BaseResponse response;
		Integer accountId;
		try {
			accountId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		try {
			SearchAccountResponse searchAccountResponse = new SearchAccountResponse();
			searchAccountResponse.setResult(BaseResponse.SUCCESS);
			searchAccountResponse.setAccounts(accountServices.getAccountsLikeScreenname(screenname, accountId));

			return searchAccountResponse;
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	LoginResponse login(@RequestParam(value = "email", required = true) String email, @RequestParam(value = "password", required = true) String password) {
		LoginResponse response = new LoginResponse();
		try {
			AccessToken accessToken = accountServices.login(email, password);
			if (accessToken != null) {
				accessToken = SessionConfiguration.pushSession(accessToken);

				response.setAccessToken(accessToken.getAccessToken());
				response.setRefreshToken(accessToken.getRefreshToken());
				response.setAccountId(accessToken.getAccountId());
				response.setResult(BaseResponse.SUCCESS);
			} else {
				response.setResult(BaseResponse.FAIL);
				response.setError(LoginResponse.WRONG_ACCOUNT);
			}
		} catch (Exception ex) {
			response.setResult(BaseResponse.FAIL);
			response.setError(ex.getMessage());
		}

		return response;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object logout(@RequestParam(value = "accessToken", required = true) String accessToken) {
		BaseResponse response;
		try {
			SessionConfiguration.removeSession(accessToken);
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}
		response = new BaseResponse(BaseResponse.SUCCESS, null);

		return response;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object update(@RequestParam(value = "accessToken", required = true) String accessToken, @RequestParam(value = "screenname", required = false) String screenName,
			@RequestParam(value = "avatar", required = false) String avatar) {
		BaseResponse response;
		Integer accountId;
		try {
			accountId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		try {
			AccountDto accountDto = new AccountDto();

			accountDto.setId(accountId);
			if (!StringUtils.isNullorEmpty(screenName)) {
				accountDto.setScreenName(screenName);
			}
			if (!StringUtils.isNullorEmpty(avatar)) {
				accountDto.setAvatar(avatar);
			}
			accountServices.update(accountDto);
		} catch (HibernateException e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		response = new BaseResponse(BaseResponse.SUCCESS, null);
		return response;
	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object changePassword(@RequestParam(value = "accessToken", required = true) String accessToken, @RequestParam(value = "new", required = true) String newPassword,
			@RequestParam(value = "old", required = true) String oldPassword) {
		BaseResponse response;
		Integer accountId;
		try {
			accountId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		try {
			accountServices.checkPassword(accountId, oldPassword);
			AccountDto accountDto = new AccountDto();
			accountDto.setId(accountId);
			accountDto.setPassword(newPassword);
			accountServices.update(accountDto);
		} catch (HibernateException e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		response = new BaseResponse(BaseResponse.SUCCESS, null);
		return response;
	}
}