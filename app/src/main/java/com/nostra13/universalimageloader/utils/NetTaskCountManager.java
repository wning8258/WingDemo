package com.nostra13.universalimageloader.utils;


import com.nostra13.universalimageloader.core.listener.TaskStateListener;

/**
 * 
 * <p>Copyright: Copyright (c) 2013</p>
 * 
 * <p>Company: 呱呱视频社区<a href="www.guagua.cn">www.guagua.cn</a></p>
 *
 * @description TODO
 *	这个类用于图片加载的任务统计功能
 *	当用户调用displayImage()函数的时候，自增1
 *	当图片从缓存或者网络中取得，自减1
 *
 * @author TPX
 * @modify 
 * @version 1.0.0
 */
public class NetTaskCountManager {
	
	/**	计数器  */
	private volatile int count ;
	
	private TaskStateListener listener;
	
	private volatile static NetTaskCountManager instance;
	
	private NetTaskCountManager() {
		this.count = 0 ;
	}
	
	public static NetTaskCountManager getInstance() {
		if (instance == null) {
			synchronized (NetTaskCountManager.class) {
				if (instance == null) {
					instance = new NetTaskCountManager();
				}
			}
		}
		return instance;
	}
	
	public void addTask() {
		synchronized (NetTaskCountManager.class) {
			++count ;
		}
	}
	
	public void removeTask() {
		synchronized (NetTaskCountManager.class) {
			--count ;
			if (count == 0 && listener != null) {
				listener.onTaskMapIsNull();
			}
		}
		
	}
	
	public void setTaskStateListener(TaskStateListener listener) {
		this.listener = listener ;
	}
	
}
