package it.mycraft.powerlibexample.common;

import it.mycraft.powerlib.common.chat.Message;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class ExampleMessage {

    public void isOp(Player player) {
        if (player.isOp())
            new Message("&4%player is a {#ffffff}Server Operator") // {#ffffff} = white!
                    .hex("{#", "}") // hex 1.16+
                    .addPlaceHolder("%player", player.getName())
                    .send(player);
    }

    public void sendMessage(Player player, FileConfiguration fileConfiguration) {
        new Message(fileConfiguration.getString("This.is.a.path"))
                .addPlaceHolder("%player", player.getName())
                .send(player);
    }

    public void sendListMessages(ConsoleCommandSender consoleCommandSender) {
        new Message("&6Hi Console", "&eHow are you?")
                .send(consoleCommandSender);
    }

    public String getText() {
        return new Message("&4Hi %player")
                .addPlaceHolder("%player", "Pompieregay")
                .getText();
    }

    public void textList() {
        List<String> s = new Message(getText(), "I'm fine")
                .getTextList();
        s.forEach(System.out::println);
    }

    public void setText() {
        System.out.println(
                new Message()
                        .set("&5Hello Shrek, today is: %day")
                        .addPlaceHolder("%day", "Somebody once told me the world is gonna roll me")
                        .getText());
    }
}