package nazario.nicosgraves.mixin;

import nazario.nicosgraves.util.ModGamerules;
import nazario.nicosgraves.util.ModTags;
//? <1.21.2 {
/*import nazario.nicosgraves.util.compat.TrinketsHelper;
import nazario.nicosgraves.util.compat.YYZsBackpackHelper;
import net.fabricmc.loader.api.FabricLoader;
*///?}
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
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

        if(player.getWorld() instanceof ServerWorld serverWorld && serverWorld.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) return;

        //? <1.21.2 {
        /*if (FabricLoader.getInstance().isModLoaded("yyzsbackpack") && YYZsBackpackHelper.needCompatibility() && player instanceof ServerPlayerEntity serverPlayer) {
            try {
                ItemStack backpackStack = YYZsBackpackHelper.save(serverPlayer);
                grimoire$dropAndDecrement(backpackStack, player);
            } catch (Exception ignored) {}
        }

        if (FabricLoader.getInstance().isModLoaded("trinkets")) {
            try {
                TrinketsHelper.findAllEquippedBy(player).forEach(stack -> grimoire$dropAndDecrement(stack, player));
            } catch (Exception ignored) {}
        }
        *///?}

        player.getInventory().main.forEach(stack -> grimoire$dropAndDecrement(stack, player));
        player.getInventory().offHand.forEach(stack -> grimoire$dropAndDecrement(stack, player));
        player.getInventory().armor.forEach(stack -> grimoire$dropAndDecrement(stack, player));

        ci.cancel();
    }

    //? >=1.21.1 {
    @Unique
    private void grimoire$dropAndDecrement(ItemStack stack, PlayerEntity player) {
        if(player.getWorld().isClient) return;

        if(!(stack.getItem().getRegistryEntry().isIn(ModTags.ItemTags.SOULBOUND_ITEMS)) && player.getWorld() instanceof ServerWorld serverWorld) {
            if(!serverWorld.getGameRules().getBoolean(ModGamerules.SPAWN_PLAYER_GRAVES) && !EnchantmentHelper.getEnchantments(stack).getEnchantments().contains(Enchantments.VANISHING_CURSE)) player.dropStack(serverWorld, stack.copy());
            stack.setCount(0);
        }
    }
    //?} else {
    /*@Unique
    private void grimoire$dropAndDecrement(ItemStack stack, PlayerEntity player) {
        if(player.getWorld().isClient) return;

        if(!(stack.getItem().getRegistryEntry().isIn(ModTags.ItemTags.SOULBOUND_ITEMS)) && player.getWorld() instanceof ServerWorld serverWorld) {
            if(!serverWorld.getGameRules().getBoolean(ModGamerules.SPAWN_PLAYER_GRAVES) && !EnchantmentHelper.getEnchantments(stack).getEnchantments().contains(Enchantments.VANISHING_CURSE)) player.dropStack(stack.copy());
            stack.setCount(0);
        }
    }
    *///?}
}
@Mixin(ServerPlayerEntity.class)
abstract class ServerPlayerEntityMixin {
    @Inject(method = "copyFrom", at = @At(value = "TAIL"))
    private void grimoire$copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        if(((ServerWorld)player.getWorld()).getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) return;
        player.getInventory().clone(oldPlayer.getInventory());
    }
}