package com.web.yuce.common.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 林尧
 *
 *         2015年12月30日 Java 的排列组合
 */
public class JavaPaiLieZuHeNinM {

	
	//准备数据
	private static String[] numbers = new String[] { "11", "12", "13", "15" };
    
	//记录最终有多少种组合
	private static int total;
    
	//设置1组 有几个数字
	private static int m = 3;

	/**
	 * 递归算法
	 * @param s
	 * @param templist
	 * @param m
	 */
	private void plzh(String s, List<Integer> templist, int m) {
		if (m == 0) {
			System.out.println(s);
			total++;
			return;
		}
		
		
		List<Integer> iL2;
		
		//4 个数字  4次
		for (int i = 0; i < numbers.length; i++) {
            
			//每一次创建一个新的数组集合
			iL2 = new ArrayList<Integer>();
			
			iL2.addAll(templist);
			if (!templist.contains(i)) {
				String str = s + "----" + numbers[i];
				iL2.add(i);
				plzh(str, iL2, m - 1);
			}
		}
	}

	public static void main(String[] args) {

		// 47 11 45 44 36 38 18 48 16 42 6 25 21

		List<Integer> templist = new ArrayList<Integer>();

		new JavaPaiLieZuHeNinM().plzh("", templist, m);

		System.out.println("total : " + total / 2);
	}

}
