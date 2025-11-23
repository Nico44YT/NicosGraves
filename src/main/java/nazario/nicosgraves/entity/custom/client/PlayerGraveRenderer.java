package nazario.nicosgraves.entity.custom.client;

//? >=1.21.9 {
/*import nazario.nicosgraves.entity.custom.PlayerGraveEntity;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.HeldItemContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
*///?} else >=1.21.5 {
/*import nazario.nicosgraves.entity.custom.PlayerGraveEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
*///?} else >=1.21.2 {
/*import nazario.nicosgraves.entity.custom.PlayerGraveEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
*///?} else {
import nazario.nicosgraves.entity.custom.PlayerGraveEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
//?}

//? >=1.21.2 {
/*public class PlayerGraveRenderer extends LivingEntityRenderer<PlayerGraveEntity, LivingEntityRenderState, PlayerGraveEntityModel> {

    private PlayerGraveEntity entity;
    private float tickDelta;

    //? >=1.21.9 {
    /^private final ItemModelManager itemModelManager;
    ^///?}

    public PlayerGraveRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PlayerGraveEntityModel(), 0.3f);

        //? >=1.21.9 {
        /^this.itemModelManager = ctx.getItemModelManager();
        ^///?}
    }

    @Override
    public Identifier getTexture(LivingEntityRenderState state) {
        return Identifier.of("","");
    }

    @Override
    public LivingEntityRenderState createRenderState() {
        return new LivingEntityRenderState();
    }

    @Override
    public void updateRenderState(PlayerGraveEntity entity, LivingEntityRenderState livingEntityRenderState, float tickDelta) {
        super.updateRenderState(entity, livingEntityRenderState, tickDelta);

        this.entity = entity;
        this.tickDelta = tickDelta;// <- No clue if this is actually tick delta, and I have no idea how to get tick delta
    }

    //? >=1.21.9 {
    /^@Override
    public void render(LivingEntityRenderState livingEntityRenderState, MatrixStack matrixStack, OrderedRenderCommandQueue orderedRenderCommandQueue, CameraRenderState cameraRenderState) {
        int light = getLight(entity, tickDelta);
        ItemStack stack = new ItemStack(Items.SKELETON_SKULL);
        ItemRenderState itemRenderState = new ItemRenderState();
        this.itemModelManager.clearAndUpdate(itemRenderState, stack, ItemDisplayContext.FIXED, entity.getEntityWorld(), (HeldItemContext)null, 0);


        matrixStack.push();

        matrixStack.translate(0, 0.5, 0);
        matrixStack.scale(0.75f, 0.75f, 0.75f);

        matrixStack.translate(0, Math.sin((entity.age + tickDelta)*0.1f)*0.1f, 0);

        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.age + tickDelta) * 2.5f));

        itemRenderState.render(
                matrixStack,
                orderedRenderCommandQueue,
                light,
                0,
                0
        );


        matrixStack.pop();

        if(entity.getCustomName() != null) {
            this.renderLabelIfPresent(livingEntityRenderState, matrixStack, orderedRenderCommandQueue, cameraRenderState);
        }
    }

    ^///?} else {
    @Override
    public void render(LivingEntityRenderState livingEntityRenderState, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

        ItemStack stack = new ItemStack(Items.SKELETON_SKULL);

        matrixStack.push();

        matrixStack.scale(1.5f, 1.5f, 1.5f);
        matrixStack.translate(0, 0.25, 0);

        matrixStack.translate(0, Math.sin((entity.age + tickDelta)*0.1f)*0.1f, 0);

        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.age + tickDelta) * 2.5f));

        //? >=1.21.5 {
        /^itemRenderer.renderItem(stack, ItemDisplayContext.GROUND, light, 0, matrixStack, vertexConsumerProvider, entity.getWorld(), 0);
        ^///?} else >=1.21.4 {
        /^itemRenderer.renderItem(stack, ModelTransformationMode.GROUND, light, 0, matrixStack, vertexConsumerProvider, entity.getWorld(), 0);
        ^///?} else {
        itemRenderer.renderItem(stack, ModelTransformationMode.GROUND, false, matrixStack, vertexConsumerProvider, light, 0, itemRenderer.getModel(stack, entity.getWorld(), null, 0));
        //?}

        matrixStack.pop();

        if(entity.getCustomName() != null) {
            this.renderLabelIfPresent(livingEntityRenderState, livingEntityRenderState.customName, matrixStack, vertexConsumerProvider, light);
        }
    }
    //?}
}
*///?} else {
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

        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((entity.getWorld().getTime() + tickDelta) * 2.5f));

        itemRenderer.renderItem(stack, ModelTransformationMode.GROUND, false, matrixStack, vertexConsumers, light, 0, itemRenderer.getModel(stack, entity.getWorld(), null, 0));

        matrixStack.pop();

        if(entity.getCustomName() != null) {
            this.renderLabelIfPresent(entity, entity.getCustomName(), matrixStack, vertexConsumers, light, tickDelta);
        }
    }

    @Override
    public Identifier getTexture(PlayerGraveEntity entity) {
        return null;
    }
}
//?}
