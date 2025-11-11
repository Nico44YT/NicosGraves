package nazario.nicosgraves.util.comp;

import com.yyz.yyzsbackpack.BackpackManager;
import com.yyz.yyzsbackpack.item.BackpackItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class YYZsBackpackHelper {

    public static final int BACKPACK_SLOT = 36;

    public static ItemStack save(ServerPlayerEntity player) {
        ItemStack equippedBackpack = player.getInventory().getStack(BACKPACK_SLOT).copy();
        player.getInventory().getStack(BACKPACK_SLOT).setCount(0);
        if (equippedBackpack.getItem() instanceof BackpackItem backpackItem) {
            BackpackManager.saveBackpackContents(player.getInventory(), equippedBackpack);

            int numSlots = backpackItem.getBackpackType().getColumns()*9;
            for(int i = 0; i < numSlots; ++i) {
                int slotIndex = BACKPACK_SLOT + i;
                ItemStack stack = player.getInventory().getStack(slotIndex);
                if (!stack.isEmpty()) {
                    player.getInventory().setStack(slotIndex, ItemStack.EMPTY);
                }
            }
        }

        return equippedBackpack;
    }
}