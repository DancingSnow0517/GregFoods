package cn.dancingsnow.gregfoods.client.renderer;

import com.gregtechceu.gtceu.api.blockentity.MetaMachineBlockEntity;
import com.gregtechceu.gtceu.api.capability.recipe.ItemRecipeCapability;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.BlockPattern;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.content.Content;
import com.gregtechceu.gtceu.client.renderer.machine.WorkableCasingMachineRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalProcessorRenderer extends WorkableCasingMachineRenderer {
    public static final MethodHandle MT_setActualRelativeOffset;
    public static final MethodHandle MT_getHurtSound;

    static {
        try {
            Method mt = BlockPattern.class.getDeclaredMethod(
                "setActualRelativeOffset",
                int.class,
                int.class,
                int.class,
                Direction.class,
                Direction.class,
                boolean.class
            );
            mt.setAccessible(true);
            MT_setActualRelativeOffset = MethodHandles.lookup().unreflect(mt);
            Method m_getHurtSound = LivingEntity.class.getDeclaredMethod(
                FMLEnvironment.production ? "m_7975_" : "getHurtSound",
                DamageSource.class
            );
            m_getHurtSound.setAccessible(true);
            MT_getHurtSound = MethodHandles.lookup().unreflect(m_getHurtSound);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private final Map<EntityType<?>, Entity> entityCacheMap = new HashMap<>();
    private long t = System.currentTimeMillis();

    public AnimalProcessorRenderer(ResourceLocation baseCasing, ResourceLocation workableModel) {
        super(baseCasing, workableModel);
    }

    @Override
    public boolean hasTESR(BlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean isGlobalRenderer(BlockEntity blockEntity) {
        return true;
    }

    @Override
    public void render(
        BlockEntity blockEntity,
        float partialTicks,
        PoseStack poseStack,
        MultiBufferSource buffer,
        int combinedLight,
        int combinedOverlay
    ) {
        if (blockEntity instanceof MetaMachineBlockEntity be) {
            if (be.getMetaMachine() instanceof CoilWorkableElectricMultiblockMachine machine) {
                GTRecipe recipe = machine.recipeLogic.getLastRecipe();
                if (!machine.isActive()) return;
                if (recipe == null) return;
                List<Content> contents = recipe.getInputContents(ItemRecipeCapability.CAP);
                for (Content content : contents) {
                    Ingredient ingredient = ItemRecipeCapability.CAP.of(content.content);
                    ItemStack[] items = ingredient.getItems();
                    for (ItemStack item : items) {
                        if (item.getItem() instanceof SpawnEggItem spawnEggItem) {
                            try {
                                renderEntity(blockEntity, poseStack, buffer, machine, item, spawnEggItem);
                            }catch (Throwable e) {
                                e.printStackTrace();
                                return;
                            }
                        }
                    }
                }
            }
        }
    }

    private void renderEntity(
        BlockEntity blockEntity,
        PoseStack poseStack,
        MultiBufferSource buffer,
        CoilWorkableElectricMultiblockMachine machine,
        ItemStack item,
        SpawnEggItem spawnEggItem
    ) throws Throwable {
        EntityType<?> ty = spawnEggItem.getType(item.getTag());
        Entity entity;
        if (!entityCacheMap.containsKey(ty)) {
            entity = ty.create(blockEntity.getLevel());
            entityCacheMap.put(ty, entity);
        } else {
            entity = entityCacheMap.get(ty);
        }

        if (!(entity instanceof LivingEntity livingEntity)) return;
        poseStack.pushPose();
        BlockPos actual;
        try {
            actual = (BlockPos) MT_setActualRelativeOffset.invoke(
                machine.getPattern(),
                0,
                1,
                -1,
                machine.getFrontFacing(),
                machine.getUpwardsFacing(),
                machine.isFlipped()
            );
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        poseStack.translate(actual.getX() + 0.5, actual.getY() + 0.01, actual.getZ() + 0.5);
        float bbMax = (float) Math.max(
            entity.getBoundingBox().getXsize(),
            Math.max(
                entity.getBoundingBox().getYsize(),
                entity.getBoundingBox().getZsize()
            )
        );
        if (System.currentTimeMillis() - t >= 1000) {
            t = System.currentTimeMillis();
        }
        int part = Math.toIntExact((System.currentTimeMillis() - t) % 900);
        if (part < 140) {
            livingEntity.hurtTime = 0;
            livingEntity.hurtDuration = 0;
            entity.hurtMarked = false;
        } else {
            entity.animateHurt(0);
            if (!entity.hurtMarked && !machine.isMuffled() && !Minecraft.getInstance().isPaused()) {
                SoundEvent soundEvent = (SoundEvent) MT_getHurtSound.invoke(
                    livingEntity,
                    livingEntity.level().damageSources().hotFloor()
                );
                livingEntity.level().playSound(
                    Minecraft.getInstance().player,
                    machine.getPos().getX(),
                    machine.getPos().getY(),
                    machine.getPos().getZ(),
                    soundEvent,
                    livingEntity.getSoundSource(),
                    0.5f,
                    (livingEntity.getRandom().nextFloat() - livingEntity.getRandom().nextFloat()) * 0.2F + 1.0F
                );
            }
            entity.hurtMarked = true;
        }

        float scale = bbMax <= 1 ? 1 : (1f / bbMax);
        poseStack.scale(scale, scale, scale);
        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        dispatcher.render(
            entity,
            0,
            0,
            0,
            0,
            0,
            poseStack,
            buffer,
            LightTexture.FULL_BLOCK
        );
        poseStack.popPose();
    }


}
