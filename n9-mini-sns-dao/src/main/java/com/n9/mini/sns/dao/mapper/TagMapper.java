/**
 * 
 */
package com.n9.mini.sns.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Preconditions;
import com.n9.mini.sns.dao.po.Tag;
import com.n9.mini.sns.domain.dto.TagDto;

/**
 * @author HoangNN6
 * 
 */
public class TagMapper {

	/**
	 * @param tagDto
	 * @return
	 */
	public Tag toPO(TagDto tagDto) throws Exception {
		Preconditions.checkNotNull(tagDto, "tag info is null");
		Tag tag = new Tag();
		tag.setId(tagDto.getId());
		tag.setName(tagDto.getName());

		return tag;
	}

	/**
	 * @param tag
	 * @return
	 */
	public TagDto toDTO(Tag tag) throws Exception {
		Preconditions.checkNotNull(tag, "tag is null");
		TagDto tagDto = new TagDto();
		tagDto.setId(tag.getId());
		tagDto.setName(tag.getName());

		return tagDto;
	}

	/**
	 * @param tagDtos
	 * @return
	 */
	public List<Tag> toPO(List<TagDto> tagDtos) throws Exception {
		List<Tag> tags = new ArrayList<Tag>();
		for (TagDto tagDto : tagDtos) {
			tags.add(toPO(tagDto));
		}

		return tags;
	}

	/**
	 * @param tags
	 * @return
	 */
	public List<TagDto> toDTO(List<Tag> tags) throws Exception {
		List<TagDto> tagDtos = new ArrayList<TagDto>();
		for (Tag tag : tags) {
			tagDtos.add(toDTO(tag));
		}

		return tagDtos;
	}
}
