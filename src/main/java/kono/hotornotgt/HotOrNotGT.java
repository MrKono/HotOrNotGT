package kono.hotornotgt;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.buuz135.hotornot.HotOrNot;

import gregtech.GTInternalTags;

import kono.hotornotgt.common.CommonProxy;

@Mod(modid = ModValues.modId,
     version = Tags.VERSION,
     name = ModValues.modName,
     acceptedMinecraftVersions = "[1.12.2]",
     dependencies = "required-after:mixinbooter@[9.1,);" + GTInternalTags.DEP_VERSION_STRING + "required-after:" +
             HotOrNot.MOD_ID + ";")
public class HotOrNotGT {

    public static final Logger LOGGER = LogManager.getLogger(ModValues.modId);

    @SidedProxy(modId = ModValues.modId,
                clientSide = "kono.hotornotgt.client.ClientProxy",
                serverSide = "kono.hotornotgt.common.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static HotOrNotGT instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        proxy.preInit(e);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

    @SubscribeEvent
    public static void syncConfigValues(ConfigChangedEvent.OnConfigChangedEvent e) {
        if (e.getModID().equals(Tags.MODID)) {
            ConfigManager.sync(Tags.MODID, Config.Type.INSTANCE);
        }
    }
}
