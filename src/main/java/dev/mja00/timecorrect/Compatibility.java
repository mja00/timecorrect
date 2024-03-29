package dev.mja00.timecorrect;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Compatibility {

    public static Boolean IS_FOLIA = null;

    private static boolean methodExists(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            clazz.getDeclaredMethod(methodName, parameterTypes);
            return true;
        } catch (Throwable dontcare) {
            return false;
        }
    }

    public static Boolean isFolia() {
        if (IS_FOLIA == null) {
            IS_FOLIA = methodExists(Bukkit.class, "getGlobalRegionScheduler");
        }
        return IS_FOLIA;
    }

    public static void runTaskAsync(Plugin plugin, Runnable runnable) {
        if (isFolia()) {
            Bukkit.getAsyncScheduler().runNow(plugin, (task) -> runnable.run());
            return;
        }
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }
}
