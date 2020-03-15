package org.example.entities;

public enum TaskStatus {

    NEW("НОВАЯ"),
    IN_PROGRESS("В РАБОТЕ"),
    CLOSED("ЗАКРЫТА");

    private final String value;

    TaskStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
