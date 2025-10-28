package nazario.nicosgraves.client;

import nazario.nicosgraves.entity.ModEntities;
import nazario.nicosgraves.entity.custom.client.PlayerGraveRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class NicosGravesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(ModEntities.PLAYER_GRAVE, PlayerGraveRenderer::new);
    }
}
