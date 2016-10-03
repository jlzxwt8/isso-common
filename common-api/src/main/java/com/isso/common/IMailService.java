
package com.isso.common;

import java.util.Map;


public interface IMailService {
	/**
	 * send mail
	 * @Author : Wang Tong
	 * @param toAddress
	 * @param subject
	 * @param content
	 * @param editPointMap
	 * @throws CommonServiceException
	 */
	public void sendMail(String[] toAddress, String subject, String templateName, Map<String, String> editPointMap) throws CommonServiceException;
}
