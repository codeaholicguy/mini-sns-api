/**
 * 
 */
package com.n9.mini.sns.web.response;

import java.util.List;

import com.n9.mini.sns.domain.dto.CommentDto;

/**
 * @author HoangNN6
 * 
 */
public class CommentsResponse extends BaseResponse {

	private List<CommentDto> comments;

	public List<CommentDto> getComments() {
		return comments;
	}

	public void setComments(List<CommentDto> comments) {
		this.comments = comments;
	}

}
