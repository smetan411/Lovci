package lovci;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class OziveniLovce implements Listener {

    private final StavHry stavHry;

    public OziveniLovce(StavHry stavHry) {
        this.stavHry = stavHry;
    }

    @EventHandler
    public void lovecSeOzivil(PlayerRespawnEvent oziveni) {
        if (!stavHry.jedeHra()) return;
        Player player = oziveni.getPlayer();
        if (!(player.equals(stavHry.getBezec()))) {
            player.getInventory().setItemInMainHand(new Kompas(stavHry).vyrobKompas());
        }
    }
}
