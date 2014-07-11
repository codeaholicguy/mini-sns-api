package com.n9.mini.sns.dao.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Comment entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "comment", catalog = "tsb_dpet")
public class Comment implements java.io.Serializable {

	// Fields

	private Integer id;
	private Feed feed;
	private Account account;
	private String text;
	private Integer createTime;
	private Integer likeCount;
	private String liker;

	// Constructors

	/** default constructor */
	public Comment() {
	}

	/** minimal constructor */
	public Comment(Feed feed, Account account, String text, Integer createTime) {
		this.feed = feed;
		this.account = account;
		this.text = text;
		this.createTime = createTime;
	}

	/** full constructor */
	public Comment(Feed feed, Account account, String text, Integer createTime, Integer likeCount, String liker) {
		this.feed = feed;
		this.account = account;
		this.text = text;
		this.createTime = createTime;
		this.likeCount = likeCount;
		this.liker = liker;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "feed_id", nullable = false)
	public Feed getFeed() {
		return this.feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id", nullable = false)
	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Column(name = "text", nullable = false, columnDefinition = "text")
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name = "create_time", nullable = false)
	public Integer getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	@Column(name = "like_count")
	public Integer getLikeCount() {
		return this.likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	@Column(name = "liker", columnDefinition = "text")
	public String getLiker() {
		return this.liker;
	}

	public void setLiker(String liker) {
		this.liker = liker;
	}

}