package lovci.hra;

import org.bukkit.entity.Player;
import java.util.List;

public class StavHry {
    private Player bezec;
    private List<Player> lovci;
    private boolean hraJede = false;

    public void start() {
        hraJede = true;
    }

    public void stop() {
        hraJede = false;
    }

    public boolean jedeHra() {
        return hraJede;
    }

    public void setBezec(Player bezec, List<Player> lovci) {
        this.bezec = bezec;
        this.lovci = lovci;
    }

    public Player getBezec() {
        return bezec;
    }

    public List<Player> getLovci() {
        return lovci;
    }

    public void zpravaLovcum(String zprava, boolean bold) {
        lovci.forEach(lovec -> lovec.sendTitle(bold ? zprava : " ", bold ? " " : zprava, 0, 100, 0));
    }

    public void zpravaBezci(String zprava, boolean bold) {
        bezec.sendTitle(bold ? zprava : " ", bold ? " " : zprava, 0, 100, 0);
    }

}
