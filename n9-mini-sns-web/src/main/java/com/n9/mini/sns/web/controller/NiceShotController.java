/**
 * 
 */
package com.n9.mini.sns.web.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.n9.mini.sns.domain.commons.utils.DateUtils;
import com.n9.mini.sns.domain.commons.utils.FilenameUtils;
import com.n9.mini.sns.domain.commons.utils.StringUtils;
import com.n9.mini.sns.web.config.SessionConfiguration;
import com.n9.mini.sns.web.response.AlbumResponse;
import com.n9.mini.sns.web.response.BaseResponse;

/**
 * @author HoangNN6
 * 
 */
@Controller
@RequestMapping("/niceshot")
public class NiceShotController {

	@Value("${base.directory}")
	private String baseDirectory;

	@Value("${niceshot.recognize.path}")
	private String niceshotRecognizePath;

	@Value("${niceshot.train.path}")
	private String niceshotTrainPath;

	@Value("${niceshot.root}")
	private String niceshotRoot;

	@Value("${niceshot.image.path}")
	private String niceshotImagePath;

	@RequestMapping(value = "/recognize", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object recognize(@RequestParam(value = "accessToken", required = true) String accessToken, @RequestParam(value = "file", required = true) String filename) {

		BaseResponse baseResponse;
		Integer accountId;
		try {
			accountId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			baseResponse = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return baseResponse;
		}

		StringBuffer resultPath = new StringBuffer();
		try {
			resultPath.append(baseDirectory).append(FilenameUtils.IMAGE_TYPE).append(StringUtils.SLASH).append(DateUtils.today(DateUtils.YYYY_MM_DD)).append(StringUtils.SLASH).append(accountId)
					.append(StringUtils.SLASH).append(filename);

			String filePath = FilenameUtils.getFilePath(baseDirectory, filename);
			ProcessBuilder processBuilder = new ProcessBuilder(niceshotRecognizePath, filePath);
			processBuilder.directory(new File(niceshotRoot));
			Process pr = processBuilder.start();
			pr.waitFor();

			File source = new File(niceshotImagePath);
			File dest = new File(resultPath.toString());
			FileUtils.copyDirectory(source, dest);
			FileUtils.cleanDirectory(source);
		} catch (IOException e) {
			baseResponse = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return baseResponse;
		} catch (InterruptedException e) {
			baseResponse = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return baseResponse;
		}

		AlbumResponse albumResponse = new AlbumResponse();
		albumResponse.setAlbum(filename);
		albumResponse.setFiles(FilenameUtils.getFileName(resultPath.toString()));

		return albumResponse;
	}

	@RequestMapping(value = "/example", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object recognizeFake(@RequestParam(value = "accessToken", required = true) String accessToken, @RequestParam(value = "file", required = true) String filename) {

		BaseResponse baseResponse;
		try {
			SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			baseResponse = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return baseResponse;
		}

		List<String> files = new ArrayList<String>();
		files.add("http://10.88.16.142/tmp/image/2014/07/03/1/example/dog_0.jpg");
		files.add("http://10.88.16.142/tmp/image/2014/07/03/1/example/dog_1.jpg");
		files.add("http://10.88.16.142/tmp/image/2014/07/03/1/example/dog_2.jpg");
		files.add("http://10.88.16.142/tmp/image/2014/07/03/1/example/dog_3.jpg");
		files.add("http://10.88.16.142/tmp/image/2014/07/03/1/example/dog_4.jpg");
		files.add("http://10.88.16.142/tmp/image/2014/07/03/1/example/dog_5.jpg");
		files.add("http://10.88.16.142/tmp/image/2014/07/03/1/example/dog_6.jpg");

		AlbumResponse albumResponse = new AlbumResponse();
		albumResponse.setAlbum(filename);
		albumResponse.setFiles(files);

		return albumResponse;
	}

	@RequestMapping(value = "/train", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object train(@RequestParam(value = "accessToken", required = true) String accessToken, @RequestParam(value = "file", required = true) String filename) {

		BaseResponse baseResponse;
		try {
			SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			baseResponse = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return baseResponse;
		}

		try {
			String filePath = FilenameUtils.getFilePath(baseDirectory, filename);
			ProcessBuilder processBuilder = new ProcessBuilder(niceshotTrainPath, filePath);
			processBuilder.directory(new File(niceshotRoot));
			Process pr = processBuilder.start();
			pr.waitFor();

		} catch (IOException e) {
			baseResponse = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return baseResponse;
		} catch (InterruptedException e) {
			baseResponse = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return baseResponse;
		}

		baseResponse = new BaseResponse(BaseResponse.SUCCESS, null);
		return baseResponse;
	}
}