package org.example.service;

import org.example.enums.Action;
import org.example.enums.TransactionType;
import org.example.model.Transaction;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Optional;

public class BudgetTracker {

    private static BudgetTracker instance = null;

    private final ArrayList<Transaction> transactions;

    private BudgetTracker() {
        this.transactions = new ArrayList<>();
    }

    public static BudgetTracker getInstance() {
        if (instance == null) {
            instance = new BudgetTracker();
        }

        return instance;
    }

    public void run() {
        IO.println("What would you like to do?");

        askForAction();
    }

    private void askForAction() {
        for (Action action : Action.values()) {
            IO.println(String.format("[%d] %s", action.getIdentifier(), action.getDescription()));
        }

        final int decision = Integer.parseInt(IO.readln());
        final Optional<Action> optionalAction = Action.findByIdentifier(decision);

        if (optionalAction.isPresent()) {
            final Action action = optionalAction.get();

            switch (action) {
                case Action.ADD_TRANSACTION -> askForTransactionType();
                case Action.SHOW_ALL_TRANSACTIONS -> listAllTransactions();
                case Action.CALCULATE_BALANCE -> calculateBalance();
            }

            return;
        }

        askForAction();
    }

    private void calculateBalance() {
        final float income = transactions.stream()
                .filter(transaction -> transaction.type() == TransactionType.INCOME)
                .map(Transaction::amount)
                .reduce(0.0f, Float::sum);
        final float expenses = transactions.stream()
                .filter(transaction -> transaction.type() == TransactionType.EXPENSE)
                .map(Transaction::amount)
                .reduce(0.0f, Float::sum);

        IO.println(String.format("Your balance is: %s", NumberFormat.getCurrencyInstance().format(income - expenses)));

        this.run();
    }

    private void listAllTransactions() {
        transactions.forEach(
                transaction -> IO.println(String.format("%s: %s", transaction.type().getDescription(), NumberFormat.getCurrencyInstance().format(transaction.amount())))
        );

        this.run();
    }

    private void askForTransactionType() {
        for (TransactionType transactionType : TransactionType.values()) {
            IO.println(String.format("[%d] %s", transactionType.getIdentifier(), transactionType.getDescription()));
        }

        final int decision = Integer.parseInt(IO.readln());
        final Optional<TransactionType> optionalTransactionType = TransactionType.findByIdentifier(decision);

        if (optionalTransactionType.isPresent()) {
            final TransactionType transactionType = optionalTransactionType.get();

            float amount;

            do {
                IO.println(String.format("What is the amount of the %s?", transactionType.getDescription().toLowerCase()));

                amount = Float.parseFloat(IO.readln());
            } while (amount <= 0);

            transactions.add(new Transaction(amount, transactionType));

            IO.println(String.format("Succesfully added a new transaction (%s): %s", transactionType.getDescription().toLowerCase(), NumberFormat.getCurrencyInstance().format(amount)));

            this.run();

            return;
        }

        IO.println("That is not a valid option. Choose one of the options below:\n");

        askForTransactionType();
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}
