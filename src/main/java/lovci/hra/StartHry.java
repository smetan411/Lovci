package lovci.hra;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class StartHry implements CommandExecutor {
    private final Server server;
    private final StavHry stavHry;

    public StartHry(Server server, StavHry stavHry) {
        this.server = server;
        this.stavHry = stavHry;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if (stavHry.jedeHra()) {
            commandSender.sendMessage("Hra jede, nemuzes nastartovat novou, dokud hra neskonci.");
            return true;
        }
        if (strings.length != 1) {
            commandSender.sendMessage("Spatne, musis zadat: start jmenoBezce.");
            return true;
        }
        String jmenoBezce = strings[0];
        Player bezec = commandSender.getServer().getPlayer(jmenoBezce);
        if (bezec == null) {
            commandSender.sendMessage("Bezec jmenem " + jmenoBezce + " neexistuje.");
            return true;
        }
        List<Player> lovci = new ArrayList<>(commandSender.getServer().getOnlinePlayers());
        lovci.remove(bezec);
        stavHry.setBezec(bezec, lovci);

        stavHry.zpravaBezci("Jsi Bezec, tak prchej!");
        stavHry.zpravaLovcum("Jsi lovec, tak chyt bezce!");

        Location poziceStartu = ((Player) commandSender).getLocation();

        bezec.teleport(poziceStartu);
        bezec.setGameMode(GameMode.SURVIVAL);
        bezec.getInventory().clear();
        bezec.getInventory().addItem(new ItemStack(Material.BREAD, 16));


        lovci.forEach(lovec -> {
            lovec.teleport(poziceStartu);
            lovec.setGameMode(GameMode.SURVIVAL);
            lovec.getInventory().clear();
            lovec.getInventory().addItem(new ItemStack(Material.BREAD, 16));

            lovec.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 200, 255, true, false, false));
            lovec.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 200, 255, true, false, false));
            lovec.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 200, 155, true, false, false));
            lovec.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 200, 155, true, false, false));

        });

        new Kompas(stavHry).dejLovcumKompas();
        stavHry.start();
        return true;
    }
}
