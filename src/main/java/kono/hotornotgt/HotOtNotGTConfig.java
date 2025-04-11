package kono.hotornotgt;

import net.minecraftforge.common.config.Config;

import kono.hotornotgt.api.util.ModValues;

@Config(modid = ModValues.modId)
public class HotOtNotGTConfig {

    @Config.Name("Feature Setting")
    @Config.RequiresMcRestart
    public static Feature feature = new Feature();

    @Config.Name("NanoArmor Protection Setting")
    @Config.RequiresMcRestart
    public static ProtectionNanoSuite nano = new ProtectionNanoSuite();

    @Config.Name("QuarkTechSuit Protection Setting")
    @Config.RequiresMcRestart
    public static ProtectionQuarkTechSuite quark = new ProtectionQuarkTechSuite();

    public static class Feature {

        @Config.Comment({ "Whether NanoMuscleSuite can protect the player from Hot ot Not effects", "Default: true" })
        public boolean protectNano = true;

        @Config.Comment({ "Whether QuarkTechSuite can protect the player from Hot ot Not effects", "Default: true" })
        public boolean protectQuark = true;
    }

    public static class ProtectionNanoSuite {

        @Config.Comment({ "Minimum number of NanoSuite equipped that can protect the player from effects",
                "Default: 4" })
        @Config.RangeInt(min = 1, max = 4)
        public int minimumEquippedNano = 4;

        @Config.Comment({ "Whether Chestplate should be \"Advanced\" only or not", "If false, both can be protected.",
                "Default: true" })
        public boolean requiredAdvancedNanoSuite = true;

        @Config.Comment({ "Whether the helmet can protect the player from effects", "Default: true" })
        public boolean protectHelmet = true;

        @Config.Comment({ "Whether the chestplate can protect the player from effects", "Default: true" })
        public boolean protectChest = true;

        @Config.Comment({ "Whether the leggings can protect the player from effects", "Default: true" })
        public boolean protectLegs = true;

        @Config.Comment({ "Whether the boots can protect the player from effects", "Default: true" })
        public boolean protectBoots = true;

        @Config.Comment({ "How much EU consumption gets applied to the protect per check", "Default: 1000" })
        public int energyConsumptionNano = 1000;
    }

    public static class ProtectionQuarkTechSuite {

        @Config.Comment({ "Minimum number of QuarkTechSuite equipped that can protect the player from effects",
                "Default: 1" })
        @Config.RangeInt(min = 1, max = 4)
        public int minimumEquippedQuark = 1;

        @Config.Comment({ "Whether Chestplate should be \"Advanced\" only or not", "If false, both can be protected.",
                "Default: false" })
        public boolean requiredAdvancedQuarkSuite = false;

        @Config.Comment({ "Whether the helmet can protect the player from effects", "Default: true" })
        public boolean protectHelmet = true;

        @Config.Comment({ "Whether the chestplate can protect the player from effects", "Default: true" })
        public boolean protectChest = true;

        @Config.Comment({ "Whether the leggings can protect the player from effects", "Default: true" })
        public boolean protectLegs = true;

        @Config.Comment({ "Whether the boots can protect the player from effects", "Default: true" })
        public boolean protectBoots = true;

        @Config.Comment({ "How much EU consumption gets applied to the protect per check", "Default: 1000" })
        public int energyConsumptionQuark = 10000;
    }
}
