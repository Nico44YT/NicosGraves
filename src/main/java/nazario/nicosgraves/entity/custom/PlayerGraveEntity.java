package nazario.nicosgraves.entity.custom;

import nazario.nicosgraves.api.SoulboundItem;
import nazario.nicosgraves.util.ModGamerules;
import nazario.nicosgraves.util.ModTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.VehicleInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.*;
import net.minecraft.loot.LootTable;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerGraveEntity extends LivingEntity implements VehicleInventory {

    private static final int MAX_SIZE = 27 * 2; // Maximum inventory size
    private DefaultedList<ItemStack> inventory;
    private UUID owner;

    public PlayerGraveEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        this.setInvulnerable(true);
        this.inventory = DefaultedList.ofSize(MAX_SIZE, ItemStack.EMPTY);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 100f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 0f);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        //? >=1.20.5 {
        Inventories.readNbt(nbt.getCompound("inventory"), this.getInventory(), this.getWorld().getRegistryManager());
        //?} else {
        /*Inventories.readNbt(nbt.getCompound("inventory"), this.getInventory());
         *///?}
        this.owner = nbt.getUuid("owner");
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        NbtCompound inventoryNbt = new NbtCompound();
        //? >=1.20.5 {
        Inventories.writeNbt(inventoryNbt, this.getInventory(), this.getRegistryManager());
        //?} else {
        /*Inventories.writeNbt(inventoryNbt, this.getInventory());
         *///?}

        nbt.put("inventory", inventoryNbt);
        nbt.putUuid("owner", this.owner);
    }

    public void setOwner(PlayerEntity player) {
        this.owner = player.getUuid();
    }

    public UUID getOwnerUUID() {
        return this.owner;
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
        if (this.isTouchingWater() && this.getFluidHeight(FluidTags.WATER) > (double) f) {
            this.applyWaterBuoyancy();
            this.velocityDirty = true;
            this.velocityModified = true;
        } else if (this.isInLava() && this.getFluidHeight(FluidTags.LAVA) > (double) f) {
            this.applyLavaBuoyancy();
            this.velocityDirty = true;
            this.velocityModified = true;
        }
    }

    @Override
    public void kill() {
        this.dropInventory();
        this.discard();
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.getAttacker() instanceof PlayerEntity player) {
            if (getWorld().getGameRules().getBoolean(ModGamerules.ONLY_OWNER_ACCESS) && !this.getOwnerUUID().equals(player.getUuid())) {
                player.sendMessage(Text.translatable("message.nicos_graves.not_owner").formatted(Formatting.RED), true);
                return false;
            }

            if (player.isSneaking()) {
                if (getWorld().isClient) return true;

                this.dropInventory();
                this.discard();
                return true;
            }
        }
        return false;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof BowItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof ShieldItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof TridentItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof FishingRodItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof CrossbowItem
        ) return ActionResult.PASS;

        if (player.getWorld().isClient) {
            return ActionResult.PASS;
        }

        if (getEntityWorld().getGameRules().getBoolean(ModGamerules.ONLY_OWNER_ACCESS) && this.owner != null && !player.getUuid().equals(this.owner)) {
            player.sendMessage(Text.translatable("message.nicos_graves.not_owner").formatted(Formatting.RED), true);
            return ActionResult.PASS;
        }

        this.open(player);
        return ActionResult.SUCCESS; // Prevents further interaction processing
    }

    @Override
    protected void dropInventory() {
        for (int i = 0; i < inventory.size(); i++) {
            ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, getWorld());

            itemEntity.setStack(inventory.get(i));
            itemEntity.setPosition(this.getPos());

            getWorld().spawnEntity(itemEntity);
        }
        this.clearInventory();
    }

    public void addInventoryStackCheckSoulbound(ItemStack stack, PlayerEntity victimPlayer) {
        if (stack.getItem().getRegistryEntry().isIn(ModTags.ItemTags.SOULBOUND_ITEMS) || (stack.getItem() instanceof SoulboundItem soulboundItem && soulboundItem.isRetained(stack, victimPlayer, victimPlayer.getWorld())))
            return;
        this.addInventoryStack(stack);
    }

    public void addInventoryStack(ItemStack stack) {
        if (stack == null) return;
        if (stack.isEmpty()) return;
        if (stack.getItem().equals(Items.AIR)) return;
        for (int i = 0; i < MAX_SIZE; i++) {
            if (inventory.get(i).getItem().equals(Items.AIR)) {
                setInventoryStack(i, stack);
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
    public int size() {
        return MAX_SIZE;
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
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
    public ItemStack removeStack(int slot) {
        return inventory.set(slot, ItemStack.EMPTY);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.set(slot, stack);
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return GenericContainerScreenHandler.createGeneric9x6(syncId, inv, this);
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

    @Override
    public boolean isPushedByFluids() {
        return false;
    }


    //? <1.20.5 {

    /*@Override
    public Identifier getLootTable() {
        return super.getLootTable();
    }

    @Override
    public @Nullable Identifier getLootTableId() {
        return null;
    }

    @Override
    public void setLootTableId(@Nullable Identifier lootTableId) {

    }

    @Override
    public long getLootTableSeed() {
        return super.getLootTableSeed();
    }

    @Override
    public void setLootTableSeed(long lootTableSeed) {

    }

    *///?} else {
    @Override
    public void setLootTable(@Nullable RegistryKey<LootTable> lootTable) {

    }

    @Override
    public void setLootTableSeed(long lootTableSeed) {

    }

    //If you think this is not important think twice and run your game with a build jar without it, it will just crash and burn when you die.
    public RegistryKey<LootTable> method_42276() {
        return null;
    }
    //?}
}