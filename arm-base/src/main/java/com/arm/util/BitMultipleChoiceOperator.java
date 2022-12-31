package com.arm.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BitMultipleChoiceOperator {

    private final int[] nums;

    public BitMultipleChoiceOperator(BitMultipleChoiceEnum code) {
        this.nums = code.getCodes();
        if (!inCode(nums)) {
            throw new IllegalArgumentException(String.format("参数错误，数据定义必须在【%s】中。", Arrays.toString(BitMultipleChoiceEnum.CODE)));
        }
    }

    public boolean having(int number, int memberCode) {
        return (number & memberCode) == memberCode;
    }

    /**
     * 将存储值拆分。
     * 比如number = 7 => 1,2,4
     *
     * @param number
     * @return
     */
    public int[] mapping(int number) {
        return Arrays.stream(nums).filter(v -> (number & v) == v).toArray();
    }


    public int merging(int... memberCode) {
        return Arrays.stream(memberCode).sum();
    }


    public Integer[] being(int... memberCodes) {
        if (!inCode(memberCodes)) {
            throw new IllegalArgumentException("参数错误");
        }
        int sum = Arrays.stream(memberCodes).sum();
        Integer[] excludedCodes = excluded(memberCodes);
        List<List<Integer>> objects = new ArrayList<>();

        backtrack(objects, new ArrayList<>(), excludedCodes, 0);

        List<Integer> allSum = new ArrayList<>();
        allSum.add(sum);

        objects.forEach(o -> {
            int i = 0;
            for (Integer integer : o) {
                i += integer;
            }
            allSum.add(i + sum);
        });

        return allSum.toArray(new Integer[0]);

    }


    /**
     * 回文数，列举数组中数的所有组合
     * {1, 2, 3, 4} => [[1, 2, 3, 4], [1, 2, 4], [1, 3, 4], [1, 4], [2, 3, 4], [2, 4], [3, 4], [4]]
     *
     * @param list
     * @param tempList
     * @param nums
     * @param start
     */
    private static void backtrack(List<List<Integer>> list, List<Integer> tempList, Integer[] nums, int start) {
        if (start != 0) {
            list.add(new ArrayList<>(tempList));
        }
        for (int i = start; i < nums.length; i++) {
            tempList.add(nums[i]);
            backtrack(list, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
        }

    }


    /**
     * 排除当前值
     * 比如 memberCodes = 1,2 则返回 4, 8, 16, 32, 64
     *
     * @param memberCodes
     * @return
     */
    private Integer[] excluded(int... memberCodes) {
        List<Integer> excludedSet = new ArrayList<>(nums.length);
        for (int i : nums) {
            boolean result = false;
            for (int memberCode : memberCodes) {
                if (memberCode == i) {
                    result = true;
                    break;
                }
            }
            if (!result) {
                excludedSet.add(i);
            }
        }
        return excludedSet.toArray(new Integer[0]);
    }


    private boolean inCode(int... memberCodes) {
        for (int memberCode : memberCodes) {
            boolean result = false;
            for (int i : BitMultipleChoiceEnum.CODE) {
                if (memberCode == i) {
                    result = true;
                    break;
                }
            }
            if (!result) {
                return false;
            }
        }
        return true;
    }
}
