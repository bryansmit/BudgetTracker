package org.example.enums;

import java.util.Arrays;
import java.util.Optional;

public enum Action {
    ADD_TRANSACTION(1, "Add a transaction"),
    SHOW_ALL_TRANSACTIONS(2, "Show all transactions"),
    CALCULATE_BALANCE(3, "Calculate the current balance");

    private final int identifier;
    private final String description;

    Action(int identifier, String description) {
        this.identifier = identifier;
        this.description = description;
    }

    public static Optional<Action> findByIdentifier(int identifier) {
        return Arrays.stream(values()).filter(action -> action.getIdentifier() == identifier).findFirst();
    }

    public String getDescription() {
        return this.description;
    }

    public int getIdentifier() {
        return identifier;
    }
}
