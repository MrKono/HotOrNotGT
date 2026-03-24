package kono.hotornotgt.common.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import gregtech.api.GTValues;
import gregtech.api.items.toolitem.IGTTool;
import gregtech.api.items.toolitem.ItemGTTool;
import gregtech.api.unification.OreDictUnifier;

import kono.hotornotgt.api.util.ModValues;

public class HotOrNotGTToolItems {

    private static final List<IGTTool> TOOLS = new ArrayList<>();

    public static IGTTool TONGS;

    private HotOrNotGTToolItems() {}

    public static List<IGTTool> getAllTools() {
        return TOOLS;
    }

    public static void init() {
        TONGS = register(ItemGTTool.Builder.of(ModValues.modId, "tongs")
                .toolStats(b -> b.durabilityMultiplier(0.5F).cannotAttack())
                .oreDict("toolTongs")
                .toolClasses("tongs")
                .build());
    }

    public static IGTTool register(IGTTool tool) {
        TOOLS.add(tool);
        return tool;
    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        TOOLS.forEach(tool -> ModelLoader.setCustomModelResourceLocation(tool.get(), 0, tool.getModelLocation()));
    }

    @SideOnly(Side.CLIENT)
    public static void registerColors(ItemColors itemColors) {
        TOOLS.forEach(tool -> itemColors.registerItemColorHandler(tool::getColor, tool.get()));
    }

    public static void registerOreDict() {
        TOOLS.forEach(tool -> {
            final ItemStack stack = new ItemStack(tool.get(), 1, GTValues.W);
            if (tool.getOreDictName() != null) {
                OreDictUnifier.registerOre(stack, tool.getOreDictName());
            }
            tool.getSecondaryOreDicts().forEach(oreDict -> OreDictUnifier.registerOre(stack, oreDict));
        });
    }
}
