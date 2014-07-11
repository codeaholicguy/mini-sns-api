package com.n9.mini.sns.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.n9.mini.sns.dao.po.Feed;

/**
 * @author HoangNN6
 * 
 */
@Repository
public class FeedDaoImpl extends BaseDao implements FeedDao {

	private final String FIND_NEW_FEED = "from Feed where account.id in (:account_id) order by createTime desc";
	private final String ID_PARAMETER = "id";
	private final String ACCOUNT_ID_PARAMETER = "account_id";

	@Override
	public Integer save(Feed feed) throws HibernateException {
		return (Integer) getSession().save(feed);
	}

	@Override
	public void update(Feed feed) throws HibernateException {
		getSession().update(feed);
		getSession().flush();
	}

	@Override
	public void delete(Feed feed) throws HibernateException {
		getSession().delete(feed);
		getSession().flush();
	}

	@Override
	public Feed findFeedById(Integer id) throws HibernateException {
		Criteria cr = getSession().createCriteria(Feed.class);
		cr.add(Restrictions.eq(ID_PARAMETER, id));
		return CollectionUtils.isEmpty(cr.list()) ? null : (Feed) cr.list().get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Feed> findAllFeeds() throws HibernateException {
		Criteria cr = getSession().createCriteria(Feed.class);
		return cr.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Feed> findFeedsByAccount(Integer accountId) throws HibernateException {
		Criteria cr = getSession().createCriteria(Feed.class);
		cr.add(Restrictions.eq(ACCOUNT_ID_PARAMETER, accountId));
		return cr.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Feed> findNewFeeds(Integer quantity, List<Integer> followeds) throws HibernateException {
		Query query = getSession().createQuery(FIND_NEW_FEED);
		query.setParameterList(ACCOUNT_ID_PARAMETER, followeds);
		query.setMaxResults(quantity);
		return query.list();
	}
}
