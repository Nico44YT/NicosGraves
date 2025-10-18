package nazario.nicosgraves.api;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface SoulboundItem {
    boolean isRetained(ItemStack stack, PlayerEntity player, World world);
}
