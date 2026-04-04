package kono.hotornotgt.common.items;

import gregtech.api.items.toolitem.behavior.IToolBehavior;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TongsBehavior implements IToolBehavior {

    public static final TongsBehavior INSTANCE = new TongsBehavior();

    @Override
    public void addInformation(@NotNull ItemStack stack, @Nullable World world, @NotNull List<String> tooltip,
                               @NotNull ITooltipFlag flag) {
        tooltip.add(I18n.format("item.hotornot.hot_item.tooltip"));
    }
}
