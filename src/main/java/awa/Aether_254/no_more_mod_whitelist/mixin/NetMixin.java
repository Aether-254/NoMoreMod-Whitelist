package awa.Aether_254.no_more_mod_whitelist.mixin;

import awa.Aether_254.no_more_mod_whitelist.ReportedModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.List;

@Mixin(targets = "com.hardrock.modwhitelist.network.Net", remap = false)
abstract class NetMixin {
    @ModifyArg(
        method = "lambda$register$2",
        at = @At(
            value = "INVOKE",
            target = "Lcom/hardrock/modwhitelist/network/Net;sendChunkedResponse(JLjava/util/List;Ljava/util/List;)V"
        ),
        index = 1,
        remap = false
    )
    private static List<String> noMoreModWhitelist$modifyReportedModIds(List<String> original) {
        return ReportedModList.modifyModIds(original);
    }

    @ModifyArg(
        method = "lambda$register$2",
        at = @At(
            value = "INVOKE",
            target = "Lcom/hardrock/modwhitelist/network/Net;sendChunkedResponse(JLjava/util/List;Ljava/util/List;)V"
        ),
        index = 2,
        remap = false
    )
    private static List<?> noMoreModWhitelist$modifyReportedFiles(List<?> original) {
        return ReportedModList.modifyFiles(original);
    }
}
