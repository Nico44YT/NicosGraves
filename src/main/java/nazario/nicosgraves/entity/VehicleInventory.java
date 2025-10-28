package nazario.nicosgraves.entity;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.function.BiConsumer;


public interface VehicleInventory extends Inventory, NamedScreenHandlerFactory {
	Vec3d getPos();

	@Nullable
	Identifier getLootTableId();

	void setLootTableId(@Nullable Identifier lootTableId);

	long getLootTableSeed();

	void setLootTableSeed(long lootTableSeed);

	DefaultedList<ItemStack> getInventory();

	void resetInventory();

	World getWorld();

	boolean isRemoved();

	default boolean isEmpty() {
		return this.isInventoryEmpty();
	}

	default void writeInventoryToNbt(NbtCompound nbt) {
		if (this.getLootTableId() != null) {
			nbt.putString("LootTable", this.getLootTableId().toString());
			if (this.getLootTableSeed() != 0L) {
				nbt.putLong("LootTableSeed", this.getLootTableSeed());
			}
		} else {
			Inventories.writeNbt(nbt, this.getInventory());
		}

	}

	default void readInventoryFromNbt(NbtCompound nbt) {
		this.resetInventory();
		if (nbt.contains("LootTable", 8)) {
			this.setLootTableId(new Identifier(nbt.getString("LootTable")));
			this.setLootTableSeed(nbt.getLong("LootTableSeed"));
		} else {
			Inventories.readNbt(nbt, this.getInventory());
		}

	}

	default void onBroken(DamageSource source, World world, Entity vehicle) {
		if (world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
			ItemScatterer.spawn(world, vehicle, this);
			if (!world.isClient) {
				Entity entity = source.getSource();
				if (entity != null && entity.getType() == EntityType.PLAYER) {
					PiglinBrain.onGuardedBlockInteracted((PlayerEntity)entity, true);
				}
			}

		}
	}

	default ActionResult open(PlayerEntity player) {
		player.openHandledScreen(this);
		if (!player.world.isClient) {
			PiglinBrain.onGuardedBlockInteracted(player, true);
			return ActionResult.CONSUME;
		} else {
			return ActionResult.SUCCESS;
		}
	}

	default void generateInventoryLoot(@Nullable PlayerEntity player) {
		MinecraftServer minecraftServer = this.getWorld().getServer();
		if (this.getLootTableId() != null && minecraftServer != null) {
			LootTable lootTable = minecraftServer.getLootManager().getTable(this.getLootTableId());

			this.setLootTableId((Identifier)null);
			LootContext.Builder builder = (new LootContext.Builder((ServerWorld)this.getWorld())).parameter(LootContextParameters.ORIGIN, this.getPos()).random(this.getLootTableSeed());
			if (player != null) {
				builder.luck(player.getLuck()).parameter(LootContextParameters.THIS_ENTITY, player);
			}

			lootTable.supplyInventory(this, builder.build(LootContextTypes.CHEST));
		}

	}

	default void clearInventory() {
		this.generateInventoryLoot((PlayerEntity)null);
		this.getInventory().clear();
	}

	default boolean isInventoryEmpty() {
		Iterator var1 = this.getInventory().iterator();

		ItemStack itemStack;
		do {
			if (!var1.hasNext()) {
				return true;
			}

			itemStack = (ItemStack)var1.next();
		} while(itemStack.isEmpty());

		return false;
	}

	default ItemStack removeInventoryStack(int slot) {
		this.generateInventoryLoot((PlayerEntity)null);
		ItemStack itemStack = (ItemStack)this.getInventory().get(slot);
		if (itemStack.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			this.getInventory().set(slot, ItemStack.EMPTY);
			return itemStack;
		}
	}

	default ItemStack getInventoryStack(int slot) {
		this.generateInventoryLoot((PlayerEntity)null);
		return (ItemStack)this.getInventory().get(slot);
	}

	default ItemStack removeInventoryStack(int slot, int amount) {
		this.generateInventoryLoot((PlayerEntity)null);
		return Inventories.splitStack(this.getInventory(), slot, amount);
	}

	default void setInventoryStack(int slot, ItemStack stack) {
		this.generateInventoryLoot((PlayerEntity)null);
		this.getInventory().set(slot, stack);
		if (!stack.isEmpty() && stack.getCount() > this.getMaxCountPerStack()) {
			stack.setCount(this.getMaxCountPerStack());
		}

	}

	default boolean canPlayerAccess(PlayerEntity player) {
		return !this.isRemoved() && this.getPos().isInRange(player.getPos(), 8.0);
	}
}
