package ru.drozdov.MySecondTestAppSpringBoot.exception;

public class ValidationFailedException  extends Exception {
    public ValidationFailedException(String message) { super(message); }
}
