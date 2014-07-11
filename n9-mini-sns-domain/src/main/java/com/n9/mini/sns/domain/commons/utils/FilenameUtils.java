/**
 * 
 */
package com.n9.mini.sns.domain.commons.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.RegexFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

/**
 * @author HoangNN6
 * 
 */
public class FilenameUtils {

	public static final String IMAGE_TYPE = "image";
	public static final String VIDEO_TYPE = "video";
	public static final String OTHER = "other";

	/**
	 * @param directory
	 * @param filename
	 * @return
	 */
	public static String getFilePath(String directory, String filename) {
		File dir = new File(directory);
		String filenamePattern = "^(" + filename + ".*?)";
		Collection<File> files = FileUtils.listFiles(dir, new RegexFileFilter(filenamePattern), DirectoryFileFilter.DIRECTORY);

		return (files == null || files.size() == 0) ? null : ((File) files.toArray()[0]).getAbsolutePath();
	}
	
	/**
	 * @param directory
	 * @return
	 */
	public static List<String> getFileName(String directory) {
		File dir = new File(directory);
		String filenamePattern = "^(.*?)";
		Collection<File> files = FileUtils.listFiles(dir, new RegexFileFilter(filenamePattern), DirectoryFileFilter.DIRECTORY);

		List<String> result = new ArrayList<String>();
		for (File file : files) {
			result.add(file.getName());
		}

		return result;
	}

	/**
	 * @param directory
	 * @return
	 */
	public static List<String> getFilePath(String directory) {
		File dir = new File(directory);
		String filenamePattern = "^(.*?)";
		Collection<File> files = FileUtils.listFiles(dir, new RegexFileFilter(filenamePattern), DirectoryFileFilter.DIRECTORY);

		List<String> result = new ArrayList<String>();
		for (File file : files) {
			result.add(file.getAbsolutePath());
		}

		return result;
	}

	/**
	 * @param path
	 * @return
	 */
	public static String getFileNameFromPath(String path) {
		return path.substring(path.lastIndexOf(StringUtils.SLASH) + 1, path.lastIndexOf(StringUtils.DOT));
	}

	/**
	 * @param directory
	 * @return
	 */
	public static List<String> getAllSubDirectories(String directory) {
		Collection<File> files = FileUtils.listFilesAndDirs(new File(directory), new NotFileFilter(TrueFileFilter.INSTANCE), DirectoryFileFilter.DIRECTORY);

		List<String> result = new ArrayList<String>();
		for (File file : files) {
			result.add(file.getName());
		}

		return result.subList(1, result.size() - 1);
	}
}
