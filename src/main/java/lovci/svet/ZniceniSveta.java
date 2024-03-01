package lovci.svet;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class ZniceniSveta implements CommandExecutor {

    private final GameWorldTeleport teleport;
    private  final ManHuntWorldCreator worldCreator;


    public ZniceniSveta(GameWorldTeleport teleport, ManHuntWorldCreator worldCreator) {
        this.teleport = teleport;
        this.worldCreator = worldCreator;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        teleport.teleportFromGame();
        try{
            worldCreator.removeWorld();
        } catch (IOException e){
            commandSender.sendMessage("Unable to delete world directory.");
            throw  new RuntimeException(e);
        }
        return true;
    }
}
