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

import com.n9.mini.sns.domain.dto.AccountDto;
import com.n9.mini.sns.domain.dto.CommentDto;
import com.n9.mini.sns.services.CommentServices;
import com.n9.mini.sns.web.config.SessionConfiguration;
import com.n9.mini.sns.web.response.BaseResponse;
import com.n9.mini.sns.web.response.CommendResponse;
import com.n9.mini.sns.web.response.CommentResponse;
import com.n9.mini.sns.web.response.CommentsResponse;

/**
 * @author HoangNN6
 * 
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	CommentServices commentServices;

	@RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object getById(@RequestParam(value = "accessToken", required = true) String accessToken, @RequestParam(value = "id", required = true) Integer id) {
		BaseResponse response;
		Integer accountId;
		try {
			accountId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		try {
			CommentResponse commentGetResponse = new CommentResponse();
			commentGetResponse.setResult(BaseResponse.SUCCESS);
			commentGetResponse.setComment(commentServices.getCommentById(id, accountId));
			return commentGetResponse;
		} catch (HibernateException e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}
	}

	@RequestMapping(value = "/getByFeed", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object getByFeedId(@RequestParam(value = "accessToken", required = true) String accessToken, @RequestParam(value = "feedId", required = true) Integer feedId) {
		BaseResponse response;
		Integer accountId;
		try {
			accountId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		try {
			CommentsResponse commentsGetResponse = new CommentsResponse();
			commentsGetResponse.setResult(BaseResponse.SUCCESS);
			commentsGetResponse.setComments(commentServices.getCommentsByFeed(feedId, accountId));
			return commentsGetResponse;
		} catch (HibernateException e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}
	}

	@RequestMapping(value = "/commend", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object commend(@RequestParam(value = "accessToken", required = true) String accessToken, @RequestParam(value = "text", required = true) String text,
			@RequestParam(value = "feedId", required = true) Integer feedId) {
		BaseResponse response;
		Integer accountId;
		try {
			accountId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}

		try {
			AccountDto accountDto = new AccountDto(accountId);
			CommentDto commentDto = new CommentDto();
			commentDto.setAccount(accountDto);
			commentDto.setCreateTime((int) (System.currentTimeMillis() / 1000));
			commentDto.setText(text);
			commentDto.setLikeCount(0);

			Integer commentId = commentServices.save(commentDto, feedId);
			CommendResponse commendResponse = new CommendResponse();
			commendResponse.setCommentId(commentId);
			commendResponse.setResult(BaseResponse.SUCCESS);
			return commendResponse;
		} catch (HibernateException e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
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
			commentServices.like(id, accountId);
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
			commentServices.unlike(id, accountId);
			response = new BaseResponse(BaseResponse.SUCCESS, null);
			return response;
		} catch (Exception e) {
			response = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return response;
		}
	}
}