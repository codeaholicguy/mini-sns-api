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

import com.n9.mini.sns.dao.FeedDao;
import com.n9.mini.sns.dao.mapper.FeedMapper;
import com.n9.mini.sns.dao.po.Feed;
import com.n9.mini.sns.domain.commons.utils.StringUtils;
import com.n9.mini.sns.domain.dto.AccountDto;
import com.n9.mini.sns.domain.dto.CommentDto;
import com.n9.mini.sns.domain.dto.FeedDto;

/**
 * @author HoangNN6
 * 
 */
public class FeedServicesImpl implements FeedServices {

	private static final Log LOGGER = LogFactory.getLog(FeedServices.class);

	@Autowired
	FeedDao feedDao;

	@Autowired
	AccountServices accountServices;

	@Autowired
	ImageServices imageServices;

	FeedMapper feedMapper = new FeedMapper();

	@Transactional(rollbackFor = Exception.class)
	public Integer save(FeedDto feedDto) throws HibernateException, Exception {
		return feedDao.save(feedMapper.toPO(feedDto));
	}

	@Transactional(readOnly = true)
	public FeedDto getFeedById(Integer id, Integer accountId) throws HibernateException, Exception {
		FeedDto feedDto = feedMapper.toDTO(feedDao.findFeedById(id));
		if (isLiked(id, accountId)) {
			feedDto.setLiked(true);
		} else {
			feedDto.setLiked(false);
		}
		return feedDto;
	}

	@Transactional(readOnly = true)
	public List<Integer> getLikerIds(Integer feedId) throws HibernateException, Exception {
		Feed feed = feedDao.findFeedById(feedId);
		List<Integer> likerIds = new ArrayList<Integer>();
		if (feed.getLikeCount() != null && feed.getLikeCount() > 0) {
			if (feed.getLiker() != null) {
				String[] stringLikerIds = feed.getLiker().split(StringUtils.DASH);
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
	public Boolean isLiked(Integer feedId, Integer accountId) throws HibernateException, Exception {
		if (getLikerIds(feedId).contains(accountId)) {
			return true;
		}
		return false;
	}

	@Override
	public List<AccountDto> getLikers(Integer feedId) throws HibernateException, Exception {
		List<Integer> likerIds = getLikerIds(feedId);
		List<AccountDto> liker = new ArrayList<AccountDto>();
		for (Integer likerId : likerIds) {
			liker.add(accountServices.getAccountById(likerId));
		}
		return liker;
	}

	@Transactional(readOnly = true)
	public List<FeedDto> getAllFeeds() throws HibernateException, Exception {
		return feedMapper.toDTO(feedDao.findAllFeeds());
	}

	@Transactional(readOnly = true)
	public List<FeedDto> getFeedsByAccount(Integer accountId) throws HibernateException, Exception {
		return feedMapper.toDTO(feedDao.findFeedsByAccount(accountId));
	}

	@Transactional(readOnly = true)
	public List<FeedDto> getNewFeeds(Integer quantity, Integer accountId) throws HibernateException, Exception {
		List<FeedDto> feedDtos = feedMapper.toDTO(feedDao.findNewFeeds(quantity, accountServices.getFollowedIds(accountId)));
		List<Integer> followedIds = accountServices.getFollowedIds(accountId);
		for (FeedDto feedDto : feedDtos) {
			if (followedIds.contains(feedDto.getAccount().getId())) {
				feedDto.getAccount().setFollowed(true);
			} else {
				feedDto.getAccount().setFollowed(false);
			}
			if (feedDto.getLikeCount() != null && feedDto.getLikeCount() > 0) {
				if (getLikerIds(feedDto.getId()).contains(accountId)) {
					feedDto.setLiked(true);
				} else {
					feedDto.setLiked(false);
				}
			} else {
				feedDto.setLiked(false);
			}
			List<CommentDto> commentDtos = feedDto.getComments();
			for (CommentDto commentDto : commentDtos) {
				if (followedIds.contains(commentDto.getAccount().getId())) {
					commentDto.getAccount().setFollowed(true);
				} else {
					commentDto.getAccount().setFollowed(false);
				}
				if (commentDto.getLikeCount() != null && commentDto.getLikeCount() > 0) {
					if (getLikerIds(commentDto.getId()).contains(accountId)) {
						commentDto.setLiked(true);
					} else {
						commentDto.setLiked(false);
					}
				} else {
					commentDto.setLiked(false);
				}
			}
		}
		return feedDtos;
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer save(Integer imageId, Integer accountId, String status, String url) throws HibernateException, Exception {
		FeedDto feedDto = new FeedDto();
		feedDto.setCreateTime((int) (System.currentTimeMillis() / 1000));
		feedDto.setStatus(status);
		feedDto.setUrl(url);
		feedDto.setImage(imageServices.getImageById(imageId));
		feedDto.setAccount(accountServices.getAccountById(accountId));

		return save(feedDto);
	}

	@Transactional(rollbackFor = Exception.class)
	public void like(Integer id, Integer accountId) throws HibernateException, Exception {
		if (isLiked(id, accountId)) {
			throw new Exception("account was liked this feed");
		}
		Feed feed = feedDao.findFeedById(id);
		feed.setLikeCount(feed.getLikeCount() != null ? feed.getLikeCount() + 1 : 1);
		List<Integer> likerIds = getLikerIds(id);
		likerIds.add(accountId);
		feed.setLiker(toStringLikerIds(likerIds));
		feedDao.update(feed);
	}

	@Transactional(rollbackFor = Exception.class)
	public void unlike(Integer id, Integer accountId) throws HibernateException, Exception {
		if (!isLiked(id, accountId)) {
			throw new Exception("account was not liked this feed before");
		}
		Feed feed = feedDao.findFeedById(id);
		feed.setLikeCount(feed.getLikeCount() - 1);
		List<Integer> likerIds = getLikerIds(id);
		likerIds.remove(accountId);
		feed.setLiker(toStringLikerIds(likerIds));
		feedDao.update(feed);
	}

	@Transactional(rollbackFor = Exception.class)
	public void remove(Integer id, Integer accountId) throws HibernateException, Exception {
		Feed feed = feedDao.findFeedById(id);
		if (!(feed.getAccount().getId() == accountId)) {
			throw new Exception("you dont have permission to remove this feed");
		}
		feedDao.delete(feed);

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
