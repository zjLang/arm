package com.arm.util;

/**
 * 通过位与操作多选
 */
public interface BitMultipleChoiceEnum {

    int[] getCodes();

    /**
     * 最多支持7个多选，再多也无意义
     */
    int[] CODE = new int[]{1, 2, 4, 8, 16, 32, 64};


}
