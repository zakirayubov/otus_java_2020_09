package ru.otus.listener.homework;

import ru.otus.model.Message;

public class State {
    private Message oldMsg;
    private Message newMsg;

    public State(Message oldMsg, Message newMsg) {
        this.oldMsg = oldMsg;
        this.newMsg = newMsg;
    }

    public State(State state) {
        this.oldMsg = state.getOldMsg();
        this.newMsg = state.getNewMsg();
    }

    public Message getOldMsg() {
        return oldMsg;
    }

    public Message getNewMsg() {
        return newMsg;
    }

    @Override
    public String toString() {
        return "State{" +
                "oldMsg=" + oldMsg +
                ", newMsg=" + newMsg +
                '}';
    }
}
