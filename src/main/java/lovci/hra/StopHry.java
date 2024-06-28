package lovci.hra;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class StopHry implements CommandExecutor {
    private final StavHry stavHry;

    public StopHry(StavHry stavHry) {
        this.stavHry = stavHry;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!stavHry.jedeHra()) {
            commandSender.sendMessage("Hra se dá stopnout, jen když jede. Teď nejede.");
            return true;
        }
        stavHry.stop();


        stavHry.zpravaBezci("Hra byla zakoncena.");
        stavHry.zpravaLovcum("Hra byla zakoncena.");

        stavHry.getLovci().forEach(
                lovec -> {
                    lovec.getInventory().clear();
//                    if(lovec.getActivePotionEffects() != null){
//                        lovec.clearActivePotionEffects(); // zde je chyba
//                    }
               });

                stavHry.getBezec().getInventory().clear();

        return true;
    }
}
