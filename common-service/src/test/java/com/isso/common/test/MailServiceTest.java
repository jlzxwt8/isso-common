package com.isso.common.test;


import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.isso.common.CommonServiceException;
import com.isso.common.IMailService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class MailServiceTest {

	@Autowired
	private IMailService mailService;
	
	@Before
	public void setup() {
	}
	
	@Test
	public void testCache() throws CommonServiceException {
		String templateName = "passwordResetNotification.html";
		String[] toAddress = {"jlzxwt8@126.com"};
		String subject = "找回密码";
		Map<String, String> editPointMap = new HashMap<String, String>();
		editPointMap.put("key", "url");
		mailService.sendMail(toAddress, subject, templateName, editPointMap);
	}
	@After
	public void destroy() {
	}
}
