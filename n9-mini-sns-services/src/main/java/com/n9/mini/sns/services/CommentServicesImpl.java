/**
 * 
 */
package com.n9.mini.sns.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.n9.mini.sns.dao.AccountDao;
import com.n9.mini.sns.dao.CommentDao;
import com.n9.mini.sns.dao.FeedDao;
import com.n9.mini.sns.dao.mapper.CommentMapper;
import com.n9.mini.sns.dao.po.Comment;
import com.n9.mini.sns.domain.commons.utils.StringUtils;
import com.n9.mini.sns.domain.dto.AccountDto;
import com.n9.mini.sns.domain.dto.CommentDto;

/**
 * @author HoangNN6
 * 
 */
public class CommentServicesImpl implements CommentServices {

	private static final Log LOGGER = LogFactory.getLog(CommentServices.class);

	@Autowired
	CommentDao commentDao;

	@Autowired
	FeedDao feedDao;

	@Autowired
	AccountDao accountDao;

	@Autowired
	AccountServices accountServices;

	CommentMapper commentMapper = new CommentMapper();

	@Transactional(rollbackFor = Exception.class)
	public Integer save(CommentDto commentDto, Integer feedId) throws Exception {
		Comment comment = commentMapper.toPO(commentDto);
		comment.setFeed(feedDao.findFeedById(feedId));
		comment.setAccount(accountDao.findAccountById(commentDto.getAccount().getId()));
		return commentDao.save(comment);
	}

	@Transactional(readOnly = true)
	public List<Integer> getLikerIds(Integer commentId) throws HibernateException, Exception {
		Comment comment = commentDao.findCommentById(commentId);
		List<Integer> likerIds = new ArrayList<Integer>();
		if (comment.getLikeCount() != null && comment.getLikeCount() > 0) {
			if (comment.getLiker() != null) {
				String[] stringLikerIds = comment.getLiker().split(StringUtils.DASH);
				if (stringLikerIds != null && stringLikerIds.length > 0) {
					for (String stringLikerId : stringLikerIds) {
						try {
							likerIds.add(Integer.parseInt(stringLikerId));
						} catch (Exception e) {
							LOGGER.error(e.getMessage());
						}
					}
				}
			}
		}
		return likerIds;
	}

	@Override
	public Boolean isLiked(Integer commentId, Integer accountId) throws HibernateException, Exception {
		if (getLikerIds(commentId).contains(accountId)) {
			return true;
		}
		return false;
	}

	@Override
	public List<AccountDto> getLikers(Integer commentId) throws HibernateException, Exception {
		List<Integer> likerIds = getLikerIds(commentId);
		List<AccountDto> liker = new ArrayList<AccountDto>();
		for (Integer likerId : likerIds) {
			liker.add(accountServices.getAccountById(likerId));
		}
		return liker;
	}

	@Transactional(readOnly = true)
	public CommentDto getCommentById(Integer id, Integer accountId) throws HibernateException, Exception {
		CommentDto commentDto = commentMapper.toDTO(commentDao.findCommentById(id));
		if (isLiked(id, accountId)) {
			commentDto.setLiked(true);
		} else {
			commentDto.setLiked(false);
		}
		return commentDto;
	}

	@Transactional(readOnly = true)
	public List<CommentDto> getCommentsByFeed(Integer feedId, Integer accountId) throws HibernateException, Exception {
		List<CommentDto> commentDtos = commentMapper.toDTO(commentDao.findCommentsByFeed(feedId));
		for (CommentDto commentDto : commentDtos) {
			if (commentDto.getLikeCount() != null && commentDto.getLikeCount() > 0) {
				if (isLiked(commentDto.getId(), accountId)) {
					commentDto.setLiked(true);
				} else {
					commentDto.setLiked(false);
				}
			} else {
				commentDto.setLiked(false);
			}
		}
		return commentDtos;
	}

	@Transactional(rollbackFor = Exception.class)
	public void like(Integer id, Integer accountId) throws HibernateException, Exception {
		if (isLiked(id, accountId)) {
			throw new Exception("account was liked this comment");
		}
		Comment comment = commentDao.findCommentById(id);
		comment.setLikeCount(comment.getLikeCount() + 1);
		List<Integer> likerIds = getLikerIds(id);
		likerIds.add(accountId);
		comment.setLiker(toStringLikerIds(likerIds));
		commentDao.update(comment);
	}

	@Transactional(rollbackFor = Exception.class)
	public void unlike(Integer id, Integer accountId) throws HibernateException, Exception {
		if (!isLiked(id, accountId)) {
			throw new Exception("account was not liked this comment before");
		}
		Comment comment = commentDao.findCommentById(id);
		comment.setLikeCount(comment.getLikeCount() - 1);
		List<Integer> likerIds = getLikerIds(id);
		likerIds.remove(accountId);
		comment.setLiker(toStringLikerIds(likerIds));
		commentDao.update(comment);
	}

	public String toStringLikerIds(List<Integer> likerIds) {
		StringBuffer stringLikerIds = new StringBuffer();
		for (Integer likerId : likerIds) {
			stringLikerIds.append(likerId).append(StringUtils.DASH);
		}
		if (stringLikerIds.length() > 0) {
			stringLikerIds.deleteCharAt(stringLikerIds.length() - 1);
		}
		return stringLikerIds.toString();
	}

}
