package it.mycraft.powerlib.bungee.chat;

import it.mycraft.powerlib.common.utils.ColorAPI;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
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
        message = message.replace(placeholder, value.toString());

        List<String> newMessages = new ArrayList<>();
        messages.forEach((s) -> newMessages.add(s.replace(placeholder, value.toString())));

        messages = newMessages;

        return this;
    }

    public Message set(String message) {
        this.message = ColorAPI.color(message);
        return this;
    }

    public Message set(List<String> messages) {
        this.messages = ColorAPI.color(messages);
        return this;
    }

    public Message set(String... messages) {
        this.messages = ColorAPI.color(Arrays.asList(messages));
        return this;
    }

    @Nullable
    public String getText() {
        return message;
    }

    @Nullable
    public List<String> getTextList() {
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
        if (!permission.equalsIgnoreCase("")) {
            if (messages.isEmpty())
                ProxyServer.getInstance().broadcast(message);
            else
                messages.forEach(ProxyServer.getInstance()::broadcast);
        } else {
            for(ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                if(p.hasPermission(permission)) {
                    send(p);
                }
            }
        }

        reset();
    }

    public Message decolor() {
        if (messages.isEmpty()) {
            this.message = ColorAPI.decolor(message);
        } else this.messages = ColorAPI.decolor(messages);
        return this;
    }

    public void broadcast() {
        broadcast("");
    }

    private void reset() {
        messages = new ArrayList<>();
        message = null;
    }
}
