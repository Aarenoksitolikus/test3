package ru.kpfu.springkr.exception;

public class EmptyParameterException extends RuntimeException {

    public EmptyParameterException() {
        super("You must provide at least one url parameter: titleContains, contentContains");
    }

}
