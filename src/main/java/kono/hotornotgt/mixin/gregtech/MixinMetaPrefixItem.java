package kono.hotornotgt.mixin.gregtech;

import static com.buuz135.hotornot.server.ServerTick.damageProtectionItem;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import gregtech.api.damagesources.DamageSources;
import gregtech.api.items.armor.ArmorMetaItem;
import gregtech.api.items.materialitem.MetaPrefixItem;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;

@Mixin(value = MetaPrefixItem.class, remap = false)
public class MixinMetaPrefixItem {

    @Final
    @Shadow
    private OrePrefix prefix;

    @Inject(
            method = "onUpdate",
            at = @At(
                     value = "INVOKE",
                     target = "Lnet/minecraft/entity/EntityLivingBase;getItemStackFromSlot(Lnet/minecraft/inventory/EntityEquipmentSlot;)Lnet/minecraft/item/ItemStack;",
                     shift = At.Shift.AFTER),
            cancellable = true)
    public void onUpdateMixin(ItemStack itemStack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected,
                              CallbackInfo ci) {
        EntityLivingBase entity = (EntityLivingBase) entityIn;
        MetaPrefixItem metaPrefixItem = (MetaPrefixItem) (Object) this;
        Material material = metaPrefixItem.getMaterial(itemStack);

        float heatDamage = prefix.heatDamageFunction.apply(material.getBlastTemperature());
        ItemStack armor = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
        if (!armor.isEmpty() && armor.getItem() instanceof ArmorMetaItem<?>) {
            ArmorMetaItem<?>.ArmorMetaValueItem metaValueItem = ((ArmorMetaItem<?>) armor.getItem()).getItem(armor);
            if (metaValueItem != null) heatDamage *= metaValueItem.getArmorLogic().getHeatResistance();
        }
        if (heatDamage > 0.0) {
            if (entity instanceof EntityPlayer) {
                if (!damageProtectionItem((EntityPlayer) entity)) {
                    entity.attackEntityFrom(DamageSources.getHeatDamage().setDamageBypassesArmor(), heatDamage);
                }
            } else {
                entity.attackEntityFrom(DamageSources.getHeatDamage().setDamageBypassesArmor(), heatDamage);
            }
        } else if (heatDamage < 0) {
            if (entity instanceof EntityPlayer) {
                if (!damageProtectionItem((EntityPlayer) entity)) {
                    entity.attackEntityFrom(DamageSources.getFrostDamage().setDamageBypassesArmor(), -heatDamage);
                }
            } else {
                entity.attackEntityFrom(DamageSources.getFrostDamage().setDamageBypassesArmor(), -heatDamage);
            }
        }
        ci.cancel();
    }
}
