/**
 * 
 */
package com.n9.mini.sns.services;

import org.hibernate.HibernateException;

/**
 * @author HoangNN6
 * 
 */
public interface FileProcessingServices {

	String getFilePath(String downloadRootDirectory, String fileType, String extension, Integer accountId, String album) throws HibernateException, Exception;

	Integer saveImagePath(String filename, Integer accountId) throws HibernateException, Exception;

}
