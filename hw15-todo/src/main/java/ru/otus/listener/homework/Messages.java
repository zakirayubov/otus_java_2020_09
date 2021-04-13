package ru.otus.listener.homework;


import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;

import java.util.ArrayList;
import java.util.List;

public final class Messages {

    private Messages() {throw new UnsupportedOperationException(); }

    // todo: 4. Сделать Listener для ведения истории: старое сообщение - новое (подумайте, как сделать, чтобы сообщения не портились)
    public static Message copyOf(Message message) {
        List<String> data = new ArrayList<>();
        ObjectForMessage field13 = message.getField13();
        if (field13 != null && field13.getData()!=null) {
            data = field13.getData();
        }

        ObjectForMessage objectForMessage = new ObjectForMessage();
        objectForMessage.setData(new ArrayList<>(data));

        return new Message.Builder(message.getId())
                       .field1(message.getField1())
                       .field2(message.getField2())
                       .field3(message.getField3())
                       .field4(message.getField4())
                       .field5(message.getField5())
                       .field6(message.getField6())
                       .field7(message.getField7())
                       .field8(message.getField8())
                       .field9(message.getField9())
                       .field10(message.getField10())
                       .field11(message.getField11())
                       .field12(message.getField12())
                       .field13(objectForMessage)
                       .build()
                ;
    }
}
