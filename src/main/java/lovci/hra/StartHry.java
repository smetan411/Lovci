package lovci.hra;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StartHry implements CommandExecutor {
    private final Server server;

    public StartHry(Server server) {
        this.server = server;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        StavHry stavHry = new StavHry();
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

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            server.getOnlinePlayers().forEach(allPlayers -> {
                allPlayers.teleport(player.getLocation());
                stavHry.getLovci().forEach(lovec -> lovec.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 720, 255, true, false, false)));
                stavHry.getLovci().forEach(lovec -> lovec.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 720, 255, true, false, false)));
                stavHry.getLovci().forEach(lovec -> lovec.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 720, 155, true, false, false)));
                stavHry.getLovci().forEach(lovec -> lovec.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 720, 155, true, false, false)));
                allPlayers.getInventory().clear();
            });
        }

        new Kompas(stavHry).dejLovcumKompas();
        stavHry.getLovci().forEach(lovec -> lovec.getInventory().addItem(new ItemStack(Material.BREAD, 16)));
        stavHry.getBezec().getInventory().addItem(new ItemStack(Material.BREAD, 16));
        stavHry.start();
        return true;
    }
}
