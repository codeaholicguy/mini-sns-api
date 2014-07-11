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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Account entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "account")
public class Account implements java.io.Serializable {

	// Fields

	private Integer id;
	private String email;
	private String password;
	private String screenName;
	private String avatar;
	private Set<Image> images = new HashSet<Image>(0);
	private Set<Account> accountsForFollowedId = new HashSet<Account>(0);
	private Set<Account> accountsForFollowerId = new HashSet<Account>(0);
	private Set<Comment> comments = new HashSet<Comment>(0);
	private Set<Feed> feeds = new HashSet<Feed>(0);

	// Constructors

	/** default constructor */
	public Account() {
	}

	/** minimal constructor */
	public Account(String email, String password) {
		this.email = email;
		this.password = password;
	}

	/** full constructor */
	public Account(String email, String password, String screenName, String avatar, Set<Image> images, Set<Account> accountsForFollowedId, Set<Account> accountsForFollowerId, Set<Comment> comments,
			Set<Feed> feeds) {
		this.email = email;
		this.password = password;
		this.screenName = screenName;
		this.avatar = avatar;
		this.images = images;
		this.accountsForFollowedId = accountsForFollowedId;
		this.accountsForFollowerId = accountsForFollowerId;
		this.comments = comments;
		this.feeds = feeds;
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

	@Column(name = "email", nullable = false, length = 256)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "password", nullable = false, length = 64)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "screen_name", length = 128)
	public String getScreenName() {
		return this.screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	@Column(name = "avatar", length = 512)
	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
	public Set<Image> getImages() {
		return this.images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "accountsForFollowerId")
	public Set<Account> getAccountsForFollowedId() {
		return this.accountsForFollowedId;
	}

	public void setAccountsForFollowedId(Set<Account> accountsForFollowedId) {
		this.accountsForFollowedId = accountsForFollowedId;
	}

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "follow", joinColumns = { @JoinColumn(name = "followed_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "follower_id", nullable = false, updatable = false) })
	public Set<Account> getAccountsForFollowerId() {
		return this.accountsForFollowerId;
	}

	public void setAccountsForFollowerId(Set<Account> accountsForFollowerId) {
		this.accountsForFollowerId = accountsForFollowerId;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
	public Set<Comment> getComments() {
		return this.comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "account")
	public Set<Feed> getFeeds() {
		return this.feeds;
	}

	public void setFeeds(Set<Feed> feeds) {
		this.feeds = feeds;
	}

}