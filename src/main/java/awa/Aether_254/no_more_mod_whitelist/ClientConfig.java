package awa.Aether_254.no_more_mod_whitelist;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.neoforged.fml.loading.FMLPaths;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class ClientConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FMLPaths.CONFIGDIR.get().resolve("no_more_mod_whitelist.json");
    private static Data data = new Data();

    private ClientConfig() {
    }

    public static Data get() {
        return data;
    }

    public static void load() {
        if (!Files.isRegularFile(PATH)) {
            save();
            return;
        }
        try (Reader reader = Files.newBufferedReader(PATH)) {
            Data loaded = GSON.fromJson(reader, Data.class);
            data = loaded == null ? new Data() : loaded;
            data.sanitize();
        } catch (Exception exception) {
            NoMoreModWhitelist.LOGGER.error("Could not load {}", PATH, exception);
            data = new Data();
        }
    }

    public static void save() {
        data.sanitize();
        try {
            Files.createDirectories(PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(PATH)) {
                GSON.toJson(data, writer);
            }
        } catch (IOException exception) {
            NoMoreModWhitelist.LOGGER.error("Could not save {}", PATH, exception);
        }
    }

    public static final class Data {
        public List<String> addedMods = new ArrayList<>();
        public List<String> removedMods = new ArrayList<>();

        private void sanitize() {
            addedMods = sanitizeList(addedMods);
            removedMods = sanitizeList(removedMods);
        }

        private static List<String> sanitizeList(List<String> values) {
            if (values == null) {
                return new ArrayList<>();
            }
            return values.stream()
                .filter(value -> value != null && !value.isBlank())
                .map(String::trim)
                .map(value -> value.toLowerCase(Locale.ROOT))
                .distinct()
                .sorted()
                .collect(java.util.stream.Collectors.toCollection(ArrayList::new));
        }
    }
}
