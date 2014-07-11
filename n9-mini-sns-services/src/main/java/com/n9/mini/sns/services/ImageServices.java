/**
 * 
 */
package com.n9.mini.sns.services;

import java.util.List;

import org.hibernate.HibernateException;

import com.n9.mini.sns.domain.dto.ImageDto;

/**
 * @author HoangNN6
 * 
 */
public interface ImageServices {

	Integer save(ImageDto imageDto) throws HibernateException, Exception;

	Integer save(ImageDto imageDto, Integer accountId) throws HibernateException, Exception;

	ImageDto getImageById(Integer id) throws HibernateException, Exception;

	List<ImageDto> getAllImages() throws HibernateException, Exception;

	List<ImageDto> getImagesByEmail(String email) throws HibernateException, Exception;

	List<ImageDto> getNewestImages(Integer accountId, Integer quantity) throws HibernateException, Exception;

}
