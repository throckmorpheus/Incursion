package net.throckmorpheus.incursion.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.model.BookModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.math.random.Random;
import net.throckmorpheus.incursion.screen.slot.EnchantableItemSlot;
import net.throckmorpheus.incursion.util.ModItemTags;

public class CustomEnchantmentScreen extends HandledScreen<CustomEnchantmentScreenHandler> {
    private static final Identifier TEXTURE = new Identifier("incursion", "textures/gui/container/enchanting_table.png");
    private static final Identifier BOOK_TEXTURE = new Identifier("textures/entity/enchanting_table_book.png");
    private final Random random = Random.create();
    private BookModel BOOK_MODEL;
    public int ticks;
    public float nextPageAngle;
    public float pageAngle;
    public float approximatePageAngle;
    public float pageRotationSpeed;
    public float nextPageTurningSpeed;
    public float pageTurningSpeed;
    public float bookAngle = 70.0F;
    public float nextBookAngle = bookAngle;
    public float bookX = 0.1F;
    public float nextBookX = bookX;
    public float bookY = 2.6F;
    public float nextBookY = bookY;
    public boolean isBookOpen = false;
    private ItemStack stack;

    public CustomEnchantmentScreen(CustomEnchantmentScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        stack = ItemStack.EMPTY;
    }

    @Override
    public void handledScreenTick() {
        super.handledScreenTick();
        this.doTick();
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);
        //Overlay
        /*
        float opacity = 0.5F;
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, opacity);
        RenderSystem.setShaderTexture(1, OVERLAY_TEXTURE);
        drawTexture(matrices, x, y,-75, 0, 0, backgroundWidth, backgroundHeight,256,256);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        */
        //Book Rendering
        int k = (int)this.client.getWindow().getScaleFactor();
        RenderSystem.viewport((this.width - 317) / 2 * k, (this.height - 255) / 2 * k, 320 * k, 240 * k);
        Matrix4f matrix4f = Matrix4f.translate(-0.34F, 0.23F, 0.0F);
        matrix4f.multiply(Matrix4f.viewboxMatrix(90.0, 1.3333334F, 9.0F, 80.0F));
        RenderSystem.backupProjectionMatrix();
        RenderSystem.setProjectionMatrix(matrix4f);
        matrices.push();
        MatrixStack.Entry entry = matrices.peek();
        entry.getPositionMatrix().loadIdentity();
        entry.getNormalMatrix().loadIdentity();
        float xInbetween = MathHelper.lerp(delta,bookX,nextBookX);
        float yInbetween = MathHelper.lerp(delta,bookY,nextBookY);
        matrices.translate(xInbetween, yInbetween, 1984.0);
        float scale = 4.0F;
        matrices.scale(scale, scale, scale);
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
        float bookInbetween = MathHelper.lerp(delta,bookAngle,nextBookAngle);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(bookInbetween));
        float g = MathHelper.lerp(delta, this.pageTurningSpeed, this.nextPageTurningSpeed);
        matrices.translate((double)((1.0F - g) * 0.2F), (double)((1.0F - g) * 0.1F), (double)((1.0F - g) * 0.25F));
        float h = -(1.0F - g) * 90.0F - 90.0F;
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(h));
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));
        float l = MathHelper.lerp(delta, this.pageAngle, this.nextPageAngle) + 0.25F;
        float m = MathHelper.lerp(delta, this.pageAngle, this.nextPageAngle) + 0.75F;
        l = (l - (float)MathHelper.fastFloor((double)l)) * 1.6F - 0.3F;
        m = (m - (float)MathHelper.fastFloor((double)m)) * 1.6F - 0.3F;
        if (l < 0.0F) {
            l = 0.0F;
        }

        if (m < 0.0F) {
            m = 0.0F;
        }

        if (l > 1.0F) {
            l = 1.0F;
        }

        if (m > 1.0F) {
            m = 1.0F;
        }

        this.BOOK_MODEL.setPageAngles(0.0F, l, m, g);
        VertexConsumerProvider.Immediate immediate = VertexConsumerProvider.immediate(Tessellator.getInstance().getBuffer());
        VertexConsumer vertexConsumer = immediate.getBuffer(this.BOOK_MODEL.getLayer(BOOK_TEXTURE));
        this.BOOK_MODEL.render(matrices, vertexConsumer, 15728880, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        immediate.draw();
        matrices.pop();
        RenderSystem.viewport(0, 0, this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight());
        RenderSystem.restoreProjectionMatrix();
        DiffuseLighting.enableGuiDepthLighting();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    public void doTick() {
        ItemStack enchantItem = this.handler.getItemToBeEnchanted();
        if (!ItemStack.areEqual(enchantItem,this.stack) && enchantItem.isIn(ModItemTags.ENCHANTABLE)) {
            stack = enchantItem;
            do {
                this.approximatePageAngle += (float) (this.random.nextInt(4) - this.random.nextInt(4));
            } while (this.nextPageAngle <= this.approximatePageAngle + 1.0F && this.nextPageAngle >= this.approximatePageAngle - 1.0F);
        }

        ++this.ticks;
        this.pageAngle = this.nextPageAngle;
        this.pageTurningSpeed = this.nextPageTurningSpeed;
        this.bookAngle = this.nextBookAngle;
        this.bookX = this.nextBookX;
        this.bookY = this.nextBookY;
        isBookOpen = !this.stack.isEmpty();

        if (isBookOpen) {
            this.nextPageTurningSpeed += 0.2F;
        } else {
            this.nextPageTurningSpeed -= 0.2F;
        }

        this.nextPageTurningSpeed = MathHelper.clamp(this.nextPageTurningSpeed, 0.0F, 1.0F);
        float f = (this.approximatePageAngle - this.nextPageAngle) * 0.4F;
        f = MathHelper.clamp(f, -0.2F, 0.2F);
        this.pageRotationSpeed += (f - this.pageRotationSpeed) * 0.9F;
        this.nextPageAngle += this.pageRotationSpeed;

        if (isBookOpen) {
            this.nextBookAngle = 40.0F;
            this.nextBookX = 0.0F;
            this.nextBookY = 3.2F;
        } else {
            this.nextBookAngle = 70.0F;
            this.nextBookX = 0.1F;
            this.nextBookY = 2.6F;
        }
    }

    @Override
    protected void init() {
        super.init();
        this.titleY -= 1;
        this.BOOK_MODEL = new BookModel(this.client.getEntityModelLoader().getModelPart(EntityModelLayers.BOOK));
    }
}
