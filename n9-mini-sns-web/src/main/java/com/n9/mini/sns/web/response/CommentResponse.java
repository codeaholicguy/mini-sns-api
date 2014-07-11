/**
 * 
 */
package com.n9.mini.sns.web.response;

import com.n9.mini.sns.domain.dto.CommentDto;

/**
 * @author HoangNN6
 * 
 */
public class CommentResponse extends BaseResponse {

	private CommentDto comment;

	public CommentDto getComment() {
		return comment;
	}

	public void setComment(CommentDto comment) {
		this.comment = comment;
	}

}
