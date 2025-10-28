package nazario.nicosgraves.entity;

import net.minecraft.container.NameableContainerFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;

public interface VehicleInventory extends Inventory, NameableContainerFactory {
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
		return this.isInvEmpty();
	}

	default void writeInventoryToNbt(CompoundTag nbt) {
		if (this.getLootTableId() != null) {
			nbt.putString("LootTable", this.getLootTableId().toString());
			if (this.getLootTableSeed() != 0L) {
				nbt.putLong("LootTableSeed", this.getLootTableSeed());
			}
		} else {
			Inventories.toTag(nbt, this.getInventory());
		}

	}

	default void readInventoryFromNbt(CompoundTag nbt) {
		this.resetInventory();
		if (nbt.contains("LootTable", 8)) {
			this.setLootTableId(new Identifier(nbt.getString("LootTable")));
			this.setLootTableSeed(nbt.getLong("LootTableSeed"));
		} else {
			Inventories.fromTag(nbt, this.getInventory());
		}

	}


	default void onBroken(DamageSource source, World world, Entity vehicle) {
		if (world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
			ItemScatterer.spawn(world, vehicle, this);

		}
	}

	default ActionResult open(PlayerEntity player) {
		player.openContainer(this);
		if (!player.world.isClient) {
			return ActionResult.CONSUME;
		} else {
			return ActionResult.SUCCESS;
		}
	}

	default void clearInventory() {
		this.getInventory().clear();
	}

	@Override
	default boolean isInvEmpty() {
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

	@Override
	default ItemStack removeInvStack(int slot) {
		ItemStack itemStack = (ItemStack)this.getInventory().get(slot);
		if (itemStack.isEmpty()) {
			return ItemStack.EMPTY;
		} else {
			this.getInventory().set(slot, ItemStack.EMPTY);
			return itemStack;
		}
	}

	@Override
	default ItemStack getInvStack(int slot) {
		return (ItemStack)this.getInventory().get(slot);
	}

	@Override
	default ItemStack takeInvStack(int slot, int amount) {
		return Inventories.splitStack(this.getInventory(), slot, amount);
	}

	@Override
	default void setInvStack(int slot, ItemStack stack) {
		this.getInventory().set(slot, stack);
		if (!stack.isEmpty() && stack.getCount() > this.getInvMaxStackAmount()) {
			stack.setCount(this.getInvMaxStackAmount());
		}

	}

	@Override
	default boolean canPlayerUseInv(PlayerEntity player) {
		return !this.isRemoved() && this.getPos().distanceTo(player.getPos()) <= 8.0;
	}
}
