package nazario.nicosgraves.client;

import nazario.nicosgraves.entity.ModEntities;
import nazario.nicosgraves.entity.custom.client.PlayerGraveRenderer;
import net.fabricmc.api.ClientModInitializer;

//? if >=1.18 {
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
//?} else {
/*import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
*///?}

public class NicosGravesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //? if >=1.18 {
        EntityRendererRegistry.register(ModEntities.PLAYER_GRAVE, PlayerGraveRenderer::new);
        //?} else {
        /*EntityRendererRegistry.INSTANCE.register(ModEntities.PLAYER_GRAVE, PlayerGraveRenderer::new);
        *///?}
    }
}
