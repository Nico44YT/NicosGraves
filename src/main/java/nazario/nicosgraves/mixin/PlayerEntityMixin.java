package nazario.nicosgraves.mixin;

import nazario.nicosgraves.util.ModGamerules;
import nazario.nicosgraves.util.ModTags;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerEntity.class, priority = 1500)
public abstract class PlayerEntityMixin {
    @Inject(method = "dropInventory", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;vanishCursedItems()V"), cancellable = true)
    private void grimoire$preDropInventory(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;

        if(player.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) return;

        player.inventory.main.forEach(stack -> grimoire$dropAndDecrement(stack, player));
        player.inventory.offHand.forEach(stack -> grimoire$dropAndDecrement(stack, player));
        player.inventory.armor.forEach(stack -> grimoire$dropAndDecrement(stack, player));

        ci.cancel();
    }

    @Unique
    private void grimoire$dropAndDecrement(ItemStack stack, PlayerEntity player) {
        if(player.world.isClient) return;

        if(!(ModTags.ItemTags.SOULBOUND_ITEMS.contains(stack.getItem()))) {
            if(!player.world.getGameRules().getBoolean(ModGamerules.SPAWN_PLAYER_GRAVES) && !EnchantmentHelper.hasVanishingCurse(stack)) player.dropStack(stack.copy());
            stack.setCount(0);
        }
    }
}
@Mixin(ServerPlayerEntity.class)
abstract class ServerPlayerEntityMixin {
    @Inject(method = "copyFrom", at = @At(value = "TAIL"))
    private void grimoire$copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        if(player.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) return;
        player.inventory.clone(oldPlayer.inventory);
    }
}