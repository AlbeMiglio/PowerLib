package it.mycraft.powerlib.common.chat;

import it.mycraft.powerlib.common.utils.ColorAPI;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.mycraft.powerlib.common.utils.ServerAPI.*;

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

    public void send(Object commandSender) {
        if (isUsingBukkit()) {
            if (messages.isEmpty())
                ((org.bukkit.command.CommandSender) commandSender).sendMessage(message);
            else
                messages.forEach(((org.bukkit.command.CommandSender) commandSender)::sendMessage);
        } else if (isStrictlyUsingBungee()) {
            if (messages.isEmpty())
                ((net.md_5.bungee.api.CommandSender) commandSender).sendMessage(message);
            else
                messages.forEach(((net.md_5.bungee.api.CommandSender) commandSender)::sendMessage);
        } else if (isUsingVelocity()) {
            if (messages.isEmpty())
                ((com.velocitypowered.api.command.CommandSource) commandSender)
                        .sendMessage(net.kyori.adventure.text.Component.text(message));
            else
                messages.forEach((m) ->
                        ((com.velocitypowered.api.command.CommandSource) commandSender)
                                .sendMessage(net.kyori.adventure.text.Component.text(m)));
        }
    }

    public void broadcast(String permission) {
        if (isUsingBukkit()) {
            for (org.bukkit.entity.Player p : org.bukkit.Bukkit.getOnlinePlayers()) {
                if (permission.equals("") || p.hasPermission(permission)) {
                    send(p);
                }
            }
        } else if (isStrictlyUsingBungee()) {
            for (net.md_5.bungee.api.connection.ProxiedPlayer p
                    : net.md_5.bungee.api.ProxyServer.getInstance().getPlayers()) {
                if (permission.equals("") || p.hasPermission(permission)) {
                    send(p);
                }
            }
        } else if (isUsingVelocity()) {
            for (com.velocitypowered.api.proxy.Player p
                    : it.mycraft.powerlib.velocity.PowerLib.getInstance().getProxy().getAllPlayers()) {
                if (permission.equals("") || p.hasPermission(permission)) {
                    send(p);
                }
            }
        }
    }

    public void broadcast() {
        broadcast("");
    }

    public Message color() { // no need by default
        if (messages.isEmpty()) {
            this.message = ColorAPI.color(message);
        } else this.messages = ColorAPI.color(messages);
        return this;
    }

    public Message decolor() {
        if (messages.isEmpty()) {
            this.message = ColorAPI.decolor(message);
        } else this.messages = ColorAPI.decolor(messages);
        return this;
    }

    private void reset() {
        messages = new ArrayList<>();
        message = null;
    }
}
