package lovci;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

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

                stavHry.getLovci().forEach(lovec -> lovec.getInventory().clear());
                stavHry.getBezec().getInventory().clear();

                stavHry.zpravaBezci("Jsi Bezec, tak prchej!");
                stavHry.zpravaLovcum("Jsi lovec, tak chyt bezce!");

                new Kompas(stavHry).dejLovcumKompas();
                stavHry.start();
                break;
        }
        return false;
    }
}

