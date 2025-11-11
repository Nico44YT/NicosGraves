package nazario.nicosgraves.entity;

import nazario.nicosgraves.NicosGraves;
import nazario.nicosgraves.entity.custom.PlayerGraveEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
//? >=1.19.3 {
/*import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
*///?} else {
import net.minecraft.util.registry.Registry;
//?}

public class ModEntities {
    public static final EntityType<PlayerGraveEntity> PLAYER_GRAVE = registerEntityType(
            "player_grave",
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, PlayerGraveEntity::new)
            .dimensions(EntityDimensions.fixed(0.5f, 0.5f))
            .spawnableFarFromPlayer()
            .disableSummon()
            .build()
    );

    public static void register() {
        FabricDefaultAttributeRegistry.register(PLAYER_GRAVE, PlayerGraveEntity.createAttributes());
    }

    //? >=1.19.3 {
    /*private static <T extends Entity> EntityType<T> registerEntityType(String name, EntityType<T> type) {
        return Registry.register(Registries.ENTITY_TYPE, NicosGraves.id(name), type);
    }
    *///?} else {
    private static <T extends Entity> EntityType<T> registerEntityType(String name, EntityType<T> type) {
        return Registry.register(Registry.ENTITY_TYPE, NicosGraves.id(name), type);
    }
    //?}
}
