package com.nostra13.universalimageloader.core.listener;

/**
 * 
 * <p>Copyright: Copyright (c) 2013</p>
 * 
 * <p>Company: 呱呱视频社区<a href="www.guagua.cn">www.guagua.cn</a></p>
 *
 * @description TODO
 *	任务状态回调
 *	这个监听器是为了大厅列表缓存机制所增加的。
 *	监听缓存的列表中是否又任务，如果没有任务，就调用回调的方法
 *
 * @author TPX
 * @modify 
 * @version 1.0.0
 */
public interface TaskStateListener {
	
	void onTaskMapIsNull() ;

}
