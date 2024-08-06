package dev.su5ed.sinytra.connectorextras.energybridge;

import com.google.common.primitives.Ints;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

@Mod(EnergyBridge.MODID)
public class EnergyBridge {
    public static final String MODID = "connectorextras_energy_bridge";

    public EnergyBridge(IEventBus bus, ModContainer container) {
        if (ModList.get().isLoaded("team_reborn_energy")) {
            EnergyBridgeSetup.init(bus);
        }
        container.registerConfig(ModConfig.Type.COMMON, EnergyBridgeConfig.COMMON_SPEC);
    }

    public static int convertFabricToForgeEnergy(long amount) {
        return convertForward(amount, EnergyBridgeConfig.COMMON.fabricToForgeEnergy);
    }

    public static long unConvertFabricToForgeEnergy(int amount) {
        return convertBackwards(amount, EnergyBridgeConfig.COMMON.fabricToForgeEnergy);
    }

    public static long convertForgeToFabricEnergy(int amount) {
        return convertBackwards(amount, EnergyBridgeConfig.COMMON.forgeToFabricEnergy);
    }

    public static int unConvertForgeToFabricEnergy(long amount) {
        return convertForward(amount, EnergyBridgeConfig.COMMON.forgeToFabricEnergy);
    }

    public static int convertForward(long amount, ModConfigSpec.IntValue ratio) {
        if (amount > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (amount < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        long fe = ratio.get() * amount;
        return Ints.saturatedCast(fe);
    }

    private static long convertBackwards(int amount, ModConfigSpec.IntValue ratio) {
        int value = ratio.get();
        return value == 0 ? 0 : amount / value;
    }
}
