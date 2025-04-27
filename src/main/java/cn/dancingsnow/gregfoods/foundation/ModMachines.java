package cn.dancingsnow.gregfoods.foundation;

import cn.dancingsnow.gregfoods.GregFoods;
import cn.dancingsnow.gregfoods.client.renderer.AnimalProcessorRenderer;
import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.common.data.GTBlocks;
import com.gregtechceu.gtceu.common.data.GTRecipeModifiers;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import static cn.dancingsnow.gregfoods.GregFoods.REGISTRATE;

public class ModMachines {
    public static final MultiblockMachineDefinition ANIMAL_PROCESS = REGISTRATE
        .multiblock("animal_process", CoilWorkableElectricMultiblockMachine::new)
        .tooltips(Component.translatable("block.gregfoods.animal_process.desc.0").withStyle(ChatFormatting.GRAY))
        .rotationState(RotationState.NON_Y_AXIS)
        .recipeType(ModRecipeTypes.ANIMAL_PROCESS)
        .recipeModifiers(GTRecipeModifiers.PARALLEL_HATCH, ModRecipeModifiers::animalProcessOverclock)
        .appearanceBlock(ModBlocks.FARM_CASING)
        .pattern(definition -> FactoryBlockPattern.start()
            .aisle("XXX", "XGX", "XXX")
            .aisle("XCX", "G#G", "XMX")
            .aisle("XSX", "XGX", "XXX")
            .where('S', Predicates.controller(Predicates.blocks(definition.get())))
            .where('X', Predicates.blocks(ModBlocks.FARM_CASING.get())
                .setMinGlobalLimited(12)
                .or(Predicates.autoAbilities(definition.getRecipeTypes()))
                .or(Predicates.autoAbilities(true, false, true))
            )
            .where('G', Predicates.blocks(GTBlocks.CASING_TEMPERED_GLASS.get()))
            .where('M', Predicates.abilities(PartAbility.MUFFLER))
            .where('C', Predicates.heatingCoils())
            .where('#', Predicates.air())
            .build()
        )
        .renderer(() -> new AnimalProcessorRenderer(GregFoods.id("block/casings/farm_casing"), GTCEu.id("block/multiblock/gcym/large_assembler")))
        .hasTESR(true)
        .additionalDisplay(((controller, components) -> {
            if (controller instanceof CoilWorkableElectricMultiblockMachine coilMachine && controller.isFormed()) {
                components.add(Component.translatable(
                    "gregfoods.multiblock.animal_process.efficiency",
                    Component.literal((0.5 + coilMachine.getCoilTier() * 0.5) * 100 + "%").withStyle(ChatFormatting.LIGHT_PURPLE)
                ));
            }
        }))
        .register();

    public static void init() {

    }
}
