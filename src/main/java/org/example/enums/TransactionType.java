package org.example.enums;

import java.util.Arrays;
import java.util.Optional;

public enum TransactionType {
    INCOME(1, "Income"),
    EXPENSE(2, "Expense");

    private final int identifier;
    private final String description;

    TransactionType(int identifier, String description) {
        this.identifier = identifier;
        this.description = description;
    }

    public static Optional<TransactionType> findByIdentifier(int identifier) {
        return Arrays.stream(values()).filter(action -> action.getIdentifier() == identifier).findFirst();
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getDescription() {
        return description;
    }
}
