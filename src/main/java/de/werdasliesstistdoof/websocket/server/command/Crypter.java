package de.werdasliesstistdoof.websocket.server.command;

import static de.werdasliesstistdoof.websocket.server.util.Bundel.getMessage;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class Crypter implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(strings.length == 0)
            sendFail(commandSender);
        else if(strings.length == 1)
            commandSender.sendMessage(getMessage("crypter.success.encode", de.werdasliesstistdoof.websocket.server.util.Crypter.encode(strings[0])));
        else if(strings.length == 2) {

            if(strings[0].equalsIgnoreCase("encrypt"))
                commandSender.sendMessage(getMessage("crypter.success.encode", de.werdasliesstistdoof.websocket.server.util.Crypter.encode(strings[1])));
            else if(strings[0].equalsIgnoreCase("decrypt")) {

                if(commandSender instanceof Player) {

                    if(commandSender.hasPermission("websocket.crypter.decrypt"))
                        commandSender.sendMessage(getMessage("crypter.success.decode", de.werdasliesstistdoof.websocket.server.util.Crypter.decode(strings[1])));
                    else
                        commandSender.sendMessage(getMessage("crypter.fail.permission"));

                } else
                    commandSender.sendMessage(getMessage("crypter.success.decode", de.werdasliesstistdoof.websocket.server.util.Crypter.decode(strings[1])));

            } else
                sendFail(commandSender);

        } else
            sendFail(commandSender);

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length == 1)
            return Arrays.asList(new String[]{"encrypt", "decrypt"});
        return null;
    }

    private void sendFail(CommandSender commandSender) {

        commandSender.sendMessage(getMessage("crypter.fail.parameter"));

    }

}
