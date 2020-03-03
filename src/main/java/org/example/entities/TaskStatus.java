package org.example.entities;

public enum TaskStatus {

    NEW("НОВАЯ"),
    IN_PROGRESS("В РАБОТЕ"),
    CLOSED("ЗАКРЫТА");

    private String state;

    TaskStatus(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
