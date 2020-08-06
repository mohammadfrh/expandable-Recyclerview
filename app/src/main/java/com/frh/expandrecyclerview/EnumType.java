package com.frh.expandrecyclerview;

public enum EnumType {

    UNDEFINE("UNDEFINED", -1),

    LISTMASSAGES_NOT_READ("LISTMASSAGESNOTREAD" , 1),
    LISTMASSAGES_READ("LISTMASSAGESREAD" , 2),

    LISTTRANSACTION("TRANSACTION" , 3)
    ;

    private String valueStr;

    private Integer value;

    EnumType(String valueStr, Integer value) {
        this.valueStr = valueStr;
        this.value = value;
    }

    public static EnumType get(String value) {
        if (value == null) {
            return UNDEFINE;
        }

        EnumType[] arr$ = values();
        for (EnumType val : arr$) {
            if (val.valueStr.equalsIgnoreCase(value.trim())) {
                return val;
            }
        }

        return UNDEFINE;
    }

    public static EnumType get(Integer value) {

        if (value == null) {
            return UNDEFINE;
        }

        EnumType[] arr$ = values();
        for (EnumType val : arr$) {
            if (val.value == value) {
                return val;
            }
        }

        return UNDEFINE;
    }

    public String getValueStr() {
        return valueStr;
    }

    public Integer getValue() {
        return value;
    }

    public void setValueStr(String valueStr) {
        this.valueStr = valueStr;
    }
}
