package com.oris.notes.exception;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(Long id) {
        super("Заметка с id=" + id + " не найдена");
    }
}
