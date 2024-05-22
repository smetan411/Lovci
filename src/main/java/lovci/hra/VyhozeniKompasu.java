package lovci.hra;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class VyhozeniKompasu implements Listener {

    private final StavHry stavHry;

    public VyhozeniKompasu(StavHry stavHry) {
        this.stavHry = stavHry;
    }

    @EventHandler
    public void vyhodilKompas(PlayerDropItemEvent event) {
        if (!stavHry.jedeHra()) return;
        Player player = event.getPlayer();

        if ((!player.equals(stavHry.getBezec()))) {
            if (event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.BLUE + "kompasLovce")) {
                event.setCancelled(true);
            }
        }
    }
}
