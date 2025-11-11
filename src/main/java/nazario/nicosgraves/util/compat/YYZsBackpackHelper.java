package nazario.nicosgraves.util.compat;

import com.yyz.yyzsbackpack.Backpack;
import com.yyz.yyzsbackpack.BackpackPlatform;
import com.yyz.yyzsbackpack.item.BackpackItem;
import com.yyz.yyzsbackpack.util.BackpackStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class YYZsBackpackHelper {

    public static ItemStack save(ServerPlayerEntity player) {
        ItemStack equippedBackpack = BackpackPlatform.getEquipped(player).copyAndEmpty();
        if (equippedBackpack.getItem() instanceof BackpackItem backpackItem) {
            BackpackStorage.saveBackpackContents(player.getInventory(), equippedBackpack, BackpackPlatform.getEmptyRule(player));

            int numSlots = backpackItem.getBackpackType().getSize();
            for(int i = 0; i < numSlots; ++i) {
                int slotIndex = 36 + i;
                ItemStack stack = player.getInventory().getStack(slotIndex);
                if (!stack.isEmpty()) {
                    player.getInventory().setStack(slotIndex, ItemStack.EMPTY);
                }
            }
        }

        return equippedBackpack;
    }

    public static boolean needCompatibility() {
        return !Backpack.getConfig().use_dedicated_slot;
    }
}