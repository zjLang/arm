package com.arm.util;

import java.util.*;

/**
 * 用位与表示多选
 *
 * @author zhaolangjing
 */
public class EnumCode {

    /**
     * 可继续后续扩展 ，采用了2 进制的特点。
     * 1,2,4,8,16,32,64,128 ...
     * <p>
     * 举列： 3&1=1 ==> 3是否含有数字1
     * <p>
     * (1 | 2) | 2 = 2
     * (1 | 4) | 5 = 5
     * (1 | 2) | 4 = 7
     */
    public static final int[] CODE = new int[]{1, 2, 4, 8};

    /**
     * 是否包含某个数值 。 比如3中是否含有1 。
     *
     * @param number     当前存储值
     * @param memberCode 当前成员值
     * @return true 含有 false 没有
     */
    public static boolean having(int number, int memberCode) {
        return (number & memberCode) == memberCode;
    }

    /**
     * 当前集合包含的数据
     * 比如number = 7 => 1,2,4
     *
     * @param number 当前存储值
     * @return 当前成员的局部视图
     */
    public static int[] mapping(int number) {
        return Arrays.stream(CODE).filter(v -> (number & v) == v).toArray();
    }

    /**
     * 合并值
     * 比如 1，2 => 3
     *
     * @param memberCode 当前成员值合集视图
     * @return 返回存储值
     */
    public static int merging(int[] memberCode) {
        return Arrays.stream(memberCode).sum();
    }

    /**
     * 返回存在某个成员的集合数
     * 比如 参数[1,2] =>  3 , 7 ,
     *
     * @param memberCodes 当前的成员值数组
     * @return 返回存储值
     */
    public static Integer[] being(int... memberCodes) {
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
     * 排除当前值
     * 比如 memberCodes = 1,2 则返回 4, 8, 16, 32, 64
     *
     * @param memberCodes
     * @return
     */
    private static Integer[] excluded(int... memberCodes) {
        List<Integer> excludedSet = new ArrayList<>(CODE.length);
        for (int i : CODE) {
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

    /**
     * 判断当前数组值是否在CODE里面
     * 1, 2, 4 => true
     * 4, 5, 6 => false
     *
     * @param memberCodes
     * @return
     */
    public static boolean inCode(int... memberCodes) {
        for (int memberCode : memberCodes) {
            boolean result = false;
            for (int i : CODE) {
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


    /**
     * candidates 候选数组
     * begin      搜索起点
     * len        candidates 的长度属性，可以不传
     * target     每减去一个元素，目标值变小
     * path       从根结点到叶子结点的路径，是一个栈
     * res        结果集列表
     */
    private static void dfs(int[] candidates, int begin, int len, int target, Deque<Integer> path, List<List<Integer>> res) {
        // 递归终止条件值只判断等于 0 的情况
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }
        // 从begin开始搜索
        for (int i = begin; i < len; i++) {
            // 候选数组有序，当小于0时便可以终止循环
            if (target - candidates[i] < 0) {
                break;
            }
            //符合条件，往path最后的位置添加元素
            path.addLast(candidates[i]);
            // 元素不可重复使用
            dfs(candidates, i + 1, len, target - candidates[i], path, res);
            // 元素都是重复的，但是起点变为了i，目标值减去已经添加的数据
            //dfs(candidates, i, len, target - candidates[i], path, res);
            //执行到这一步证明多添加了一个元素，所以需要删除最后一个元素
            path.removeLast();
        }
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


    public static void main(String[] args) {
        System.out.println(having(3, 1));
        System.out.println(Arrays.toString(mapping(9)));
        System.out.println(merging(new int[]{1, 2}));
        // = 2
        System.out.println(((1 | 2) | 2));
        // = 7
        System.out.println(((1 | 2) | 4));
        // = 5
        System.out.println(((1 | 4) | 5));

        System.out.println(inCode(1, 2, 4));
        System.out.println(inCode(4, 5, 6));
        System.out.println(inCode(11, 12, 8));
        System.out.println(inCode(1, 4, 8));


        System.out.println(Arrays.toString(excluded(1, 4, 8)));
        System.out.println(Arrays.toString(excluded(5, 5, 6)));

        List<List<Integer>> objects = new ArrayList<>();
        //dfs(CODE, 0, CODE.length, 65, new ArrayDeque<>(), objects);
        int[] ints = {1, 2, 3};
        dfs(ints, 0, ints.length, 6, new ArrayDeque<>(), objects);
        System.out.println(objects);


        List<List<Integer>> objects2 = new ArrayList<>();
        // 1 , 2, 3, (1,2) , (1,3) , (2,3) , (1,2,3)
        Integer[] ints2 = {1, 2};
        /*dfh(ints2, 0, ints2.length, 1, new ArrayDeque<>(), objects2);
        System.out.println("objects2 :" + objects2);
        System.out.println(cycle);*/


        backtrack(objects2, new ArrayList<>(), ints2, 0);
        System.out.println(objects2);


        System.out.println("being == " + Arrays.toString(being(1)));
    }
}
