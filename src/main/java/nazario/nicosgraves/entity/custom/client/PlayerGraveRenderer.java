package nazario.nicosgraves.entity.custom.client;

import nazario.nicosgraves.entity.custom.PlayerGraveEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
//? if >=1.17 {
import net.minecraft.client.render.entity.EntityRendererFactory;
//?} else {
/*import net.minecraft.client.render.entity.EntityRenderDispatcher;
*///?}
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;

//? if >=1.20 {
import net.minecraft.util.math.RotationAxis;
import net.minecraft.client.render.model.json.ModelTransformationMode;
//?} else if >=1.17 {
/*import net.minecraft.util.math.Vec3f;
import net.minecraft.client.render.model.json.ModelTransformation;
*///?} else {
/*import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.Vector3f;
*///?}


@Environment(EnvType.CLIENT)
public class PlayerGraveRenderer extends LivingEntityRenderer<PlayerGraveEntity, PlayerGraveEntityModel<PlayerGraveEntity>> {

    //? if >=1.17 {
    public PlayerGraveRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PlayerGraveEntityModel<>(), 0.3f);
    }
    //?} else {
    /*public PlayerGraveRenderer(EntityRenderDispatcher dispatcher, EntityRendererRegistry.Context ctx) {
        super(dispatcher, new PlayerGraveEntityModel<>(), 0.3f);
    }
    *///?}

    @Override
    public void render(PlayerGraveEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        ItemStack stack = new ItemStack(Items.SKELETON_SKULL);

        matrixStack.push();

        matrixStack.scale(1.5f, 1.5f, 1.5f);
        matrixStack.translate(0, 0.25, 0);

        matrixStack.translate(0, Math.sin((entity.getWorld().getTime() + tickDelta)*0.1f)*0.1f, 0);

        //? if >=1.20 {
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.getWorld().getTime() + tickDelta) * 2.5f));

        itemRenderer.renderItem(stack, ModelTransformationMode.GROUND, false, matrixStack, vertexConsumers, light, 0, itemRenderer.getModel(stack, entity.getWorld(), null, 0));
        //?} else if >=1.18 {
        /*matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 2.5f));

        itemRenderer.renderItem(stack, ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumers, light, 0, itemRenderer.getModel(stack, entity.getWorld(), null, 0));
        *///?} else if >=1.17 {
        /*matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 2.5f));

        itemRenderer.renderItem(stack, ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumers, light, 0, itemRenderer.getHeldItemModel(stack, entity.getWorld(), null, 0));
        *///?} else {
        /*matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta) * 2.5f));

        itemRenderer.renderItem(stack, ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumers, light, 0, itemRenderer.getHeldItemModel(stack, entity.getWorld(), null));
        *///?}

        matrixStack.pop();

        if(entity.getCustomName() != null) {
            //? if >=1.21 {
            this.renderLabelIfPresent(entity, entity.getCustomName(), matrixStack, vertexConsumers, light, tickDelta);
            //?} else {
            /*this.renderLabelIfPresent(entity, entity.getCustomName(), matrixStack, vertexConsumers, light);
            *///?}

        }
    }
    @Override
    public Identifier getTexture(PlayerGraveEntity entity) {
        return null;
    }
}
