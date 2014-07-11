/**
 * 
 */
package com.n9.mini.sns.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.n9.mini.sns.dao.po.Feed;
import com.n9.mini.sns.domain.dto.FeedDto;

/**
 * @author HoangNN6
 * 
 */
public class FeedMapper {

	ImageMapper imageMapper = new ImageMapper();

	AccountMapper accountMapper = new AccountMapper();

	CommentMapper commentMapper = new CommentMapper();

	/**
	 * @param feedDto
	 * @return
	 * @throws Exception
	 */
	public Feed toPO(FeedDto feedDto) throws Exception {
		Preconditions.checkNotNull(feedDto, "feed info is null");
		Feed feed = new Feed();
		feed.setId(feedDto.getId());
		feed.setImage(imageMapper.toPO(feedDto.getImage()));
		feed.setStatus(feedDto.getStatus());
		feed.setUrl(feedDto.getUrl());
		feed.setCreateTime(feedDto.getCreateTime());
		feed.setAccount(accountMapper.toPO(feedDto.getAccount()));
		feed.setLikeCount(feedDto.getLikeCount());

		return feed;
	}

	/**
	 * @param feed
	 * @return
	 * @throws Exception
	 */
	public FeedDto toDTO(Feed feed) throws Exception {
		Preconditions.checkNotNull(feed, "feed is null");
		FeedDto feedDto = new FeedDto();
		feedDto.setId(feed.getId());
		feedDto.setAccount(accountMapper.toDTO(feed.getAccount()));
		feedDto.setImage(imageMapper.toDTO(feed.getImage()));
		feedDto.setStatus(feed.getStatus());
		feedDto.setUrl(feed.getUrl());
		feedDto.setCreateTime(feed.getCreateTime());
		feedDto.setComments(commentMapper.toDTO(Lists.newArrayList(feed.getComments())));
		feedDto.setLikeCount(feed.getLikeCount());

		return feedDto;
	}

	/**
	 * @param feedDtos
	 * @return
	 * @throws Exception
	 */
	public List<Feed> toPO(List<FeedDto> feedDtos) throws Exception {
		List<Feed> feeds = new ArrayList<Feed>();
		for (FeedDto feedDto : feedDtos) {
			feeds.add(toPO(feedDto));
		}

		return feeds;
	}

	/**
	 * @param feeds
	 * @return
	 * @throws Exception
	 */
	public List<FeedDto> toDTO(List<Feed> feeds) throws Exception {
		List<FeedDto> feedDtos = new ArrayList<FeedDto>();
		for (Feed feed : feeds) {
			feedDtos.add(toDTO(feed));
		}

		return feedDtos;
	}
}
