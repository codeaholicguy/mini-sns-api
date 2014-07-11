/**
 * 
 */
package com.n9.mini.sns.web.response;

import java.util.List;

import com.n9.mini.sns.domain.dto.FeedDto;

/**
 * @author HoangNN6
 * 
 */
public class NewFeedResponse extends BaseResponse {

	public List<FeedDto> getFeeds() {
		return feeds;
	}

	public void setFeeds(List<FeedDto> feeds) {
		this.feeds = feeds;
	}

	List<FeedDto> feeds;

}
