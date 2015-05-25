package org.quartzpowered.common.util;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
public class UuidUtil {
    public UUID fromFlatString(String str) {
        // xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
        return UUID.fromString(str.substring(0, 8) + "-" + str.substring(8, 12) + "-" + str.substring(12, 16) +
                "-" + str.substring(16, 20) + "-" + str.substring(20, 32));
    }

    public String toFlatString(UUID uuid) {
        return uuid.toString().replace("-", "");
    }
}
