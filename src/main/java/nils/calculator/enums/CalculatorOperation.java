package nils.calculator.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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
        throw new IllegalArgumentException("Invalid enum value");
    }

    @JsonValue
    public String toValue() {
        for (CalculatorOperation entry : values()) {
            if (entry == this)
                return entry.getShortName();
        }
        return null;
    }

    public String getShortName() {
        return shortName;
    }

    @Override
    public String toString() {
        return shortName;
    }

}
