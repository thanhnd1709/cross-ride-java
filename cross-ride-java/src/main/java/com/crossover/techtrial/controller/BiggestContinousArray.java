package com.crossover.techtrial.controller;

public class BiggestContinousArray {

	public static void main(String[] args) {
		int[] a = { -2, -3, 4, -1, -2, 1, 5, -3 };
		System.out.println("Maximum contiguous sum is " + maxSubArraySum(a));
	}

	static int maxSubArraySum(int a[]) {
		int max = Integer.MIN_VALUE;
		int max_end = 0;
		for (int k = 0; k < a.length; k++) {
			max_end = max_end + a[k];
			if (max < max_end) {
				max = max_end;
			}
			if (max_end < 0) {
				max_end = 0;
			}

		}
		return max;
	}
}
