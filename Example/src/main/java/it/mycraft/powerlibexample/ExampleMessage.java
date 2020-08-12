package it.mycraft.powerlibexample;

import it.mycraft.powerlib.chat.Message;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class ExampleMessage {

    public void isOp(Player player) {
        if (player.isOp())
            new Message("&4%player is a Server Operator")
                    .addPlaceHolder("%player", player.getName())
                    .send(player);
    }

    public void sendMessage(Player player, FileConfiguration fileConfiguration) {
        new Message(fileConfiguration.getString("This.is.a.path"))
                .addPlaceHolder("%player", player.getName())
                .send(player);
    }

    public void sendListMessages(ConsoleCommandSender consoleCommandSender) {
        new Message("&6Hi console", "&eHow are you?")
                .send(consoleCommandSender);
    }

    public String getText(){
        return new Message("&4hi %player")
                .addPlaceHolder("%player", "Pompiere1")
                .getText();
    }

    public void textList(){
        List<String> s = new Message(getText() ,"I'm fine")
                .getTextList();
        s.forEach(System.out::println);
    }


}