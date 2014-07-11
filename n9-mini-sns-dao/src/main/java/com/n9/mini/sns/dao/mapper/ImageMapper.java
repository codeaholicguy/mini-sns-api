/**
 * 
 */
package com.n9.mini.sns.dao.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.n9.mini.sns.dao.po.Image;
import com.n9.mini.sns.dao.po.Tag;
import com.n9.mini.sns.domain.dto.ImageDto;

/**
 * @author HoangNN6
 * 
 */
public class ImageMapper {

	TagMapper tagMapper = new TagMapper();

	/**
	 * @param imageDto
	 * @return
	 */
	public Image toPO(ImageDto imageDto) throws Exception {
		Preconditions.checkNotNull(imageDto, "image info is null");
		Image image = new Image();
		image.setId(imageDto.getId());
		image.setUrl(imageDto.getUrl());
		image.setCreateTime(imageDto.getCreateTime());
		image.setTags(CollectionUtils.isEmpty(imageDto.getTags()) ? null : new HashSet<Tag>(tagMapper.toPO(imageDto.getTags())));

		return image;
	}

	/**
	 * @param image
	 * @return
	 */
	public ImageDto toDTO(Image image) throws Exception {
		Preconditions.checkNotNull(image, "image is null");
		ImageDto imageDto = new ImageDto();
		imageDto.setId(image.getId());
		imageDto.setUrl(image.getUrl());
		imageDto.setCreateTime(image.getCreateTime());
		imageDto.setTags(tagMapper.toDTO(Lists.newArrayList(image.getTags())));

		return imageDto;
	}

	/**
	 * @param tagDtos
	 * @return
	 */
	public List<Image> toPO(List<ImageDto> imageDtos) throws Exception {
		List<Image> images = new ArrayList<Image>();
		for (ImageDto imageDto : imageDtos) {
			images.add(toPO(imageDto));
		}

		return images;
	}

	/**
	 * @param images
	 * @return
	 */
	public List<ImageDto> toDTO(List<Image> images) throws Exception {
		List<ImageDto> imageDtos = new ArrayList<ImageDto>();
		for (Image image : images) {
			imageDtos.add(toDTO(image));
		}

		return imageDtos;
	}
}
