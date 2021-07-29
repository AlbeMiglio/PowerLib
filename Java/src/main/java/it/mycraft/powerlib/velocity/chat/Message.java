package it.mycraft.powerlib.velocity.chat;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import it.mycraft.powerlib.common.utils.ColorAPI;
import it.mycraft.powerlib.velocity.PowerLib;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {

    private String message;
    private List<String> messages;
    private static PowerLib main;

    public Message() {
        main = PowerLib.getInstance();
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

    public void send(CommandSource commandSender) {
        if (messages.isEmpty())
            commandSender.sendMessage(Component.text(message));
        else
            messages.forEach((m) -> commandSender.sendMessage(Component.text(m)));
    }

    public void broadcast(String permission) {
        if (!permission.equalsIgnoreCase("")) {
            if (messages.isEmpty())
                 main.getProxy().broadcast((net.kyori.text.Component) Component.text(message));
            else
                messages.forEach((m) -> main.getProxy().broadcast((net.kyori.text.Component) Component.text(m)));
        } else {
            for(Player p : main.getProxy().getAllPlayers()) {
                if(p.hasPermission(permission)) {
                    send(p);
                }
            }
        }
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

