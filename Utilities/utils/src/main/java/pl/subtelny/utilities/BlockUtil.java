package pl.subtelny.utilities;

import org.bukkit.block.Block;
import org.bukkit.block.data.type.Switch;
import org.bukkit.material.Openable;
import org.bukkit.material.PressureSensor;

public final class BlockUtil {

    public static boolean isOpenable(Block block) {
        return block.getState().getData() instanceof Openable;
    }

    public static boolean isPressurable(Block block) {
        return block.getState().getData() instanceof PressureSensor;
    }

    public static boolean isSwitchable(Block block) {
        return block.getState().getData() instanceof Switch;
    }

}
