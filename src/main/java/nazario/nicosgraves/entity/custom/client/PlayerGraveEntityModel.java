package nazario.nicosgraves.entity.custom.client;

import nazario.nicosgraves.entity.custom.PlayerGraveEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class PlayerGraveEntityModel<T extends PlayerGraveEntity> extends EntityModel<T> {

    @Override
    public void setAngles(PlayerGraveEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {

    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(null, 8, 8);
    }
}
