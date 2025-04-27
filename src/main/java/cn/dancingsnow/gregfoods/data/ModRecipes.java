package cn.dancingsnow.gregfoods.data;

import cn.dancingsnow.gregfoods.GregFoods;
import cn.dancingsnow.gregfoods.foundation.ModBlocks;
import cn.dancingsnow.gregfoods.foundation.ModMachines;
import cn.dancingsnow.gregfoods.foundation.ModRecipeTypes;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.gregtechceu.gtceu.common.data.GTMaterials;
import com.gregtechceu.gtceu.common.data.GTRecipeTypes;
import com.gregtechceu.gtceu.data.recipe.CustomTags;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.function.Consumer;

public class ModRecipes {
    public static void init(Consumer<FinishedRecipe> provider) {
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.FARM_CASING, 2)
            .pattern("AHA")
            .pattern("AFA")
            .pattern("AWA")
            .define('A', TagPrefix.plate.getItemTags(GTMaterials.TreatedWood)[0])
            .define('F', TagPrefix.frameGt.getItemTags(GTMaterials.TreatedWood)[0])
            .define('H', GTToolType.HARD_HAMMER.itemTags.get(0))
            .define('W', GTToolType.WRENCH.itemTags.get(0))
            .unlockedBy("has_item", RegistrateRecipeProvider.has(TagPrefix.frameGt.getItemTags(GTMaterials.TreatedWood)[0]))
            .save(provider);

        GTRecipeTypes.ASSEMBLER_RECIPES.recipeBuilder(GregFoods.id("farm_casing"))
            .inputItems(TagPrefix.plate, GTMaterials.TreatedWood, 6)
            .inputItems(TagPrefix.frameGt, GTMaterials.TreatedWood)
            .outputItems(ModBlocks.FARM_CASING, 2)
            .circuitMeta(6)
            .EUt(GTValues.VA[GTValues.LV])
            .duration(50)
            .save(provider);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModMachines.ANIMAL_PROCESS.getBlock())
            .pattern("WSW")
            .pattern("RCV")
            .pattern("WXW")
            .define('W', TagPrefix.cableGtDouble.getItemFromTable(GTMaterials.Platinum).get())
            .define('S', TagPrefix.toolHeadBuzzSaw.getItemFromTable(GTMaterials.TungstenCarbide).get())
            .define('R', GTItems.ROBOT_ARM_IV)
            .define('C', ModBlocks.FARM_CASING)
            .define('V', GTItems.CONVEYOR_MODULE_IV)
            .define('X', CustomTags.IV_CIRCUITS)
            .unlockedBy("has_item", RegistrateRecipeProvider.has(ModBlocks.FARM_CASING))
            .save(provider);

        ModRecipeTypes.ANIMAL_PROCESS.recipeBuilder(GregFoods.id("chicken"))
            .notConsumable(Items.CHICKEN_SPAWN_EGG)
            .outputItems(new ItemStack(Items.COOKED_CHICKEN, 16))
            .EUt(GTValues.VA[GTValues.LV])
            .duration(64 * 20)
            .save(provider);

        ModRecipeTypes.ANIMAL_PROCESS.recipeBuilder(GregFoods.id("cow"))
            .notConsumable(Items.COW_SPAWN_EGG)
            .outputItems(new ItemStack(Items.COOKED_BEEF, 16))
            .EUt(GTValues.VA[GTValues.LV])
            .duration(64 * 20)
            .save(provider);

        ModRecipeTypes.ANIMAL_PROCESS.recipeBuilder(GregFoods.id("pig"))
            .notConsumable(Items.PIG_SPAWN_EGG)
            .outputItems(new ItemStack(Items.COOKED_PORKCHOP, 16))
            .EUt(GTValues.VA[GTValues.LV])
            .duration(64 * 20)
            .save(provider);

        ModRecipeTypes.ANIMAL_PROCESS.recipeBuilder(GregFoods.id("sheep"))
            .notConsumable(Items.SHEEP_SPAWN_EGG)
            .outputItems(new ItemStack(Items.COOKED_MUTTON, 16))
            .EUt(GTValues.VA[GTValues.LV])
            .duration(64 * 20)
            .save(provider);

        ModRecipeTypes.ANIMAL_PROCESS.recipeBuilder(GregFoods.id("cod"))
            .notConsumable(Items.COD_SPAWN_EGG)
            .outputItems(new ItemStack(Items.COOKED_COD, 16))
            .EUt(GTValues.VA[GTValues.LV])
            .duration(64 * 20)
            .save(provider);

        ModRecipeTypes.ANIMAL_PROCESS.recipeBuilder(GregFoods.id("rabbit"))
            .notConsumable(Items.RABBIT_SPAWN_EGG)
            .outputItems(new ItemStack(Items.COOKED_RABBIT, 16))
            .EUt(GTValues.VA[GTValues.LV])
            .duration(64 * 20)
            .save(provider);

        ModRecipeTypes.ANIMAL_PROCESS.recipeBuilder(GregFoods.id("salmon"))
            .notConsumable(Items.SALMON_SPAWN_EGG)
            .outputItems(new ItemStack(Items.COOKED_SALMON, 16))
            .EUt(GTValues.VA[GTValues.LV])
            .duration(64 * 20)
            .save(provider);

        ModRecipeTypes.ANIMAL_PROCESS.recipeBuilder(GregFoods.id("zombie"))
            .notConsumable(Items.ZOMBIE_SPAWN_EGG)
            .chancedOutput(new ItemStack(Items.BAKED_POTATO, 16), 200, 100)
            .chancedOutput(new ItemStack(Items.CARROT, 16), 200, 100)
            .EUt(GTValues.VA[GTValues.LV])
            .duration(64 * 20)
            .save(provider);

        ModRecipeTypes.ANIMAL_PROCESS.recipeBuilder(GregFoods.id("villager"))
            .notConsumable(Items.VILLAGER_SPAWN_EGG)
            .chancedOutput(new ItemStack(Items.GOLDEN_CARROT, 16), 10, 10)
            .chancedOutput(new ItemStack(Items.BREAD, 16), 100, 20)
            .EUt(GTValues.VA[GTValues.IV])
            .duration(128 * 20)
            .save(provider);
    }
}
