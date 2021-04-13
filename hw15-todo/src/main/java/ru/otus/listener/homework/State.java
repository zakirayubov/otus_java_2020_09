package ru.otus.listener.homework;

public class State {
    private final MessageHistory messageHistory;

    public State(MessageHistory messageHistory) {
        this.messageHistory = messageHistory;
    }

    public State(State state) {
        this.messageHistory = new MessageHistory(state.getMessageHistory());
    }

    public MessageHistory getMessageHistory() {
        return messageHistory;
    }

    @Override
    public String toString() {
        return "State{" +
                "messageHistory=" + messageHistory +
                '}';
    }
}
