package nazario.nicosgraves.entity.custom;

//? >=1.21.9 {
/*import nazario.nicosgraves.NicosGraves;
import nazario.nicosgraves.api.SoulboundItem;
import nazario.nicosgraves.util.ModGamerules;
import nazario.nicosgraves.util.ModTags;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.vehicle.VehicleInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.*;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
*///?} else >=1.21.5 {
/*import nazario.nicosgraves.NicosGraves;
import nazario.nicosgraves.api.SoulboundItem;
import nazario.nicosgraves.util.ModGamerules;
import nazario.nicosgraves.util.ModTags;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.server.world.ServerWorld;
//? >=1.21.6 {
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
//?}
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
*///?} else >=1.21.2 {
/*import nazario.nicosgraves.api.SoulboundItem;
import nazario.nicosgraves.util.ModGamerules;
import nazario.nicosgraves.util.ModTags;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
*///?} else {
import nazario.nicosgraves.api.SoulboundItem;
import nazario.nicosgraves.util.ModGamerules;
import nazario.nicosgraves.util.ModTags;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
//?}

import java.util.ArrayList;
import java.util.UUID;

public class PlayerGraveEntity extends LivingEntity implements VehicleInventory {

    private static final int MAX_SIZE = 27*2; // Maximum inventory size
    private DefaultedList<ItemStack> inventory;
    private UUID owner;

    public PlayerGraveEntity(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
        this.setInvulnerable(true);
        this.inventory = DefaultedList.ofSize(MAX_SIZE, ItemStack.EMPTY);
    }

    public static FabricEntityType.Builder.Living<PlayerGraveEntity> createAttributes(FabricEntityType.Builder.Living<PlayerGraveEntity> builder) {
        //? <1.21.2 {
        return builder.defaultAttributes(() -> LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 100f)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 0f)
        );
        //?} else {
        /*return builder.defaultAttributes(() -> LivingEntity.createLivingAttributes()
                .add(EntityAttributes.MOVEMENT_SPEED, 0)
                .add(EntityAttributes.KNOCKBACK_RESISTANCE, 100)
                .add(EntityAttributes.FOLLOW_RANGE, 0)
        );
        *///?}
    }

    //? >=1.21.6 {

    /*@Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);

        Inventories.readData(view, this.getInventory());
        view.read("owner", Uuids.CODEC).ifPresentOrElse(this::setOwnerUUID, () -> NicosGraves.LOGGER.warn("Owner UUID not found in grave."));
    }

    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);

        Inventories.writeData(view, this.getInventory());
        view.putNullable("owner", Uuids.CODEC, this.owner);
    }

    *///?} else {
    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        //? >=1.21.5 {
        /*nbt.getCompound("inventory").ifPresentOrElse(this::setInventoryFromNbt, () -> NicosGraves.LOGGER.warn("No inventory found in grave."));
        nbt.get("owner", Uuids.CODEC).ifPresentOrElse(this::setOwnerUUID, () -> NicosGraves.LOGGER.warn("Owner UUID not found in grave."));
        *///?} else {
        Inventories.readNbt(nbt.getCompound("inventory"), this.getInventory(), getWorld().getRegistryManager());
        this.owner = nbt.getUuid("owner");
        //?}
    }

    public void setInventoryFromNbt(NbtCompound inventoryNbt) {
        Inventories.readNbt(inventoryNbt, this.getInventory(), getWorld().getRegistryManager());
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        NbtCompound inventoryNbt = new NbtCompound();
        Inventories.writeNbt(inventoryNbt, this.getInventory(), getWorld().getRegistryManager());

        //? >=1.21.5 {
        /*nbt.put("inventory", NbtCompound.CODEC, inventoryNbt);
        nbt.put("owner", Uuids.CODEC, this.owner);
        *///?} else {
        nbt.put("inventory", inventoryNbt);
        nbt.putUuid("owner", this.owner);
        //?}
    }
    //?}

    private void setOwnerUUID(UUID uuid) {
        this.owner = uuid;
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
        if (this.isTouchingWater() && this.getFluidHeight(FluidTags.WATER) > (double)f) {
            this.applyWaterBuoyancy();
            this.velocityDirty = true;
            this.velocityModified = true;
        } else if (this.isInLava() && this.getFluidHeight(FluidTags.LAVA) > (double)f) {
            this.applyLavaBuoyancy();
            this.velocityDirty = true;
            this.velocityModified = true;
        }
    }

    //? >=1.21.2 {
    /*@Override
    public void kill(ServerWorld world) {
        this.kill();
    }
    *///?}


    public void kill() {
        this.dropInventory();
        this.discard();
    }

    //? >=1.21.2 {
    /*@Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return this.damage(source, amount);
    }
    *///?}


    public boolean damage(DamageSource source, float amount) {
        //? >=1.21.9 {
        /*World world = this.getEntityWorld();
        *///?} else {
        World world = this.getWorld();
        //?}

        if (source.getAttacker() instanceof PlayerEntity player && world instanceof ServerWorld serverWorld) {
            if (serverWorld.getGameRules().getBoolean(ModGamerules.ONLY_OWNER_ACCESS) && !this.getOwnerUUID().equals(player.getUuid())) {
                player.sendMessage(Text.translatable("message.nicos_graves.not_owner").formatted(Formatting.RED), true);
                return false;
            }

            if (player.isSneaking()) {

                this.dropInventory();
                this.discard();
                return true;
            }
        }
        return false;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        //? >=1.21.9 {
        /*World world = player.getEntityWorld();
        *///?} else {
        World world = player.getWorld();
        //?}

        if(player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof BowItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof ShieldItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof TridentItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof FishingRodItem ||
                player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof CrossbowItem
        ) return ActionResult.PASS;

        if (world.isClient()) {
            return ActionResult.PASS;
        }

        if (world instanceof ServerWorld serverWorld && serverWorld.getGameRules().getBoolean(ModGamerules.ONLY_OWNER_ACCESS) && this.owner != null && !player.getUuid().equals(this.owner)) {
            player.sendMessage(Text.translatable("message.nicos_graves.not_owner").formatted(Formatting.RED), true);
            return ActionResult.PASS;
        }

        this.open(player);
        return ActionResult.SUCCESS; // Prevents further interaction processing
    }


    //? >=1.21.2 {
    /*@Override
    protected void dropInventory(ServerWorld world) {
        this.dropInventory();
    }
    *///?}

    protected void dropInventory() {
        //? >=1.21.9 {
        /*World world = this.getEntityWorld();
        *///?} else {
        World world = this.getWorld();
        //?}

        for(int i = 0;i<inventory.size();i++) {
            ItemEntity itemEntity = new ItemEntity(EntityType.ITEM, world);

            itemEntity.setStack(inventory.get(i));

            //? >=1.21.9 {
            /*itemEntity.setPosition(this.getEntityPos());
            *///?} else {
            itemEntity.setPosition(this.getPos());
            //?}

            world.spawnEntity(itemEntity);
        }
        this.clearInventory();
    }

    public void addInventoryStackCheckSoulbound(ItemStack stack, PlayerEntity victimPlayer) {
        //? >=1.21.9 {
        /*World world = victimPlayer.getEntityWorld();
        *///?} else {
        World world = victimPlayer.getWorld();
        //?}

        if(stack.getItem().getRegistryEntry().isIn(ModTags.ItemTags.SOULBOUND_ITEMS) || (stack.getItem() instanceof SoulboundItem soulboundItem && soulboundItem.isRetained(stack, victimPlayer, world))) return;
        this.addInventoryStack(stack);
    }

    public void addInventoryStack(ItemStack stack) {
        if(stack == null) return;
        if(stack.isEmpty()) return;
        if(stack.getItem().equals(Items.AIR)) return;
        for(int i = 0;i<MAX_SIZE;i++) {
            if(inventory.get(i).getItem().equals(Items.AIR)) {
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

    //@Override
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

    //region// No clue //
    @Override
    public void setLootTable(@Nullable RegistryKey<LootTable> lootTable) {

    }

    @Override
    public void setLootTableSeed(long lootTableSeed) {

    }

    //? <1.21.2 {
    //If you think this is not important think twice and run your game with a build jar without it, it will just crash and burn when you die.
    public RegistryKey<LootTable> method_42276() {
        return null;
    }

    //?} else {
    /*@Override
    public @Nullable RegistryKey<LootTable> getLootTable() {
        return null;
    }
    *///?}

    //? <1.21.9 && >1.21.6 {
    /*public @Nullable World method_37908() {
        return this.world;
    }
    *///?}

    //endregion
}
