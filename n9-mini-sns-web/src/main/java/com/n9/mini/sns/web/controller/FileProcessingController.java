/**
 * 
 */
package com.n9.mini.sns.web.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.n9.mini.sns.domain.commons.utils.FilenameUtils;
import com.n9.mini.sns.domain.commons.utils.StringUtils;
import com.n9.mini.sns.services.FileProcessingServices;
import com.n9.mini.sns.web.config.SessionConfiguration;
import com.n9.mini.sns.web.response.AlbumResponse;
import com.n9.mini.sns.web.response.AlbumsResponse;
import com.n9.mini.sns.web.response.BaseResponse;
import com.n9.mini.sns.web.response.UploadResponse;

/**
 * @author HoangNN6
 * 
 */
@Controller
@RequestMapping("/file")
public class FileProcessingController {

	private static final Log LOGGER = LogFactory.getLog(FileProcessingController.class);

	@Autowired
	ServletContext context;

	@Autowired
	FileProcessingServices fileProcessingServices;

	@Value("${base.directory}")
	private String baseDirectory;

	@Value("${base.url}")
	private String baseUrl;

	@RequestMapping(value = "/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object upload(@RequestParam(value = "accessToken", required = true) String accessToken, @RequestParam(value = "album", required = false) String album, HttpServletRequest request,
			HttpServletResponse response) throws IOException, IllegalArgumentException {
		UploadResponse uploadResponse;
		Integer accountId;
		List<Integer> imageIds = new ArrayList<Integer>();
		List<String> filenames = new ArrayList<String>();
		List<String> links = new ArrayList<String>();
		try {
			accountId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			uploadResponse = new UploadResponse();
			uploadResponse.setResult(BaseResponse.FAIL);
			uploadResponse.setError(e.getMessage());
			return uploadResponse;
		}

		try {
			boolean isMultipart = ServletFileUpload.isMultipartContent(request);
			if (isMultipart) {

				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);

				Iterator<FileItem> itemIterator = upload.parseRequest(request).iterator();

				FileItem item = null;
				while (itemIterator.hasNext()) {
					item = itemIterator.next();

					if (!item.isFormField()) {
						OutputStream outputStream = null;
						InputStream inputStream = null;
						try {
							String fileType = context.getMimeType(item.getName());
							String extension = item.getName().substring(item.getName().lastIndexOf(StringUtils.DOT));
							StringBuffer filename = new StringBuffer();
							filename.append(fileProcessingServices.getFilePath(baseDirectory, fileType, extension, accountId, album));
							outputStream = new FileOutputStream(new File(filename.toString()));
							inputStream = item.getInputStream();

							IOUtils.copy(inputStream, outputStream);

							StringBuffer link = (new StringBuffer(baseUrl)).append(filename);
							if (fileType.startsWith("image")) {
								imageIds.add(fileProcessingServices.saveImagePath(link.toString(), accountId));
								LOGGER.info("File: " + filename + " save image path successful");
							} else {
								LOGGER.info("File: " + filename + " upload successful");
							}
							links.add(link.toString());
							filenames.add(FilenameUtils.getFileNameFromPath(filename.toString()));
						} catch (Exception e) {
							LOGGER.info("File upload fail" + e.getMessage());
							uploadResponse = new UploadResponse();
							uploadResponse.setResult(BaseResponse.FAIL);
							uploadResponse.setError(e.getMessage());
							return uploadResponse;
						} finally {
							inputStream.close();
							outputStream.flush();
							outputStream.close();
						}
					}
				}
			}
			uploadResponse = new UploadResponse();
			uploadResponse.setResult(BaseResponse.SUCCESS);
			uploadResponse.setImageIds(imageIds);
			uploadResponse.setFileNames(filenames);
			uploadResponse.setLinks(links);
			uploadResponse.setAlbum(album == null ? FilenameUtils.OTHER : album);
			return uploadResponse;
		} catch (FileUploadException e) {
			LOGGER.info("File upload fail" + e.getMessage());
			uploadResponse = new UploadResponse();
			uploadResponse.setResult(BaseResponse.FAIL);
			uploadResponse.setError(e.getMessage());
			return uploadResponse;
		}
	}

	@RequestMapping(value = "/download/{filename}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object download(@RequestParam(value = "accessToken", required = true) String accessToken, @RequestParam(value = "type", required = true) String type,
			@RequestParam(value = "date", required = true) String date, @RequestParam(value = "album", required = true) String album, @PathVariable("filename") String filename,
			HttpServletResponse response) {
		BaseResponse baseResponse;
		Integer accountId;
		try {
			accountId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			baseResponse = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return baseResponse;
		}

		try {
			String path = (new StringBuffer()).append(baseDirectory).append(type).append(StringUtils.SLASH).append(date).append(StringUtils.SLASH).append(accountId).append(StringUtils.SLASH)
					.append(album).toString();
			String filePath = FilenameUtils.getFilePath(path, filename);
			if (!StringUtils.isNullorEmpty(filePath)) {
				File file = new File(filePath);
				int fileSize = (int) file.length();

				if (fileSize > 0) {

					BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

					response.setBufferSize(fileSize);
					response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
					response.setContentLength(fileSize);

					FileCopyUtils.copy(in, response.getOutputStream());
					in.close();
					response.getOutputStream().flush();
					response.getOutputStream().close();

					return null;
				} else {
					baseResponse = new BaseResponse(BaseResponse.FAIL, "Could not get file name: " + filename);
					return baseResponse;
				}
			} else {
				baseResponse = new BaseResponse(BaseResponse.FAIL, "Could not get file name: " + filename);
				return baseResponse;
			}
		} catch (IOException e) {
			baseResponse = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return baseResponse;
		} catch (Exception e) {
			baseResponse = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return baseResponse;
		}
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	Object listFile(@RequestParam(value = "accessToken", required = true) String accessToken, @RequestParam(value = "date", required = true) String date,
			@RequestParam(value = "type", required = true) String type) {

		BaseResponse baseResponse;
		Integer accountId;
		try {
			accountId = SessionConfiguration.checkAuthority(accessToken);
		} catch (Exception e) {
			baseResponse = new BaseResponse(BaseResponse.FAIL, e.getMessage());
			return baseResponse;
		}

		List<AlbumResponse> albumResponses = new ArrayList<AlbumResponse>();
		String path = (new StringBuffer()).append(baseDirectory).append(type).append(StringUtils.SLASH).append(date).append(StringUtils.SLASH).append(accountId).toString();
		List<String> albums = FilenameUtils.getAllSubDirectories(path);
		for (String album : albums) {
			String albumPath = (new StringBuffer(path)).append(StringUtils.SLASH).append(album).toString();
			AlbumResponse albumResponse = new AlbumResponse();
			albumResponse.setAlbum(album);
			albumResponse.setFiles(FilenameUtils.getFileName(albumPath));
			albumResponses.add(albumResponse);
		}
		AlbumsResponse albumsResponse = new AlbumsResponse();
		albumsResponse.setAlbums(albumResponses);
		albumsResponse.setResult(BaseResponse.SUCCESS);

		return albumsResponse;
	}

}