package it.mycraft.powerlib.chat;

import it.mycraft.powerlib.utils.ColorAPI;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {

    private String message;
    private List<String> messages;

    public Message(String message) {
        this.message = ColorAPI.color(message);
    }

    public Message(String... messages) {
        this.messages = new ArrayList<>(ColorAPI.color(Arrays.asList(messages)));
    }

    public Message(List<String> messages) {
        this.messages = new ArrayList<>(ColorAPI.color(messages));
    }

    public Message addPlaceHolder(String placeholder, Object value) {
        message = message.replaceAll(placeholder, value.toString());

        List<String> newMessages = new ArrayList<>();
        messages.forEach((s) -> newMessages.add(s.replaceAll(placeholder, value.toString())));

        messages = newMessages;

        return this;
    }

    public void send(CommandSender commandSender) {
        if (messages.isEmpty())
            commandSender.sendMessage(message);
        else
            messages.forEach(commandSender::sendMessage);

        reset();
    }

    private void reset() {
        messages = new ArrayList<>();
        message = null;
    }
}