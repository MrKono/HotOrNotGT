package kono.hotornotgt.client;

import static kono.hotornotgt.api.util.ModUtils.checkArmor;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import gregtech.common.items.MetaItems;

import kono.hotornotgt.HotOtNotGTConfig;
import kono.hotornotgt.common.CommonProxy;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {}

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        int i, j;
        List<ItemStack> nano = new ArrayList<>();
        nano.add(MetaItems.NANO_HELMET.getStackForm());
        nano.add(MetaItems.NANO_CHESTPLATE_ADVANCED.getStackForm());
        nano.add(MetaItems.NANO_LEGGINGS.getStackForm());
        nano.add(MetaItems.NANO_BOOTS.getStackForm());
        if (!HotOtNotGTConfig.nano.requiredAdvancedNanoSuite) nano.add(MetaItems.NANO_CHESTPLATE.getStackForm());
        List<ItemStack> quark = new ArrayList<>();
        quark.add(MetaItems.QUANTUM_HELMET.getStackForm());
        quark.add(MetaItems.QUANTUM_CHESTPLATE_ADVANCED.getStackForm());
        quark.add(MetaItems.QUANTUM_LEGGINGS.getStackForm());
        quark.add(MetaItems.QUANTUM_BOOTS.getStackForm());
        if (!HotOtNotGTConfig.quark.requiredAdvancedQuarkSuite) quark.add(MetaItems.QUANTUM_CHESTPLATE.getStackForm());
        if (HotOtNotGTConfig.feature.protectNano) {
            for (ItemStack armor : nano) {
                if (checkArmor(stack, armor, 0)) {
                    j = HotOtNotGTConfig.nano.minimumEquippedNano;
                    event.getToolTip()
                            .add(I18n.format(j == 4 ? "hotornotgt.full_set" : j == 1 ? "hotornotgt.not_full_set" : ""));
                    i = HotOtNotGTConfig.nano.energyConsumptionNano;
                    event.getToolTip().add(I18n.format("hotornotgt.required_energy", i));
                }
            }
        }
        if (HotOtNotGTConfig.feature.protectQuark) {
            for (ItemStack armor : quark) {
                if (checkArmor(stack, armor, 0)) {
                    j = HotOtNotGTConfig.quark.minimumEquippedQuark;
                    event.getToolTip()
                            .add(I18n.format(j == 4 ? "hotornotgt.full_set" : j == 1 ? "hotornotgt.not_full_set" : ""));
                    i = HotOtNotGTConfig.quark.energyConsumptionQuark;
                    event.getToolTip().add(I18n.format("hotornotgt.required_energy", i));
                }
            }
        }
    }
}
