package cn.dancingsnow.gregfoods.data.lang;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class GregFoodsLangHandler {
    public static void init(RegistrateLangProvider provider) {
        provider.add("gregfoods.multiblock.animal_process.efficiency", "Efficiency: %s");
        provider.add("block.gregfoods.animal_process.desc.0", "No animals was hurt by this machine.");
        provider.add("gregfoods.animal_process", "Animal Process");
    }
}
