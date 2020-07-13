package pl.subtelny.gui.crate;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.gui.api.crate.inventory.CrateInventory;
import pl.subtelny.gui.api.crate.model.Crate;
import pl.subtelny.gui.api.crate.repository.CrateInventoryCreator;
import pl.subtelny.gui.crate.settings.CrateSettings;
import pl.subtelny.gui.messages.CrateMessages;
import pl.subtelny.utilities.ColorUtil;
import pl.subtelny.utilities.condition.Condition;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CrateService {

    private final CrateSettings settings;

    private final CrateMessages messages;

    private final CrateInventoryCreator inventoryCreator;

    @Autowired
    public CrateService(CrateSettings settings, CrateMessages messages, CrateInventoryCreator inventoryCreator) {
        this.settings = settings;
        this.messages = messages;
        this.inventoryCreator = inventoryCreator;
    }

    public Optional<ItemStack> getNotSatisfiedItemStack(List<Condition> conditions) {
        return settings.getNotSatisfiedConditionsItemPattern().map(itemStack -> {
            ItemStack item = itemStack.clone();
            ItemMeta itemMeta = item.getItemMeta();
            List<String> lore = itemMeta.getLore();
            if (lore != null) {
                List<String> rawLore = lore.stream()
                        .flatMap(loreLine -> {
                            if (loreLine.contains("{CONDITION}")) {
                                String pattern = loreLine.replace("{CONDITION}", "%s");
                                return prepareConditionMessages(conditions, pattern).stream();
                            }
                            return Stream.of(loreLine);
                        }).collect(Collectors.toList());
                itemMeta.setLore(ColorUtil.color(rawLore));
            }
            item.setItemMeta(itemMeta);
            return item;
        });
    }

    private List<String> prepareConditionMessages(List<Condition> conditions, String pattern) {
        return conditions.stream()
                .map(condition -> {
                    String rawMessage = messages.getRawMessage(condition.getMessageKey().getKey());
                    String message = String.format(rawMessage, condition.getMessageKey().getObjects());
                    return String.format(pattern, message);
                }).collect(Collectors.toList());
    }

    public CrateInventory getInventoryForCrate(Player player, Crate crate) {
        return inventoryCreator.createInv(player, crate);
    }

}
