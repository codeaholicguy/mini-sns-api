/**
 * 
 */
package com.n9.mini.sns.services;

import java.io.File;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.n9.mini.sns.domain.commons.utils.DateUtils;
import com.n9.mini.sns.domain.commons.utils.FilenameUtils;
import com.n9.mini.sns.domain.commons.utils.StringUtils;
import com.n9.mini.sns.domain.dto.ImageDto;

/**
 * @author HoangNN6
 * 
 */
public class FileProcessingServicesImpl implements FileProcessingServices {

	@Autowired
	ImageServices imageServices;

	@Override
	public String getFilePath(String downloadRootDirectory, String fileType, String extension, Integer accountId, String album) {
		StringBuffer filename = new StringBuffer();
		filename.append(downloadRootDirectory);
		if (fileType.startsWith(FilenameUtils.IMAGE_TYPE)) {
			filename.append(FilenameUtils.IMAGE_TYPE).append(StringUtils.SLASH);
		} else {
			filename.append(FilenameUtils.VIDEO_TYPE).append(StringUtils.SLASH);
		}
		filename.append(DateUtils.today(DateUtils.YYYY_MM_DD)).append(StringUtils.SLASH).append(accountId).append(StringUtils.SLASH);
		if (StringUtils.isNullorEmpty(album)) {
			filename.append(FilenameUtils.OTHER);
		} else {
			filename.append(album);
		}
		File file = new File(filename.toString());
		file.mkdirs();
		filename.append(StringUtils.SLASH).append(UUID.randomUUID().toString().replaceAll(StringUtils.HYPHEN, StringUtils.EMPTY)).append(extension);

		return filename.toString();
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer saveImagePath(String filename, Integer accountId) throws HibernateException, Exception {
		ImageDto imageDto = new ImageDto();
		imageDto.setUrl(filename);
		imageDto.setCreateTime((int) (System.currentTimeMillis() / 1000));
		return imageServices.save(imageDto, accountId);
	}
}
