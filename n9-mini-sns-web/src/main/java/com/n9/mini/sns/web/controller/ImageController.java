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

import com.n9.mini.sns.services.ImageServices;
import com.n9.mini.sns.web.config.SessionConfiguration;
import com.n9.mini.sns.web.response.BaseResponse;

/**
 * @author HoangNN6
 * 
 */
@Controller
@RequestMapping("/image")
public class ImageController {

	@Autowired
	ImageServices imageServices;

	@RequestMapping(value = "/get/newest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object getImages(@RequestParam(value = "accountId", required = true) Integer accountId, @RequestParam(value = "quantity", required = true) Integer quantity,
			@RequestParam(value = "accessToken", required = true) String accessToken) {
		try {
			SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			BaseResponse response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}
		try {
			return imageServices.getNewestImages(accountId, quantity);
		} catch (HibernateException e) {
			BaseResponse response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		} catch (Exception e) {
			BaseResponse response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}
	}
}