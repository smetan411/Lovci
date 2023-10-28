package lovci;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

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
        kompas.addEnchantment(Enchantment.VANISHING_CURSE, 1); //z lovcu nebudou vypadavat kompasy
        return kompas;
    }

    private void smazLovciKompas(Player lovec) {
        List<ItemStack> kompasy = new ArrayList<>();
        List<ItemStack> allItems = new ArrayList<>(Arrays.asList(lovec.getInventory().getContents()));
        allItems.add(lovec.getInventory().getItemInMainHand());
        allItems.add(lovec.getInventory().getItemInOffHand());
        for (ItemStack polozka : allItems) {
            if (polozka == null) continue;
            var itemMeta = polozka.getItemMeta();
            if (itemMeta != null && "kompasLovce".equals(itemMeta.getDisplayName())) {
                kompasy.add(polozka);
            }
        }
        kompasy.forEach(kompasKeSmazani -> lovec.getInventory().remove(kompasKeSmazani));
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
            if (!stavHry.jedeHra()) return;
            for (Player lovec : stavHry.getLovci()) {
                lovec.setCompassTarget(stavHry.getBezec().getLocation());
            }
        }
    }
}
