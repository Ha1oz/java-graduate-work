package ru.skypro.homework.exception;

public class CommentsNotFoundException extends RuntimeException{
    public CommentsNotFoundException(String message) {
        super(message);
    }
}
