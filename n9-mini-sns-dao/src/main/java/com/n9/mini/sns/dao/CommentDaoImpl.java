package com.n9.mini.sns.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.n9.mini.sns.dao.po.Comment;

/**
 * @author HoangNN6
 * 
 */
@Repository
public class CommentDaoImpl extends BaseDao implements CommentDao {

	private final String ID_PARAMETER = "id";
	private final String FEED_ID_PARAMETER = "feed.id";

	@Override
	public Integer save(Comment comment) throws HibernateException {
		return (Integer) getSession().save(comment);
	}

	@Override
	public void update(Comment comment) throws HibernateException {
		getSession().update(comment);
		getSession().flush();
	}

	@Override
	public Comment findCommentById(Integer id) throws HibernateException {
		Criteria cr = getSession().createCriteria(Comment.class);
		cr.add(Restrictions.eq(ID_PARAMETER, id));
		return CollectionUtils.isEmpty(cr.list()) ? null : (Comment) cr.list().get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> findCommentsByFeed(Integer feedId) throws HibernateException {
		Criteria cr = getSession().createCriteria(Comment.class);
		cr.add(Restrictions.eq(FEED_ID_PARAMETER, feedId));
		return cr.list();
	}
}
