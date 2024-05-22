package lovci;

import lovci.hra.*;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MainLovci extends JavaPlugin implements Listener {
    private StavHry stavHry = new StavHry();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new SmrtBezce(stavHry), this);
        getServer().getPluginManager().registerEvents(new PruchodPortalem(stavHry), this);
        getServer().getPluginManager().registerEvents(new OziveniLovce(stavHry), this);
        getServer().getPluginManager().registerEvents(new VyhozeniKompasu(stavHry), this);

        getCommand("start").setExecutor(new StartHry(this.getServer(), stavHry));
        getCommand("stop").setExecutor(new StopHry(stavHry));
    }

}

