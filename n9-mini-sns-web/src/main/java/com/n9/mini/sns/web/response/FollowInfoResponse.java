/**
 * 
 */
package com.n9.mini.sns.web.response;

import com.n9.mini.sns.domain.dto.FollowDto;

/**
 * @author HoangNN6
 * 
 */
public class FollowInfoResponse extends BaseResponse {

	FollowDto info;

	public FollowDto getInfo() {
		return info;
	}

	public void setInfo(FollowDto info) {
		this.info = info;
	}

}
