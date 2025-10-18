package nazario.nicosgraves.entity;

import nazario.liby.api.registry.helper.LibyEntityTypeRegistry;
import nazario.nicosgraves.NicosGraves;
import nazario.nicosgraves.entity.custom.PlayerGraveEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;

public class ModEntities {
    private static final LibyEntityTypeRegistry REGISTRY = LibyEntityTypeRegistry.of(NicosGraves.MOD_ID);

    public static final EntityType<PlayerGraveEntity> PLAYER_GRAVE = REGISTRY.registerEntityType(
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
}
