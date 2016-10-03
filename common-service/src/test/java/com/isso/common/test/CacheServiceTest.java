package com.isso.common.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.isso.common.CommonServiceException;
import com.isso.common.ICacheService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-config.xml")
public class CacheServiceTest {

	@Autowired
	private ICacheService cacheService;
	
	@Before
	public void setup() {
	}
	
	@Test
	public void testCache() throws CommonServiceException {
		cacheService.setCacheValue("com.isso.idm.authorize","152730",60, "1002");
		assertEquals(((String)cacheService.getCacheValue("com.isso.idm.authorize","152730")),"1002");
	}
	@After
	public void destroy() {
	}
}
