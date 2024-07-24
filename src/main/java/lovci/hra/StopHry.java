package lovci.hra;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class StopHry implements CommandExecutor {
    private final StavHry stavHry;

    public StopHry(StavHry stavHry) {
        this.stavHry = stavHry;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        stavHry.stop();
        stavHry.zpravaBezci("Hra byla zakoncena.", true);
        stavHry.zpravaLovcum("Hra byla zakoncena.", true);
        stavHry.getLovci().forEach(lovec -> lovec.getInventory().clear());
        stavHry.getBezec().getInventory().clear();
        return true;
    }
}
