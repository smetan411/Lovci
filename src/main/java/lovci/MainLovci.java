package lovci;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public final class MainLovci extends JavaPlugin implements Listener {
    private StavHry stavHry = new StavHry();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new SmrtBezce(stavHry), this);
        getServer().getPluginManager().registerEvents(new PruchodPortalem(stavHry), this);
        getServer().getPluginManager().registerEvents(new OziveniLovce(stavHry), this);
        getServer().getPluginManager().registerEvents(new VyhozeniKompasu(stavHry), this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        switch (command.getName()) {
            case "stop":
                stavHry.stop();
                stavHry.zpravaBezci("Hra byla ukoncena.");
                stavHry.zpravaLovcum("Hra byla ukoncena.");
                stavHry.getLovci().forEach(lovec -> lovec.getInventory().clear());
                stavHry.getBezec().getInventory().clear();
                break;

            case "start":
                if (stavHry.jedeHra()) {
                    sender.sendMessage("Hra jede, nemuzes nastartovat novou, dokud hra neskonci.");
                    return true;
                }
                if (args.length != 1) {
                    sender.sendMessage("Spatne, musis zadat: start jmenoBezce.");
                    return true;
                }
                String jmenoBezce = args[0];
                Player bezec = sender.getServer().getPlayer(jmenoBezce);
                if (bezec == null) {
                    sender.sendMessage("Bezec jmenem " + jmenoBezce + " neexistuje.");
                    return true;
                }
                List<Player> lovci = new ArrayList<>(sender.getServer().getOnlinePlayers());
                lovci.remove(bezec);
                stavHry.setBezec(bezec, lovci);

                stavHry.zpravaBezci("Jsi Bezec, tak prchej!");
                stavHry.zpravaLovcum("Jsi lovec, tak chyt bezce!");

                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    getServer().getOnlinePlayers().forEach(allPlayers -> {
                        allPlayers.teleport(player.getLocation());
                        stavHry.getLovci().forEach(lovec -> lovec.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 720, 255, true, false, false)));
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
            break;
        }
        return false;
    }
}

