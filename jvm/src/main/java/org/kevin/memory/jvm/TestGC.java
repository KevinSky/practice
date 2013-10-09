package org.kevin.memory.jvm;

public class TestGC {

	public static final int _1MB = 1024 * 1024;
	
	/**
	 * VM参数: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:+PrintGCDetails -XX:UseParNewGC
	 */
	public static void testAllocation() {
		byte[] allocation1, allocation2, allocation3, allocation4,allocation5;
		allocation1 = new byte[2 * _1MB];
		allocation2 = new byte[2 * _1MB];
		allocation3 = new byte[2 * _1MB];
		allocation4 = new byte[4 * _1MB];	// 这里将出现一次Minor GC
		System.out.println("线束");
	}
	
	/**
	 * VM参数: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:SurvivorRatio=8 -XX:UseParNewGC -XX:+PrintTenuringDistribution -XX:MaxTenuringThreshold=1 
	 */
	@SuppressWarnings("unused")
	public static void testTenuringThreshold() {
		byte[] allocation1, allocation2,allocation3;
		allocation1 = new byte[_1MB / 4];
			//什么时候进入老年代了决于XX:MaxTenuringThreshold设置
		allocation2 = new byte[4 * _1MB];
		allocation3 = new byte[4 * _1MB];
		
		allocation3 = null;
		allocation3 = new byte[4 * _1MB];
		
	}
	
	public static void main(String[] args) {
//		testAllocation();
		testTenuringThreshold();
		
	}
}
