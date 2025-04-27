package cn.dancingsnow.gregfoods;

import cn.dancingsnow.gregfoods.data.ModRecipes;
import com.gregtechceu.gtceu.api.addon.GTAddon;
import com.gregtechceu.gtceu.api.addon.IGTAddon;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.minecraft.data.recipes.FinishedRecipe;

import java.util.function.Consumer;

@GTAddon
public class GregFoodsAddon implements IGTAddon {
    @Override
    public GTRegistrate getRegistrate() {
        return GregFoods.REGISTRATE;
    }

    @Override
    public void initializeAddon() {

    }

    @Override
    public String addonModId() {
        return GregFoods.MOD_ID;
    }

    @Override
    public void addRecipes(Consumer<FinishedRecipe> provider) {
        ModRecipes.init(provider);
    }
}
