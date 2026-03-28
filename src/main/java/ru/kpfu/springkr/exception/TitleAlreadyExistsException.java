package ru.kpfu.springkr.exception;

public class TitleAlreadyExistsException extends RuntimeException {

    public TitleAlreadyExistsException(String title) {
        super("Note with title " + title + " already exists");
    }

}
