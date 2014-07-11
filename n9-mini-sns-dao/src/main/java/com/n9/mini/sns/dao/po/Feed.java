package com.n9.mini.sns.dao.po;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Feed entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "feed", catalog = "tsb_dpet")
public class Feed implements java.io.Serializable {

	// Fields

	private Integer id;
	private Image image;
	private Account account;
	private String status;
	private Integer createTime;
	private String url;
	private Integer likeCount;
	private String liker;
	private Set<Comment> comments = new HashSet<Comment>(0);

	// Constructors

	/** default constructor */
	public Feed() {
	}

	/** minimal constructor */
	public Feed(Integer createTime) {
		this.createTime = createTime;
	}

	/** full constructor */
	public Feed(Image image, Account account, String status, Integer createTime, String url, Integer likeCount, String liker, Set<Comment> comments) {
		this.image = image;
		this.account = account;
		this.status = status;
		this.createTime = createTime;
		this.url = url;
		this.likeCount = likeCount;
		this.liker = liker;
		this.comments = comments;
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
	@JoinColumn(name = "image_id")
	public Image getImage() {
		return this.image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "account_id")
	public Account getAccount() {
		return this.account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Column(name = "status", columnDefinition = "text")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "create_time", nullable = false)
	public Integer getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Integer createTime) {
		this.createTime = createTime;
	}

	@Column(name = "url", columnDefinition = "text")
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "feed")
	public Set<Comment> getComments() {
		return this.comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

}