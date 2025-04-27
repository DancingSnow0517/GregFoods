package cn.dancingsnow.gregfoods.foundation;

import cn.dancingsnow.gregfoods.GregFoods;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.common.data.GTModels;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import static cn.dancingsnow.gregfoods.GregFoods.REGISTRATE;

public class ModBlocks {
    @SuppressWarnings("removal")
    public static final BlockEntry<Block> FARM_CASING = REGISTRATE
        .block("farm_casing", Block::new)
        .initialProperties(() -> Blocks.OAK_PLANKS)
        .properties(p -> p.isValidSpawn((blockState, blockGetter, blockPos, entityType) -> false))
        .addLayer(() -> RenderType::cutoutMipped)
        .blockstate(GTModels.cubeAllModel("farm_casing", GregFoods.id("block/casings/farm_casing")))
        .tag(GTToolType.WRENCH.harvestTags.get(0), BlockTags.MINEABLE_WITH_AXE)
        .simpleItem()
        .register();

    public static void init() {

    }
}
