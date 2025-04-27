package cn.dancingsnow.gregfoods.foundation;

import cn.dancingsnow.gregfoods.GregFoods;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.recipe.GTRecipeSerializer;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.data.GTSoundEntries;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipeTypes {

    public static final GTRecipeType ANIMAL_PROCESS = register("animal_process", "electric")
        .setMaxIOSize(1, 4, 0, 0)
        .setEUIO(IO.IN)
        .setSound(GTSoundEntries.FURNACE);

    public static GTRecipeType register(String name, String group, RecipeType<?>... proxyRecipes) {
        GTRecipeType recipeType = new GTRecipeType(GregFoods.id(name), group, proxyRecipes);
        ForgeRegistries.RECIPE_TYPES.register(recipeType.registryName, recipeType);
        ForgeRegistries.RECIPE_SERIALIZERS.register(recipeType.registryName, new GTRecipeSerializer());
        GTRegistries.RECIPE_TYPES.register(recipeType.registryName, recipeType);
        return recipeType;
    }

    public static void init() {

    }
}
