package nils.calculator.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum CalculatorOperation {
    DIVIDE("div"),
    MULITIPLY("prod"),
    ADD("sum"),
    SUBTRACT("sub");

    private final String shortName;

    CalculatorOperation(String shortName) {
        this.shortName = shortName;
    }

    @JsonCreator
    public static CalculatorOperation getEnum(String value) {
        for (CalculatorOperation v : values()) {
            if (v.getShortName().equalsIgnoreCase(value)) {
                return v;
            }
        }
        throw new IllegalArgumentException();
    }

    public String getShortName() {
        return shortName;
    }

    @Override
    public String toString() {
        return shortName;
    }

}
