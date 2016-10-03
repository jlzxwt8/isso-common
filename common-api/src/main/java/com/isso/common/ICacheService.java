
package com.isso.common;



public interface ICacheService {
	/**
	 * set value in the cache
	 * @Author : Wang Tong
	 * @param cacheNamespace
	 * @param name
	 * @param cacheTokenTime
	 * @param value
	 * @return result
	 * @throws CommonServiceException
	 */
	public Boolean setCacheValue(String cacheNamespace, String name, int cacheTokenTime, Object value) throws CommonServiceException;

	/**
	 * get value from cache
	 * @Author : Wang Tong
	 * @param cacheNamespace
	 * @param name
	 * @return value
	 * @throws CommonServiceException
	 */
	public Object getCacheValue(String cacheNamespace,String name) throws CommonServiceException;
	
	/**
	 * delete value from cache
	 * @Author : Wang Tong
	 * @param cacheNamespace
	 * @param name
	 * @return result
	 * @throws CommonServiceException
	 */
	public Boolean deleteCacheValue(String cacheNamespace,String name) throws CommonServiceException;
}
