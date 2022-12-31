package com.arm.util;

public enum TestEnum implements BitMultipleChoiceEnum {
    /**
     *
     */
    none(0),
    interf(1),
    db(2),
    file(4);

    private final int code;


    TestEnum(int code) {
        this.code = code;
    }

    @Override
    public int[] getCodes() {
        return new int[]{1, 2, 4};
    }


    public static void main(String[] args) {
        BitMultipleChoiceOperator bmc = new BitMultipleChoiceOperator(TestEnum.file);
        bmc.being(none.code);
    }
}
