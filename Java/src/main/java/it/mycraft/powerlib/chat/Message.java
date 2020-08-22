package it.mycraft.powerlib.chat;

import it.mycraft.powerlib.utils.ColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {

    private String message;
    private List<String> messages;

    public Message() {
        this.message = "";
        this.messages = new ArrayList<>();
    }

    public Message(String message) {
        this.message = ColorAPI.color(message);
        this.messages = new ArrayList<>();
    }

    public Message(String... messages) {
        this.message = "";
        this.messages = new ArrayList<>(ColorAPI.color(Arrays.asList(messages)));
    }

    public Message(List<String> messages) {
        this.message = "";
        this.messages = new ArrayList<>(ColorAPI.color(messages));
    }

    public Message addPlaceHolder(String placeholder, Object value) {
        message = message.replaceAll(placeholder, value.toString());

        List<String> newMessages = new ArrayList<>();
        messages.forEach((s) -> newMessages.add(s.replaceAll(placeholder, value.toString())));

        messages = newMessages;

        return this;
    }

    public void set(String message) {
        this.message = message;
    }

    public void set(List<String> messages) {
        this.messages = messages;
    }

    @Nullable
    public String getText(){
        return message;
    }

    @Nullable
    public List<String> getTextList(){
        return messages;
    }

    public void send(CommandSender commandSender) {
        if (messages.isEmpty())
            commandSender.sendMessage(message);
        else
            messages.forEach(commandSender::sendMessage);

        reset();
    }

    public void broadcast(String permission) {
        if(!permission.equalsIgnoreCase("")) {
            if (messages.isEmpty())
                Bukkit.broadcastMessage(message);
            else
                messages.forEach(Bukkit::broadcastMessage);
        }
        else {
            if (messages.isEmpty())
                Bukkit.broadcast(message, permission);
            else
                messages.forEach((m) -> Bukkit.broadcast(m, permission));
        }
    }

    public void color() {
        if(messages.isEmpty()) {
            this.message = ColorAPI.color(message);
        }
        else this.messages = ColorAPI.color(messages);
    }

    public void decolor() {
        if(messages.isEmpty()) {
            this.message = ColorAPI.decolor(message);
        }
        else this.messages = ColorAPI.decolor(messages);
    }

    public void broadcast() {
        broadcast("");
    }


    private void reset() {
        messages = new ArrayList<>();
        message = null;
    }
}