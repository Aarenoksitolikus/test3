package com.example.demo.exceptions

class NoteNotFoundException(id: Long) : RuntimeException("Note with id=$id not found")
