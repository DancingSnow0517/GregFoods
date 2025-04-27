package cn.dancingsnow.gregfoods.data;

import cn.dancingsnow.gregfoods.data.lang.GregFoodsLangHandler;
import com.tterrag.registrate.providers.ProviderType;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static cn.dancingsnow.gregfoods.GregFoods.REGISTRATE;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerators {
    public static void init() {
        REGISTRATE.addDataGenerator(ProviderType.LANG, GregFoodsLangHandler::init);
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

    }
}
