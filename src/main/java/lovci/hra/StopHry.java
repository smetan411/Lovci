package lovci.hra;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class StopHry implements CommandExecutor {
    private final StavHry stavHry;

    public StopHry(StavHry stavHry) {
        this.stavHry = stavHry;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        stavHry.stop();
        stavHry.zpravaBezci("Hra byla zakoncena.");
        stavHry.zpravaLovcum("Hra byla zakoncena.");
        stavHry.getLovci().forEach(lovec -> lovec.getInventory().clear());
        stavHry.getBezec().getInventory().clear();
        return true;
    }
}
