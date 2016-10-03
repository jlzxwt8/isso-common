
package com.isso.common.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.isso.common.CommonServiceException;
import com.isso.common.ICacheService;
import com.isso.common.constant.CommonServiceErrorConstant;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.MemcachedClientCallable;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

@Service("CacheService")
public class CacheServiceImpl implements ICacheService {
	
	// memcached server host name
	@Value("#{propertyConfigurer['com.isso.common.memcached.address']}")
	private String address;

	// memcached server pool size
	@Value("#{propertyConfigurer['com.isso.common.memcached.poolsize']}")
	private int poolSize;
	
	@Override
	public Boolean setCacheValue(String cacheNamespace,String name, int cacheTokenTime, Object value)
			throws CommonServiceException {
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(address));
		builder.setConnectionPoolSize(poolSize);
		Boolean result = false;
		try {
			MemcachedClient memcachedClient = builder.build();
			result = memcachedClient.withNamespace(cacheNamespace,
	                new MemcachedClientCallable<Boolean>() {

	                    public Boolean call(MemcachedClient client)
	                            throws MemcachedException, InterruptedException,
	                            TimeoutException {
	                                                client.set(name,cacheTokenTime,value);
	                                                return true;
	                    }
	                });
			//close memcached client
			memcachedClient.shutdown();
		} catch (MemcachedException e) {
			throw new CommonServiceException(CommonServiceErrorConstant.ERROR_CACHE_OPERATION, "MemcachedClient operation fail");
		} catch (TimeoutException e) {
			throw new CommonServiceException(CommonServiceErrorConstant.ERROR_CACHE_OPERATION, "MemcachedClient operation timeout");
		} catch (InterruptedException e) {
			throw new CommonServiceException(CommonServiceErrorConstant.ERROR_CACHE_OPERATION, "interrupted exception");
		} catch (IOException e) {
			throw new CommonServiceException(CommonServiceErrorConstant.ERROR_CACHE_OPERATION, "MemcachedClient open or shutdown fail");
		}
		return result;
	}

	@Override
	public Object getCacheValue(String cacheNamespace,String name) throws CommonServiceException {
		Object value = null;
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(address));
		builder.setConnectionPoolSize(poolSize);
		
		try {
			MemcachedClient memcachedClient = builder.build();
			value = memcachedClient.withNamespace(cacheNamespace,
	                new MemcachedClientCallable<Object>() {

	                    public Object call(MemcachedClient client)
	                            throws MemcachedException, InterruptedException,
	                            TimeoutException {
	                    		return client.get(name);
	                    }
	                });
			//close memcached client
			memcachedClient.shutdown();
		} catch (MemcachedException e) {
			throw new CommonServiceException(CommonServiceErrorConstant.ERROR_CACHE_OPERATION, "MemcachedClient operation fail");
		} catch (TimeoutException e) {
			throw new CommonServiceException(CommonServiceErrorConstant.ERROR_CACHE_OPERATION, "MemcachedClient operation timeout");
		} catch (InterruptedException e) {
			throw new CommonServiceException(CommonServiceErrorConstant.ERROR_CACHE_OPERATION, "interrupted exception");
		} catch (IOException e) {
			throw new CommonServiceException(CommonServiceErrorConstant.ERROR_CACHE_OPERATION, "MemcachedClient open or shutdown fail");
		}
		return value;
	}
	
	@Override
	public Boolean deleteCacheValue(String cacheNamespace,String name)
			throws CommonServiceException {
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(address));
		builder.setConnectionPoolSize(poolSize);
		Boolean result = false;
		try {
			MemcachedClient memcachedClient = builder.build();
			result = memcachedClient.withNamespace(cacheNamespace,
	                new MemcachedClientCallable<Boolean>() {

	                    public Boolean call(MemcachedClient client)
	                            throws MemcachedException, InterruptedException,
	                            TimeoutException {
	                                                client.delete(name);
	                                                return true;
	                    }
	                });
			//close memcached client
			memcachedClient.shutdown();
		} catch (MemcachedException e) {
			throw new CommonServiceException(CommonServiceErrorConstant.ERROR_CACHE_OPERATION, "MemcachedClient operation fail");
		} catch (TimeoutException e) {
			throw new CommonServiceException(CommonServiceErrorConstant.ERROR_CACHE_OPERATION, "MemcachedClient operation timeout");
		} catch (InterruptedException e) {
			throw new CommonServiceException(CommonServiceErrorConstant.ERROR_CACHE_OPERATION, "interrupted exception");
		} catch (IOException e) {
			throw new CommonServiceException(CommonServiceErrorConstant.ERROR_CACHE_OPERATION, "MemcachedClient open or shutdown fail");
		}
		return result;
	}
}
