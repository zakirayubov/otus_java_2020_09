package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;

public class HistoryListener implements Listener {

    private final Deque<Memento> stack = new ArrayDeque<>();

    @Override
    public void onUpdated(Message oldMsg, Message newMsg) {
        MessageHistory messageHistory = new MessageHistory(oldMsg, newMsg);
        State state = new State(messageHistory);
        Memento memento = new Memento(state, LocalDateTime.now());
        stack.push(memento);
    }

    public State restoreState() {
        var memento = stack.pop();
        System.out.println("createdAt:" + memento.getCreatedAt());
        return memento.getState();
    }
}
