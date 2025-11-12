package nazario.nicosgraves.entity;

import nazario.nicosgraves.NicosGraves;
import nazario.nicosgraves.entity.custom.PlayerGraveEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.Function;

public class ModEntities {
    public static final EntityType<PlayerGraveEntity> PLAYER_GRAVE = registerEntityType(
            "player_grave",
            key -> FabricEntityType.Builder.createLiving(PlayerGraveEntity::new, SpawnGroup.MISC, PlayerGraveEntity::createAttributes)
            .dimensions(0.5f, 0.5f)
            .spawnableFarFromPlayer()
            .disableSummon()
            //? >=1.21.2 {
            /*.build(key)
            *///?} else {
            .build()
            //?}
    );

    public static void register() {

    }

    private static <T extends Entity> EntityType<T> registerEntityType(String name, Function<RegistryKey<EntityType<?>>, EntityType<T>> factory) {
        RegistryKey<EntityType<?>> entityKey = RegistryKey.of(RegistryKeys.ENTITY_TYPE, NicosGraves.id(name));
        return Registry.register(Registries.ENTITY_TYPE, NicosGraves.id(name), factory.apply(entityKey));
    }
}
