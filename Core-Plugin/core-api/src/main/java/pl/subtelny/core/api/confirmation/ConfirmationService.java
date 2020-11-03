package pl.subtelny.core.api.confirmation;

import org.bukkit.entity.Player;

public interface ConfirmationService {

    void confirm(Player player, ConfirmContextId confirmContextId);

    void reject(Player player, ConfirmContextId confirmContextId);

    void makeConfirmation(ConfirmationRequest request);

}
