/**
 * 
 */
package com.n9.mini.sns.web.job;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.n9.mini.sns.domain.commons.utils.DateUtils;
import com.n9.mini.sns.domain.commons.utils.FilenameUtils;

/**
 * @author HoangNN6
 * 
 */
public class RegconizeJob extends QuartzJobBean implements StatefulJob {

	private static final Log LOGGER = LogFactory.getLog(RegconizeJob.class);

	ApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(ContextLoaderListener.getCurrentWebApplicationContext().getServletContext());

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		try {
			String today = DateUtils.today(DateUtils.YYYY_MM_DD);
			Iterator<String> filePaths = FilenameUtils.getFilePath("/tmp/" + today).iterator();
			while (filePaths.hasNext()) {
				String filePath = filePaths.next();
				ProcessBuilder processBuilder = new ProcessBuilder("/etc/toshiba-dpet/reg/DogReg.exe", filePath);
				processBuilder.directory(new File("/etc/toshiba-dpet/reg"));
				Process pr = processBuilder.start();
				pr.waitFor();
				LOGGER.info("exec /etc/toshiba-dpet/reg/DogReg.exe " + filePath + " exitValue: " + pr.exitValue());
			}

		} catch (IOException e) {
			LOGGER.error("exec /etc/toshiba-dpet/reg/DogReg.exe fail: " + e.getMessage());
		} catch (InterruptedException e) {
			LOGGER.error("exec /etc/toshiba-dpet/reg/DogReg.exe fail: " + e.getMessage());
		}
	}
}
