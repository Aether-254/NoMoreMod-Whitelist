package awa.Aether_254.no_more_mod_whitelist;

import net.neoforged.fml.ModList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public final class ReportedModList {
    private ReportedModList() {
    }

    public static List<String> modifyModIds(List<String> original) {
        Set<String> reported = new HashSet<>(original);
        ClientConfig.Data config = ClientConfig.get();
        reported.addAll(config.addedMods);
        reported.removeAll(config.removedMods);
        reported.remove(NoMoreModWhitelist.MOD_ID);

        List<String> result = new ArrayList<>(reported);
        result.sort(String::compareTo);
        NoMoreModWhitelist.LOGGER.info("Reported ModWhiteList mod IDs: {}", result);
        return result;
    }

    public static List<?> modifyFiles(List<?> original) {
        Set<String> hiddenFiles = hiddenFileNames();
        List<Object> result = new ArrayList<>();
        for (Object fileHash : original) {
            try {
                String name = (String) fileHash.getClass().getMethod("name").invoke(fileHash);
                if (!hiddenFiles.contains(name.toLowerCase(Locale.ROOT))) {
                    result.add(fileHash);
                }
            } catch (ReflectiveOperationException exception) {
                throw new IllegalStateException("Unable to read ModWhiteList FileHash.name", exception);
            }
        }
        NoMoreModWhitelist.LOGGER.info("Suppressed ModWhiteList files: {}", hiddenFiles);
        return result;
    }

    private static Set<String> hiddenFileNames() {
        Set<String> hidden = new HashSet<>();
        Set<String> hiddenModIds = new HashSet<>(ClientConfig.get().removedMods);
        hiddenModIds.add(NoMoreModWhitelist.MOD_ID);
        for (String modId : hiddenModIds) {
            var modFile = ModList.get().getModFileById(modId);
            if (modFile != null) {
                String name = modFile.getFile().getFilePath().getFileName().toString();
                hidden.add(name.toLowerCase(Locale.ROOT));
            }
        }
        return hidden;
    }
}
