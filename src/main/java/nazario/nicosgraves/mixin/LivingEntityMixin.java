package nazario.nicosgraves.mixin;

import nazario.nicosgraves.entity.ModEntities;
import nazario.nicosgraves.entity.custom.PlayerGraveEntity;
import nazario.nicosgraves.util.ModGamerules;
//? <1.21.2 {
/*import nazario.nicosgraves.util.compat.TrinketsHelper;
import nazario.nicosgraves.util.compat.YYZsBackpackHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
*///?} else {
import net.minecraft.server.world.ServerWorld;
//?}
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntity.class, priority = 1500)
public abstract class LivingEntityMixin {
    //? >=1.21.2 {
    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;onDeath(Lnet/minecraft/entity/damage/DamageSource;)V"), cancellable = true)
    private void grimoire$onDeathDamage(ServerWorld world, DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity thisEntity = (LivingEntity) (Object) this;

        if (thisEntity instanceof PlayerEntity victimPlayer && victimPlayer.getWorld() instanceof ServerWorld serverWorld) {

            if (victimPlayer.getWorld() == null) return;
            if (!serverWorld.getGameRules().getBoolean(GameRules.KEEP_INVENTORY) && serverWorld.getGameRules().getBoolean(ModGamerules.SPAWN_PLAYER_GRAVES)) {

                PlayerGraveEntity playerGrave = new PlayerGraveEntity(ModEntities.PLAYER_GRAVE, victimPlayer.getWorld());
                playerGrave.setPosition(victimPlayer.getPos());
                playerGrave.setOwner(victimPlayer);
                playerGrave.resetInventory();

                victimPlayer.getInventory().main.forEach(stack -> playerGrave.addInventoryStackCheckSoulbound(stack.copy(), victimPlayer));
                victimPlayer.getInventory().offHand.forEach(stack -> playerGrave.addInventoryStackCheckSoulbound(stack.copy(), victimPlayer));
                victimPlayer.getInventory().armor.forEach(stack -> playerGrave.addInventoryStackCheckSoulbound(stack.copy(), victimPlayer));

                playerGrave.setCustomName(victimPlayer.getDisplayName());
                playerGrave.setCustomNameVisible(true);
                victimPlayer.getWorld().spawnEntity(playerGrave);
            }
        }
    }
    //?} else {
    /*@Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;onDeath(Lnet/minecraft/entity/damage/DamageSource;)V"), cancellable = true)
    private void grimoire$onDeathDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity thisEntity = (LivingEntity) (Object) this;

        if (thisEntity instanceof PlayerEntity victimPlayer) {

            if (victimPlayer.getWorld() == null) return;
            if (!victimPlayer.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY) && victimPlayer.getWorld().getGameRules().getBoolean(ModGamerules.SPAWN_PLAYER_GRAVES)) {

                PlayerGraveEntity playerGrave = new PlayerGraveEntity(ModEntities.PLAYER_GRAVE, victimPlayer.getWorld());
                playerGrave.setPosition(victimPlayer.getPos());
                playerGrave.setOwner(victimPlayer);
                playerGrave.resetInventory();

                if (FabricLoader.getInstance().isModLoaded("yyzsbackpack") && YYZsBackpackHelper.needCompatibility() && victimPlayer instanceof ServerPlayerEntity serverPlayer) {
                    try {
                        ItemStack backpackStack = YYZsBackpackHelper.save(serverPlayer);
                        playerGrave.addInventoryStackCheckSoulbound(backpackStack.copy(), victimPlayer);
                    } catch (Exception ignored) {}
                }

                if (FabricLoader.getInstance().isModLoaded("trinkets")) {
                    try {
                        TrinketsHelper.findAllEquippedBy(victimPlayer).forEach((stack) -> playerGrave.addInventoryStackCheckSoulbound(stack.copy(), victimPlayer));
                        TrinketsHelper.clearAllEquippedTrinkets(victimPlayer);
                    } catch (Exception ignored) {
                    }
                }

                victimPlayer.getInventory().main.forEach(stack -> playerGrave.addInventoryStackCheckSoulbound(stack.copy(), victimPlayer));
                victimPlayer.getInventory().offHand.forEach(stack -> playerGrave.addInventoryStackCheckSoulbound(stack.copy(), victimPlayer));
                victimPlayer.getInventory().armor.forEach(stack -> playerGrave.addInventoryStackCheckSoulbound(stack.copy(), victimPlayer));

                playerGrave.setCustomName(victimPlayer.getDisplayName());
                playerGrave.setCustomNameVisible(true);
                victimPlayer.getWorld().spawnEntity(playerGrave);
            }
        }
    }
    *///?}
}
