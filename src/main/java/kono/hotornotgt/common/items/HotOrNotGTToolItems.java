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
import gregtech.api.items.toolitem.ToolHelper;
import gregtech.api.unification.OreDictUnifier;

import kono.hotornotgt.api.util.ModValues;

public class HotOrNotGTToolItems {

    private static final List<IGTTool> TOOLS = new ArrayList<>();

    public static IGTTool TONGS;
    public static IGTTool TONGS_LV;
    public static IGTTool TONGS_HV;
    public static IGTTool TONGS_IV;

    private HotOrNotGTToolItems() {}

    public static List<IGTTool> getAllTools() {
        return TOOLS;
    }

    public static void init() {
        TONGS = register(ItemGTTool.Builder.of(ModValues.modId, "tongs")
                .toolStats(b -> b.cannotAttack().behaviors(TongsBehavior.INSTANCE))
                .oreDict("toolTongs")
                .toolClasses("tongs")
                .build());
        TONGS_LV = register(ItemGTTool.Builder.of(ModValues.modId, "tongs_lv")
                .toolStats(b -> b.durabilityMultiplier(1.5F).cannotAttack().behaviors(TongsBehavior.INSTANCE)
                        .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_LV))
                .oreDict("toolTongs")
                .toolClasses("tongs")
                .electric(GTValues.LV)
                .build());
        TONGS_HV = register(ItemGTTool.Builder.of(ModValues.modId, "tongs_hv")
                .toolStats(b -> b.durabilityMultiplier(2.0F).cannotAttack().behaviors(TongsBehavior.INSTANCE)
                        .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_HV))
                .oreDict("toolTongs")
                .toolClasses("tongs")
                .electric(GTValues.HV)
                .build());
        TONGS_IV = register(ItemGTTool.Builder.of(ModValues.modId, "tongs_iv")
                .toolStats(b -> b.durabilityMultiplier(4.0F).cannotAttack().behaviors(TongsBehavior.INSTANCE)
                        .brokenStack(ToolHelper.SUPPLY_POWER_UNIT_IV))
                .oreDict("toolTongs")
                .toolClasses("tongs")
                .electric(GTValues.IV)
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
