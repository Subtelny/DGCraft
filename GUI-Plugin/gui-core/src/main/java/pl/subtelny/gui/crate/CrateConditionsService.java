package pl.subtelny.gui.crate;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.gui.crate.settings.CrateSettings;
import pl.subtelny.gui.messages.CrateMessages;
import pl.subtelny.utilities.ColorUtil;
import pl.subtelny.utilities.condition.Condition;
import pl.subtelny.utilities.messages.MessageKey;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CrateConditionsService {

    private final CrateSettings settings;

    private final CrateMessages messages;

    @Autowired
    public CrateConditionsService(CrateSettings settings, CrateMessages messages) {
        this.settings = settings;
        this.messages = messages;
    }

    public void getNotSatisfiedConditions(Player player, List<Condition> notSatisfiedConditions) {
        notSatisfiedConditions.forEach(condition -> {
            MessageKey messageKey = condition.getMessageKey();
            messages.sendTo(player, messageKey.getKey(), messageKey.getObjects());
        });
    }

    public Optional<ItemStack> computeNotSatisfiedItemStack(List<Condition> conditions) {
        return settings.getNotSatisfiedConditionsItemPattern().map(itemStack -> {
            ItemStack item = itemStack.clone();
            ItemMeta itemMeta = item.getItemMeta();
            List<String> lore = itemMeta.getLore();
            if (lore != null) {
                List<String> rawLore = lore.stream()
                        .flatMap(loreLine -> computeLoreLine(conditions, loreLine))
                        .collect(Collectors.toList());
                itemMeta.setLore(ColorUtil.color(rawLore));
            }
            item.setItemMeta(itemMeta);
            return item;
        });
    }

    private Stream<? extends String> computeLoreLine(List<Condition> conditions, String loreLine) {
        if (loreLine.contains("{CONDITION}")) {
            String pattern = loreLine.replace("{CONDITION}", "%s");
            return prepareConditionMessages(conditions, pattern).stream();
        }
        return Stream.of(loreLine);
    }

    private List<String> prepareConditionMessages(List<Condition> conditions, String pattern) {
        return conditions.stream()
                .map(condition -> {
                    String rawMessage = messages.getRawMessage(condition.getMessageKey().getKey());
                    String message = String.format(rawMessage, condition.getMessageKey().getObjects());
                    return String.format(pattern, message);
                }).collect(Collectors.toList());
    }

}
