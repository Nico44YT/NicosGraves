package nazario.nicosgraves.mixin;

//? >=1.21.9 {
/*import nazario.nicosgraves.util.ModGamerules;
import nazario.nicosgraves.util.ModTags;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
*///?} else >=1.21.5 {
/*import nazario.nicosgraves.util.ModGamerules;
import nazario.nicosgraves.util.ModTags;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
*///?} else >=1.21.2 {
/*import nazario.nicosgraves.util.ModGamerules;
import nazario.nicosgraves.util.ModTags;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
*///?} else {
import nazario.nicosgraves.util.ModGamerules;
import nazario.nicosgraves.util.ModTags;
import nazario.nicosgraves.util.compat.TrinketsHelper;
import nazario.nicosgraves.util.compat.YYZsBackpackHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//?}

@Mixin(value = PlayerEntity.class, priority = 1500)
public abstract class PlayerEntityMixin {
    @Inject(method = "dropInventory", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;vanishCursedItems()V"), cancellable = true)
    private void nicos_graves$preDropInventory(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        //? >=1.21.9 {
        /*World entityWorld = player.getEntityWorld();
        *///?} else {
        World entityWorld = player.getWorld();
        //?}

        if(entityWorld instanceof ServerWorld serverWorld && serverWorld.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) return;

        //? <1.21.2 {
        if (FabricLoader.getInstance().isModLoaded("yyzsbackpack") && YYZsBackpackHelper.needCompatibility() && player instanceof ServerPlayerEntity serverPlayer) {
            try {
                ItemStack backpackStack = YYZsBackpackHelper.save(serverPlayer);
                nicos_graves$dropAndDecrement(backpackStack, player);
            } catch (Exception ignored) {}
        }

        if (FabricLoader.getInstance().isModLoaded("trinkets")) {
            try {
                TrinketsHelper.findAllEquippedBy(player).forEach(stack -> nicos_graves$dropAndDecrement(stack, player));
            } catch (Exception ignored) {}
        }
        //?}

        //? >=1.21.5 {
        /*player.getInventory().main.forEach(stack -> nicos_graves$dropAndDecrement(stack, player));
        player.getInventory().equipment.map.forEach((slot, stack) -> nicos_graves$dropAndDecrement(stack, player));
        *///?} else {
        player.getInventory().main.forEach(stack -> nicos_graves$dropAndDecrement(stack, player));
        player.getInventory().offHand.forEach(stack -> nicos_graves$dropAndDecrement(stack, player));
        player.getInventory().armor.forEach(stack -> nicos_graves$dropAndDecrement(stack, player));
        //?}

        ci.cancel();
    }

    //? >=1.21.1 {
    /*@Unique
    private void nicos_graves$dropAndDecrement(ItemStack stack, PlayerEntity player) {
        //? >=1.21.9 {
        /^World world = player.getEntityWorld();
        ^///?} else {
        World world = player.getWorld();
        //?}

        if(world.isClient()) return;

        if(!(stack.getItem().getRegistryEntry().isIn(ModTags.ItemTags.SOULBOUND_ITEMS)) && world instanceof ServerWorld serverWorld) {
            if(!serverWorld.getGameRules().getBoolean(ModGamerules.SPAWN_PLAYER_GRAVES) && !EnchantmentHelper.getEnchantments(stack).getEnchantments().contains(Enchantments.VANISHING_CURSE)) player.dropStack(serverWorld, stack.copy());
            stack.setCount(0);
        }
    }
    *///?} else {
    @Unique
    private void nicos_graves$dropAndDecrement(ItemStack stack, PlayerEntity player) {
        if(player.getWorld().isClient) return;

        if(!(stack.getItem().getRegistryEntry().isIn(ModTags.ItemTags.SOULBOUND_ITEMS)) && player.getWorld() instanceof ServerWorld serverWorld) {
            if(!serverWorld.getGameRules().getBoolean(ModGamerules.SPAWN_PLAYER_GRAVES) && !EnchantmentHelper.getEnchantments(stack).getEnchantments().contains(Enchantments.VANISHING_CURSE)) player.dropStack(stack.copy());
            stack.setCount(0);
        }
    }
    //?}
}
@Mixin(ServerPlayerEntity.class)
abstract class ServerPlayerEntityMixin {
    @Inject(method = "copyFrom", at = @At(value = "TAIL"))
    private void nicos_graves$copyFrom(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        ServerPlayerEntity player = (ServerPlayerEntity)(Object)this;
        //? >=1.21.9 {
        /*ServerWorld world = (ServerWorld) player.getEntityWorld();
        *///?} else {
        ServerWorld world = (ServerWorld) player.getWorld();
        //?}

        if(world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY)) return;
        player.getInventory().clone(oldPlayer.getInventory());
    }
}