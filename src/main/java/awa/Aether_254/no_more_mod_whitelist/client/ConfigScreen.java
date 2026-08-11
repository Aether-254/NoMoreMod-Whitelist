package awa.Aether_254.no_more_mod_whitelist.client;

import awa.Aether_254.no_more_mod_whitelist.ClientConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

import java.util.ArrayList;

public final class ConfigScreen {
    private ConfigScreen() {
    }

    public static void register(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, (mod, parent) -> create(parent));
    }

    private static Screen create(Screen parent) {
        ClientConfig.Data config = ClientConfig.get();
        ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(parent)
            .setTitle(Component.translatable("no_more_mod_whitelist.title"));
        ConfigCategory category = builder.getOrCreateCategory(
            Component.translatable("no_more_mod_whitelist.category.reported_mods"));
        ConfigEntryBuilder entries = builder.entryBuilder();

        category.addEntry(entries.startStrList(
                Component.translatable("no_more_mod_whitelist.added_mods"),
                new ArrayList<>(config.addedMods))
            .setDefaultValue(ArrayList::new)
            .setTooltip(Component.translatable("no_more_mod_whitelist.added_mods.tooltip"))
            .setSaveConsumer(value -> config.addedMods = new ArrayList<>(value))
            .build());
        category.addEntry(entries.startStrList(
                Component.translatable("no_more_mod_whitelist.removed_mods"),
                new ArrayList<>(config.removedMods))
            .setDefaultValue(ArrayList::new)
            .setTooltip(Component.translatable("no_more_mod_whitelist.removed_mods.tooltip"))
            .setSaveConsumer(value -> config.removedMods = new ArrayList<>(value))
            .build());

        builder.setSavingRunnable(() -> {
            ClientConfig.save();
        });
        return builder.build();
    }
}
