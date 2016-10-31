/*******************************************************************
 * GsonModel.java   2016Äê10ÔÂ31ÈÕ
 * Copyright 2015 by GNNT Company. All Rights Reserved.
 * Author: fujj 
 ******************************************************************/
package top.klweb.staticmodel;

/**
 * @author fujj
 *
 */
public class GsonModel {
	private String hash;
	private String key;
	/**
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}
	/**
	 * @param hash the hash to set
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}
	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GsonModel [hash=" + hash + ", key=\n" + key + "\n]";
	}
}
