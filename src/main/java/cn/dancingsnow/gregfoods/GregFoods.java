package cn.dancingsnow.gregfoods;

import cn.dancingsnow.gregfoods.data.ModDataGenerators;
import cn.dancingsnow.gregfoods.foundation.ModBlocks;
import cn.dancingsnow.gregfoods.foundation.ModMachines;
import cn.dancingsnow.gregfoods.foundation.ModRecipeTypes;
import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;
import com.gregtechceu.gtceu.api.recipe.GTRecipeType;
import com.gregtechceu.gtceu.api.registry.registrate.GTRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(GregFoods.MOD_ID)
public class GregFoods {
    public static final String MOD_ID = "gregfoods";
    public static final GTRegistrate REGISTRATE = GTRegistrate.create(MOD_ID);

    public GregFoods() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        REGISTRATE.registerEventListeners(bus);

        bus.addGenericListener(GTRecipeType.class, GregFoods::registerRecipeTypes);
        bus.addGenericListener(MachineDefinition.class, GregFoods::registerMachineDefinitions);

        ModDataGenerators.init();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static void registerRecipeTypes(GTCEuAPI.RegisterEvent<ResourceLocation, GTRecipeType> event) {
        ModRecipeTypes.init();
    }

    public static void registerMachineDefinitions(GTCEuAPI.RegisterEvent<ResourceLocation, MachineDefinition> event) {
        ModBlocks.init();
        ModMachines.init();
    }
}
