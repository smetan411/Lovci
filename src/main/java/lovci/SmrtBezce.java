package lovci;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class SmrtBezce implements Listener {

    private final StavHry stavHry;

    public SmrtBezce(StavHry stavHry) {
        this.stavHry = stavHry;
    }

    @EventHandler
    public void bezecZemrel(PlayerDeathEvent smrtBezce){
        Player player = smrtBezce.getEntity();
        if(player.getName().equals(stavHry.getBezec().getName()) && stavHry.jedeHra()){
            stavHry.zpravaBezci("Zabili te, konec hry.");
            stavHry.zpravaLovcum("Vyhral jsi, bezec je mrtvy.");
            stavHry.stop();
        }
    }

}
