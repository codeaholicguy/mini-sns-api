package com.n9.mini.sns.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.n9.mini.sns.dao.po.Tag;

/**
 * @author HoangNN6
 * 
 */
@Repository
public class TagDaoImpl extends BaseDao implements TagDao {

	private final String ID_PARAMETER = "id";
	private final String IMAGE_ID_PARAMETER = "image_id";

	@Override
	public Integer save(Tag tag) throws HibernateException {
		return (Integer) getSession().save(tag);
	}

	@Override
	public Tag findTagById(Integer id) throws HibernateException {
		Criteria cr = getSession().createCriteria(Tag.class);
		cr.add(Restrictions.eq(ID_PARAMETER, id));
		return CollectionUtils.isEmpty(cr.list()) ? null : (Tag) cr.list().get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> findAllTags() throws HibernateException {
		Criteria cr = getSession().createCriteria(Tag.class);
		return cr.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tag> findTagsByImage(Integer imageId) throws HibernateException {
		Criteria cr = getSession().createCriteria(Tag.class);
		cr.add(Restrictions.eq(IMAGE_ID_PARAMETER, imageId));
		return cr.list();
	}
}
