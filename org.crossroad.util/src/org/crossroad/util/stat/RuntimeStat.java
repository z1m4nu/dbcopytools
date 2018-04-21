package org.crossroad.util.stat;

import java.util.Properties;

public class RuntimeStat {

	
	private long startTime = 0L;
	private long endTime = 0L;
	
	private long startUsedMemory = 0L;
	private long endUsedMemory = 0L;
	
	
	public RuntimeStat() {
		
	}

	

	private long computeUsedMemory()
	{
		long total = Runtime.getRuntime().totalMemory();
		long free = Runtime.getRuntime().freeMemory();
		
		return (total - free);
	}
	
	/**
	 * 
	 */
	public void markStart()
	{
		this.startTime = System.currentTimeMillis();
		this.startUsedMemory = computeUsedMemory();
	}
	/**
	 * 
	 */
	public void markEnd()
	{
		this.endTime = System.currentTimeMillis();
		this.endUsedMemory = computeUsedMemory();
	}

	public long getStartMemory()
	{
		return this.startUsedMemory;
	}
	
	public long getEndMemory()
	{
		return this.endUsedMemory;
	}
	
	public long getStartTime()
	{
		return this.startTime;
	}
	
	public long getEndTime()
	{
		return this.endTime;
	}
	
	public long getSpendTime()
	{
		return (this.getEndTime() - this.getStartTime());
	}
	
	public long getConsumeMemory()
	{
		return (this.getEndMemory() - this.getStartMemory());
	}
	
	
}
