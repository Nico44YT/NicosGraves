package nazario.nicosgraves.mixin;

import nazario.nicosgraves.entity.ModEntities;
import nazario.nicosgraves.entity.custom.PlayerGraveEntity;
import nazario.nicosgraves.util.ModGamerules;
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
    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;onDeath(Lnet/minecraft/entity/damage/DamageSource;)V"), cancellable = true)
    private void grimoire$onDeathDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity thisEntity = (LivingEntity) (Object) this;

        if (thisEntity instanceof PlayerEntity victimPlayer) {

            //? if >1.17 {
            if (victimPlayer.getWorld() == null) return;
            if (!victimPlayer.getWorld().getGameRules().getBoolean(GameRules.KEEP_INVENTORY) && victimPlayer.getWorld().getGameRules().getBoolean(ModGamerules.SPAWN_PLAYER_GRAVES)) {

                PlayerGraveEntity playerGrave = new PlayerGraveEntity(ModEntities.PLAYER_GRAVE, victimPlayer.getWorld());
                playerGrave.setPosition(victimPlayer.getPos());
                playerGrave.resetInventory();

                victimPlayer.getInventory().main.forEach(stack -> playerGrave.addInventoryStackCheckSoulbound(stack.copy(), victimPlayer));
                victimPlayer.getInventory().offHand.forEach(stack -> playerGrave.addInventoryStackCheckSoulbound(stack.copy(), victimPlayer));
                victimPlayer.getInventory().armor.forEach(stack -> playerGrave.addInventoryStackCheckSoulbound(stack.copy(), victimPlayer));

                //if (FabricLoader.getInstance().isModLoaded("trinkets")) {
                //    try {
                //        TrinketsHelper.findAllEquippedBy(victimPlayer).forEach((stack) -> playerGrave.addInventoryStackCheckSoulbound(stack.copy(), victimPlayer));
                //        TrinketsHelper.clearAllEquippedTrinkets(victimPlayer);
                //    } catch (Exception ignored) {
                //    }
                //}

                playerGrave.setCustomName(victimPlayer.getDisplayName());
                playerGrave.setCustomNameVisible(true);
                victimPlayer.getWorld().spawnEntity(playerGrave);
            }
            //?} else {
            /*if (victimPlayer.world == null) return;
            if (!victimPlayer.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY) && victimPlayer.world.getGameRules().getBoolean(ModGamerules.SPAWN_PLAYER_GRAVES)) {

                PlayerGraveEntity playerGrave = new PlayerGraveEntity(ModEntities.PLAYER_GRAVE, victimPlayer.world);
                playerGrave.setPosition(victimPlayer.getPos());
                playerGrave.resetInventory();

                victimPlayer.getInventory().main.forEach(stack -> playerGrave.addInventoryStackCheckSoulbound(stack.copy(), victimPlayer));
                victimPlayer.getInventory().offHand.forEach(stack -> playerGrave.addInventoryStackCheckSoulbound(stack.copy(), victimPlayer));
                victimPlayer.getInventory().armor.forEach(stack -> playerGrave.addInventoryStackCheckSoulbound(stack.copy(), victimPlayer));

                //if (FabricLoader.getInstance().isModLoaded("trinkets")) {
                //    try {
                //        TrinketsHelper.findAllEquippedBy(victimPlayer).forEach((stack) -> playerGrave.addInventoryStackCheckSoulbound(stack.copy(), victimPlayer));
                //        TrinketsHelper.clearAllEquippedTrinkets(victimPlayer);
                //    } catch (Exception ignored) {
                //    }
                //}

                playerGrave.setCustomName(victimPlayer.getDisplayName());
                playerGrave.setCustomNameVisible(true);
                victimPlayer.world.spawnEntity(playerGrave);
            }
            *///?}
        }
    }
}
