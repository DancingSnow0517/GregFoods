package cn.dancingsnow.gregfoods.foundation;

import com.gregtechceu.gtceu.api.machine.MetaMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.recipe.GTRecipe;
import com.gregtechceu.gtceu.api.recipe.OverclockingLogic;
import com.gregtechceu.gtceu.api.recipe.RecipeHelper;
import com.gregtechceu.gtceu.api.recipe.modifier.ModifierFunction;
import com.gregtechceu.gtceu.api.recipe.modifier.RecipeModifier;

public class ModRecipeModifiers {
    public static ModifierFunction animalProcessOverclock(MetaMachine machine, GTRecipe recipe) {
        if (machine instanceof CoilWorkableElectricMultiblockMachine coilMachine) {
            if (RecipeHelper.getRecipeEUtTier(recipe) > coilMachine.getTier()) {
                return ModifierFunction.NULL;
            }
            int tier = coilMachine.getCoilTier();
            double durationMultiplier = 2f / (tier + 1);
            ModifierFunction modifierFunction = ModifierFunction.builder()
                .durationMultiplier(durationMultiplier)
                .build();

            ModifierFunction modifier = OverclockingLogic.NON_PERFECT_OVERCLOCK_SUBTICK.getModifier(machine, recipe, coilMachine.getOverclockVoltage());
            return modifier.andThen(modifierFunction);

        } else {
            return RecipeModifier.nullWrongType(CoilWorkableElectricMultiblockMachine.class, machine);
        }
    }
}
