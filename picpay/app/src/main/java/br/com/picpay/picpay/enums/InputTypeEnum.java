package br.com.picpay.picpay.enums;

import android.support.annotation.Nullable;

public enum InputTypeEnum {
    NAME(0),
    CARD(1),
    DATE_CARD(2),
    NUMBER(3),
    VALUE(4);

    private final int option;

    InputTypeEnum(int option) {
        this.option = option;
    }

    @Nullable
    public static InputTypeEnum getOptionEnum(int option) {
        for (InputTypeEnum inputTypeEnum : InputTypeEnum.values()) {
            if (inputTypeEnum.option == option) {
                return inputTypeEnum;
            }
        }
        return null;
    }
}
