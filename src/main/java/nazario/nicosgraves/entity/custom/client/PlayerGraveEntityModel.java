package nazario.nicosgraves.entity.custom.client;

//? >=1.21.2 {
/*import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
*///?} else {
import nazario.nicosgraves.entity.custom.PlayerGraveEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
//?}

//? >=1.21.2 {
/*@Environment(EnvType.CLIENT)
public class PlayerGraveEntityModel extends EntityModel<LivingEntityRenderState> {
    protected PlayerGraveEntityModel() {
        super(new ModelPart(new ArrayList<>(), new HashMap<>()));
    }

    @Override
    public void setAngles(LivingEntityRenderState state) {

    }


}
*///?} else {
@Environment(EnvType.CLIENT)
public class PlayerGraveEntityModel<T extends PlayerGraveEntity> extends EntityModel<T> {
    @Override
    public void setAngles(PlayerGraveEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {

    }
}
//?}
