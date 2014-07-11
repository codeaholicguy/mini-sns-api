/**
 * 
 */
package com.n9.mini.sns.services;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.n9.mini.sns.dao.AccountDao;
import com.n9.mini.sns.dao.ImageDao;
import com.n9.mini.sns.dao.mapper.ImageMapper;
import com.n9.mini.sns.dao.po.Image;
import com.n9.mini.sns.domain.dto.ImageDto;

/**
 * @author HoangNN6
 * 
 */
public class ImageServicesImpl implements ImageServices {

	@Autowired
	ImageDao imageDao;

	@Autowired
	AccountDao accountDao;

	ImageMapper imageMapper = new ImageMapper();

	@Transactional(rollbackFor = Exception.class)
	public Integer save(ImageDto imageDto) throws HibernateException, Exception {
		return imageDao.save(imageMapper.toPO(imageDto));
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer save(ImageDto imageDto, Integer accountId) throws Exception {
		Image image = new Image();
		image = imageMapper.toPO(imageDto);
		image.setAccount(accountDao.findAccountById(accountId));
		return imageDao.save(image);
	}

	@Transactional(readOnly = true)
	public ImageDto getImageById(Integer id) throws HibernateException, Exception {
		return imageMapper.toDTO(imageDao.findImageById(id));
	}

	@Transactional(readOnly = true)
	public List<ImageDto> getImagesByEmail(String email) throws HibernateException, Exception {
		return imageMapper.toDTO(imageDao.findImagesByEmail(email));
	}

	@Transactional(readOnly = true)
	public List<ImageDto> getAllImages() throws HibernateException, Exception {
		return imageMapper.toDTO(imageDao.findAllImages());
	}

	@Transactional(readOnly = true)
	public List<ImageDto> getNewestImages(Integer accountId, Integer quantity) throws HibernateException, Exception {
		return imageMapper.toDTO(imageDao.findImagesByAccount(accountId, quantity));
	}

}
