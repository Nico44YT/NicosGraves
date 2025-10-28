package nazario.nicosgraves.entity.custom;

import com.mojang.authlib.GameProfile;
import nazario.nicosgraves.api.SoulboundItem;
import nazario.nicosgraves.entity.VehicleInventory;
import nazario.nicosgraves.util.ModTags;
import net.minecraft.container.Container;
import net.minecraft.container.GenericContainer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.*;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerGraveEntity extends LivingEntity implements VehicleInventory {

    private static final int MAX_SIZE = 27*2; // Maximum inventory size
    private DefaultedList<ItemStack> inventory;
    private GameProfile playerGameProfile;

    public PlayerGraveEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        this.setInvulnerable(true);
        this.inventory = DefaultedList.ofSize(MAX_SIZE, ItemStack.EMPTY);
    }



    //public static DefaultAttributeContainer.Builder createAttributes() {
    //    return LivingEntity.createLivingAttributes()
    //            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0f)
    //            .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 100f)
    //            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 0f);
    //}

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);

        Inventories.fromTag(tag.getCompound("inventory"), this.getInventory());

    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);

        CompoundTag inventoryNbt = new CompoundTag();
        Inventories.toTag(inventoryNbt, this.getInventory());

        //CompoundTag gameProfileNbt = new CompoundTag();
        //NbtHelper.writeGameProfile(gameProfileNbt, this.getGameProfile());

        tag.put("inventory", inventoryNbt);
        //nbt.put("player_profile", gameProfileNbt);
    }

    public void setGameProfile(PlayerEntity player) {
        UUID playerUuid = player.getUuid();
        String playerName = player.getName().getString();

        this.playerGameProfile = new GameProfile(playerUuid, playerName);
    }

    public GameProfile getGameProfile() {
        return this.playerGameProfile;
    }

    private void applyWaterBuoyancy() {
        Vec3d vec3d = this.getVelocity();
        this.setVelocity(vec3d.x * 0.99, vec3d.y + (vec3d.y < 0.05 ? 0.02 : 0.0), vec3d.z * 0.99);
    }

    private void applyLavaBuoyancy() {
        Vec3d vec3d = this.getVelocity();
        this.setVelocity(vec3d.x * 0.99, vec3d.y + (vec3d.y < 0.10 ? 0.04 : 0.0), vec3d.z * 0.99);
    }

    @Override
    public void tick() {
        super.tick();

        float f = this.getStandingEyeHeight();
        if (this.isTouchingWater() && this.getHeight() > (double)f) {
            this.applyWaterBuoyancy();
            this.velocityDirty = true;
            this.velocityModified = true;
        } else if (this.isInLava() && this.getHeight() > (double)f) {
            this.applyLavaBuoyancy();
            this.velocityDirty = true;
            this.velocityModified = true;
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(source.getAttacker() instanceof PlayerEntity player) {
            if(player.isSneaking()) {
                if(getWorld().isClient) return true;

                this.dropInventory();
                this.remove();
                return true;
            }
        }
        return false;
    }

    public Container getContainer(int syncId, PlayerInventory playerInventory) {
        return GenericContainer.createGeneric9x6(syncId, playerInventory, this);
    }

    @Override
    public @Nullable Container createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        if (playerEntity.isSpectator()) {
            return null;
        } else {
            this.method_7563(playerInventory.player);
            return this.getContainer(syncId, playerInventory);
        }
    }

    public void method_7563(@Nullable PlayerEntity playerEntity) {
        if (this.getLootTableId() != null && this.world.getServer() != null) {
            LootTable lootTable = this.world.getServer().getLootManager().getSupplier(this.getLootTableId());
            this.setLootTableId(null);
            LootContext.Builder builder = (new LootContext.Builder((ServerWorld)this.world)).put(LootContextParameters.POSITION, new BlockPos(this)).setRandom(this.getLootTableSeed());
            if (playerEntity != null) {
                builder.setLuck(playerEntity.getLuck()).put(LootContextParameters.THIS_ENTITY, playerEntity);
            }

            lootTable.supplyInventory(this, builder.build(LootContextTypes.CHEST));
        }

    }

    @Override
    public boolean interact(PlayerEntity player, Hand hand) {
        if(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof BowItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof ShieldItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof TridentItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof FishingRodItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof CrossbowItem
        ) return true;

        if (player.world.isClient) {
            return false;
        }

        this.open(player);
        return false; // Prevents further interaction processing
    }

    @Override
    protected void dropInventory() {
        for(int i = 0;i<inventory.size();i++) {
            ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, getWorld());

            itemEntity.setStack(inventory.get(i));
            itemEntity.setPos(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());

            getWorld().spawnEntity(itemEntity);
        }
        this.clearInventory();
    }

    public void addInventoryStackCheckSoulbound(ItemStack stack, PlayerEntity victimPlayer) {
        if(ModTags.ItemTags.SOULBOUND_ITEMS.contains(stack.getItem()) || (stack.getItem() instanceof SoulboundItem soulboundItem && soulboundItem.isRetained(stack, victimPlayer, victimPlayer.world))) return;
        this.addInventoryStack(stack);
    }

    public void addInventoryStack(ItemStack stack) {
        if(stack == null) return;
        if(stack.isEmpty()) return;
        if(stack.getItem().equals(Items.AIR)) return;
        for(int i = 0;i<MAX_SIZE;i++) {
            if(inventory.get(i).getItem().equals(Items.AIR)) {
                setInvStack(i, stack);
                break;
            }
        }
    }

    @Override
    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    @Override
    public void resetInventory() {
        this.inventory = DefaultedList.ofSize(MAX_SIZE, ItemStack.EMPTY);
    }

    @Override
    public World getWorld() {
        return world;
    }

    @Override
    public boolean isRemoved() {
        return removed;
    }

    @Override
    public int getInvSize() {
        return MAX_SIZE;
    }

    @Override
    public ItemStack getInvStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack takeInvStack(int slot, int amount) {
        ItemStack currentStack = inventory.get(slot);
        if (currentStack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        // Split the stack
        ItemStack removedStack = currentStack.split(amount);

        // If the current stack is now empty, set the slot to EMPTY
        if (currentStack.isEmpty()) {
            inventory.set(slot, ItemStack.EMPTY);
        }

        return removedStack;
    }

    @Override
    public ItemStack removeInvStack(int slot) {
        return inventory.set(slot, ItemStack.EMPTY);
    }

    @Override
    public void setInvStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }

    @Override
    public void markDirty() {

    }

    @Override
    public void clear() {
        inventory.clear();
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        Iterable<ItemStack> stacks = new ArrayList<ItemStack>();
        stacks.forEach(stack -> new ItemStack(Items.AIR));
        return stacks;
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return new ItemStack(Items.AIR);
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {

    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    //region// No clue //
    @Override
    public long getLootTableSeed() {
        return 0;
    }
    @Override
    public @Nullable Identifier getLootTableId() {
        return null;
    }

    @Override
    public void setLootTableId(@Nullable Identifier lootTableId) {

    }

    @Override
    public void setLootTableSeed(long lootTableSeed) {

    }
    //endregion
}
