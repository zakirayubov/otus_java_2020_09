package ru.otus.listener.homework;

import java.time.LocalDateTime;

public class Memento {
    private final State state;
    private final LocalDateTime createdAt;

    public Memento(State state, LocalDateTime createdAt) {
        this.state = new State(state);
        this.createdAt = createdAt;
    }

    public State getState() {
        return state;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
