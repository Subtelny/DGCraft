package pl.subtelny.core.cuboidselector.listener;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.configuration.CoreMessages;
import pl.subtelny.core.cuboidselector.CuboidSelectService;
import pl.subtelny.core.cuboidselector.CuboidSelectSession;

@Component
public class PlayerSelectCuboidListener implements Listener {

    private static final String LOCATION_POS1_PATH = "pos1";

    private static final String LOCATION_POS2_PATH = "pos2";

    private final CuboidSelectService cuboidSelectService;

    private final CoreMessages messages;

    @Autowired
    public PlayerSelectCuboidListener(CuboidSelectService cuboidSelectService, CoreMessages messages) {
        this.cuboidSelectService = cuboidSelectService;
        this.messages = messages;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.isCancelled() || e.getHand() != EquipmentSlot.HAND || !isBlockAction(e.getAction())) {
            return;
        }
        Block clickedBlock = e.getClickedBlock();
        Player player = e.getPlayer();
        CuboidSelectSession cuboidSelect = cuboidSelectService.getCuboidSelect(player);
        if (cuboidSelect != null && clickedBlock != null) {
            e.setCancelled(true);
            Location location = clickedBlock.getLocation();
            Action action = e.getAction();
            setPosition(player, cuboidSelect, location, action == Action.RIGHT_CLICK_BLOCK);
        }
    }

    private boolean isBlockAction(Action action) {
        return action == Action.RIGHT_CLICK_BLOCK || action == Action.LEFT_CLICK_BLOCK;
    }

    private void setPosition(Player player, CuboidSelectSession selectSession, Location location, boolean rightClick) {
        if (rightClick) {
            selectSession.setPositionTwo(location);
            informLocationIsSet(player, location, LOCATION_POS2_PATH);
        } else {
            selectSession.setPositionOne(location);
            informLocationIsSet(player, location, LOCATION_POS1_PATH);
        }
        if (selectSession.isReady()) {
            selectSession.createCuboid();
        }
    }

    private void informLocationIsSet(Player player, Location location, String pos) {
        String locStr = String.format("x:%s, y:%s, z:%s", location.getBlockX(), location.getBlockY(), location.getBlockZ());
        messages.sendTo(player, "location_" + pos + "_set", locStr);
    }
}
