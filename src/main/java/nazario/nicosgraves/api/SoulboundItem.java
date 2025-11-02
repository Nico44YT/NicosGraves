package nazario.nicosgraves.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface SoulboundItem {

    /**
     *
     * @param stack
     * @param player
     * @param world
     * @return true if the item is still in the players inventory after respawn, false if not.
     */
    boolean isRetained(ItemStack stack, PlayerEntity player, World world);
}
