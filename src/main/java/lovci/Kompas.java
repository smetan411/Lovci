package lovci;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Kompas {

    private final StavHry stavHry;

    public Kompas(StavHry stavHry) {
        this.stavHry = stavHry;
    }

    public ItemStack vyrobKompas() {
        ItemStack kompas = new ItemStack(Material.COMPASS);
        ItemMeta vlastnosti = kompas.getItemMeta();
        vlastnosti.setDisplayName(ChatColor.BLUE + "kompasLovce");
        kompas.setItemMeta(vlastnosti);
        return kompas;
    }

    private void smazLovciKompas(Player lovec) {
        List<ItemStack> kompasy = new ArrayList<>();
        for (ItemStack polozka : lovec.getInventory()) {
            if (polozka.getItemMeta().getDisplayName().equals("kompasLovce")) {
                kompasy.add(polozka);
            }
        }
        kompasy.forEach(kompasKeSmazani -> lovec.getInventory().removeItem(kompasKeSmazani));
    }


    public void dejLovcumKompas() {
        for (Player lovec : stavHry.getLovci()) {
            smazLovciKompas(lovec);
            lovec.getInventory().setItemInMainHand(vyrobKompas());
        }
        Timer casovac = new Timer();
        casovac.schedule(new SmerovaniKompasu(), 1000, 1000);
    }

    private class SmerovaniKompasu extends TimerTask {
        @Override
        public void run() {
            for (Player lovec : stavHry.getLovci()) {
                lovec.setCompassTarget(stavHry.getBezec().getLocation());
            }
        }
    }
}
