package org.example;

import org.example.service.BudgetTracker;

public class Main {

    static void main() {
        final BudgetTracker budgetTracker = BudgetTracker.getInstance();

        IO.println("Hi, thank you for using the budget tracker!");

        budgetTracker.run();
    }
}
