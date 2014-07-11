package com.n9.mini.sns.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.n9.mini.sns.dao.po.Image;

/**
 * @author HoangNN6
 * 
 */
@Repository
public class ImageDaoImpl extends BaseDao implements ImageDao {

	private final String FIND_BY_ACCOUNT = "from Image where account.id = :id order by createTime desc";
	private final String ID_PARAMETER = "id";
	private final String EMAIL_PARAMETER = "email";

	@Override
	public Integer save(Image image) throws HibernateException {
		return (Integer) getSession().save(image);
	}

	@Override
	public Image findImageById(Integer id) throws HibernateException {
		Criteria cr = getSession().createCriteria(Image.class);
		cr.add(Restrictions.eq(ID_PARAMETER, id));
		return CollectionUtils.isEmpty(cr.list()) ? null : (Image) cr.list().get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Image> findAllImages() throws HibernateException {
		Criteria cr = getSession().createCriteria(Image.class);
		return cr.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Image> findImagesByEmail(String email) throws HibernateException {
		Criteria cr = getSession().createCriteria(Image.class);
		cr.add(Restrictions.eq(EMAIL_PARAMETER, email));
		return cr.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Image> findImagesByAccount(Integer accountId, Integer quantity) throws HibernateException {
		Query query = getSession().createQuery(FIND_BY_ACCOUNT);
		query.setInteger(ID_PARAMETER, accountId);
		query.setMaxResults(quantity);
		return query.list();
	}

}
