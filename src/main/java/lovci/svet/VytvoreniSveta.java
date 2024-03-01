package lovci.svet;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class VytvoreniSveta implements CommandExecutor {
    private  final ManHuntWorldCreator manHuntWorldCreator;
    private final GameWorldTeleport teleport;

   public VytvoreniSveta(ManHuntWorldCreator manHungWorldCreator, GameWorldTeleport teleport) {
        this.manHuntWorldCreator = manHungWorldCreator;
        this.teleport = teleport;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        try{
            if(strings.length >1){
                throw  new UserException("Command musi mit 1 parametr / velikost sveta");
            }
            //svet
            var velikostSveta = Integer.parseInt(strings[0]);
            if(velikostSveta > 6000) throw  new UserException("Svet nesmi byt vetsi nez 6000");
            manHuntWorldCreator.createWorld(velikostSveta);
            teleport.teleportToGame();
        } catch (NumberFormatException e){
            throw new UserException("Velilkost sveta(parameter 1) musi byt cislo.");

        }
        return true;
    }
}
