package nazario.nicosgraves.entity.custom.client;

import nazario.nicosgraves.entity.custom.PlayerGraveEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation; //<- Don't delete for 1.19 till 1.19.3
//? >=1.19.4 {
/*import net.minecraft.client.render.model.json.ModelTransformationMode;
*///?}
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
//? >=1.19.3 {
/*import net.minecraft.util.math.RotationAxis;
*///?} else {
import net.minecraft.util.math.Vec3f;
//?}

public class PlayerGraveRenderer extends LivingEntityRenderer<PlayerGraveEntity, PlayerGraveEntityModel<PlayerGraveEntity>> {

    public PlayerGraveRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PlayerGraveEntityModel<>(), 0.3f);
    }

    @Override
    public void render(PlayerGraveEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        ItemStack stack = new ItemStack(Items.SKELETON_SKULL);

        matrixStack.push();

        matrixStack.scale(1.5f, 1.5f, 1.5f);
        matrixStack.translate(0, 0.25, 0);

        matrixStack.translate(0, Math.sin((entity.getWorld().getTime() + tickDelta)*0.1f)*0.1f, 0);

        //? >=1.19.3 {
        /*matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.getWorld().getTime() + tickDelta) * 2.5f));
        *///?} else {
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 2.5f));
        //?}

        itemRenderer.renderItem(stack,
                //? >=1.19.4 {
                /*ModelTransformationMode.GROUND,
                *///?} else {
                ModelTransformation.Mode.GROUND,
                //?}
                false,
                matrixStack,
                vertexConsumers,
                light,
                0,
                itemRenderer.getModel(stack, entity.getWorld(), null, 0)
        );

        matrixStack.pop();

        if(entity.getCustomName() != null) {
            this.renderLabelIfPresent(entity, entity.getCustomName(), matrixStack, vertexConsumers, light);
        }
    }
    @Override
    public Identifier getTexture(PlayerGraveEntity entity) {
        return null;
    }
}
