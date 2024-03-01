package lovci.hra;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PruchodPortalem implements Listener {
    private final StavHry stavHry;

    public PruchodPortalem(StavHry stavHry) {
        this.stavHry = stavHry;
    }

    @EventHandler
    public void projdiPortalem(PlayerTeleportEvent event) {
        if (!stavHry.jedeHra()) return;
        PlayerTeleportEvent.TeleportCause typUdalosti = event.getCause();
        if (typUdalosti == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL && event.getPlayer().equals(stavHry.getBezec())) {
            stavHry.stop();
            stavHry.zpravaBezci("Utekls jim, vyhrals. Konec hry.");
            stavHry.zpravaLovcum("Zdrhl vam, prohrali jste. Konec hry.");
        }
    }
}
