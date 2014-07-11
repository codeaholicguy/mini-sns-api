/**
 * 
 */
package com.n9.mini.sns.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.n9.mini.sns.services.FeedServices;
import com.n9.mini.sns.web.config.SessionConfiguration;
import com.n9.mini.sns.web.response.BaseResponse;
import com.n9.mini.sns.web.response.NewFeedResponse;
import com.n9.mini.sns.web.response.PostFeedResponse;

/**
 * @author HoangNN6
 * 
 */
@Controller
@RequestMapping("/feed")
public class FeedController {

	@Autowired
	FeedServices feedServices;

	@RequestMapping(value = "/get/newest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object getAllTags(@RequestParam(value = "quantity", required = false) Integer quantity, @RequestParam(value = "accessToken", required = true) String accessToken) {
		BaseResponse response;
		Integer accountId;
		try {
			accountId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		try {
			if (quantity == null) {
				quantity = 20;
			}
			NewFeedResponse newFeedResponse = new NewFeedResponse();
			newFeedResponse.setResult(BaseResponse.SUCCESS);
			newFeedResponse.setFeeds(feedServices.getNewFeeds(quantity, accountId));
			return newFeedResponse;
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}
	}

	@RequestMapping(value = "/post", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object getAllTags(@RequestParam(value = "accessToken", required = true) String accessToken, @RequestParam(value = "imageId", required = false) Integer imageId,
			@RequestParam(value = "status", required = false) String status, @RequestParam(value = "url", required = false) String url) {
		BaseResponse response;
		Integer accountId;
		try {
			accountId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		try {
			PostFeedResponse postFeedResponse = new PostFeedResponse();
			Integer feedId = feedServices.save(imageId, accountId, status, url);
			postFeedResponse.setResult(BaseResponse.SUCCESS);
			postFeedResponse.setFeedId(feedId);

			return postFeedResponse;
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}
	}

	@RequestMapping(value = "/like", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object like(@RequestParam(value = "accessToken", required = true) String accessToken, @RequestParam(value = "id", required = false) Integer id) {
		BaseResponse response;
		Integer accountId;
		try {
			accountId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		try {
			feedServices.like(id, accountId);
			response = new BaseResponse(BaseResponse.SUCCESS, null);
			return response;
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}
	}

	@RequestMapping(value = "/unlike", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object unlike(@RequestParam(value = "accessToken", required = true) String accessToken, @RequestParam(value = "id", required = false) Integer id) {
		BaseResponse response;
		Integer accountId;
		try {
			accountId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		try {
			feedServices.unlike(id, accountId);
			response = new BaseResponse(BaseResponse.SUCCESS, null);
			return response;
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}
	}

	@RequestMapping(value = "/remove", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object remove(@RequestParam(value = "accessToken", required = true) String accessToken, @RequestParam(value = "id", required = false) Integer id) {
		BaseResponse response;
		Integer accountId;
		try {
			accountId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		try {
			feedServices.remove(id, accountId);
			response = new BaseResponse(BaseResponse.SUCCESS, null);
			return response;
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}
	}
}