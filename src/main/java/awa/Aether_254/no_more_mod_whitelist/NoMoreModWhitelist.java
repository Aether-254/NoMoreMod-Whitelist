package awa.Aether_254.no_more_mod_whitelist;

import awa.Aether_254.no_more_mod_whitelist.client.ConfigScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(NoMoreModWhitelist.MOD_ID)
public final class NoMoreModWhitelist {
    public static final String MOD_ID = "no_more_mod_whitelist";
    public static final Logger LOGGER = LoggerFactory.getLogger("NoMoreMod-Whitelist");

    public NoMoreModWhitelist(ModContainer container) {
        ClientConfig.load();
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ConfigScreen.register(container);
        }
    }
}
