package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;

public class HistoryListener implements Listener {
    private final Deque<Memento> stack = new ArrayDeque<>();

    private final LocalDateTime dateTime;

    public HistoryListener(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        State state = new State(oldMsg, newMsg);
        System.out.println(state);
        stack.push(new Memento(state, dateTime));
    }

    public State restoreState() {
        var memento = stack.pop();
        System.out.println("createdAt:" + memento.getCreatedAt());
        return memento.getState();
    }
}
