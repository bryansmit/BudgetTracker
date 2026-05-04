package org.example.model;

import org.example.enums.TransactionType;

public record Transaction(float amount, TransactionType type) {
}
