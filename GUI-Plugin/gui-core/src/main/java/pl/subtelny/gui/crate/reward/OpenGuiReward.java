package pl.subtelny.gui.crate.reward;

import org.bukkit.entity.Player;
import pl.subtelny.gui.api.crate.model.CrateId;
import pl.subtelny.gui.api.crate.session.PlayerCrateSessionService;
import pl.subtelny.utilities.reward.Reward;

public class OpenGuiReward implements Reward {

    private final CrateId crateId;

    private final PlayerCrateSessionService sessionService;

    public OpenGuiReward(CrateId crateId, PlayerCrateSessionService sessionService) {
        this.crateId = crateId;
        this.sessionService = sessionService;
    }

    @Override
    public void admitReward(Player player) {
        sessionService.openSession(player, crateId);
    }
}
