package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

//todo: Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
public class EvenSecondExceptionProcessor implements Processor {
    private final LocalDateTime dateTime;

    public EvenSecondExceptionProcessor(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public Message process(Message message) {
        int second = dateTime.getSecond();
        System.out.println("EvenSecondExceptionProcessor - second: " + second);
        if (second % 2 == 0) {
            throw new RuntimeException();
        }
        return message;
    }
}
