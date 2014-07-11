/**
 * 
 */
package com.n9.mini.sns.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.n9.mini.sns.dao.po.Comment;
import com.n9.mini.sns.domain.dto.CommentDto;

/**
 * @author HoangNN6
 * 
 */
public class CommentMapper {

	AccountMapper accountMapper = new AccountMapper();

	/**
	 * @param commentDto
	 * @return
	 */
	public Comment toPO(CommentDto commentDto) throws Exception {
		Preconditions.checkNotNull(commentDto, "comment info is null");
		Comment comment = new Comment();
		comment.setId(commentDto.getId());
		comment.setAccount(accountMapper.toPO(commentDto.getAccount()));
		comment.setCreateTime(commentDto.getCreateTime());
		comment.setText(commentDto.getText());
		comment.setLikeCount(commentDto.getLikeCount());

		return comment;
	}

	/**
	 * @param comment
	 * @return
	 */
	public CommentDto toDTO(Comment comment) throws Exception {
		Preconditions.checkNotNull(comment, "comment is null");
		CommentDto commentDto = new CommentDto();
		commentDto.setId(comment.getId());
		commentDto.setAccount(accountMapper.toDTO(comment.getAccount()));
		commentDto.setCreateTime(comment.getCreateTime());
		commentDto.setText(comment.getText());
		commentDto.setLikeCount(comment.getLikeCount());

		return commentDto;
	}

	/**
	 * @param commentDtos
	 * @return
	 */
	public List<Comment> toPO(List<CommentDto> commentDtos) throws Exception {
		List<Comment> comments = new ArrayList<Comment>();
		for (CommentDto commentDto : commentDtos) {
			comments.add(toPO(commentDto));
		}

		return comments;
	}

	/**
	 * @param comments
	 * @return
	 */
	public List<CommentDto> toDTO(List<Comment> comments) throws Exception {
		List<CommentDto> commentDtos = new ArrayList<CommentDto>();
		for (Comment comment : comments) {
			commentDtos.add(toDTO(comment));
		}

		return commentDtos;
	}
}
