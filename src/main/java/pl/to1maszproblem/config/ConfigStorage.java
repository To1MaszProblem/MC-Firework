package pl.to1maszproblem.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ConfigStorage {
    private static File cfgFile;
    private static final String fileName = "config.yml";
    private static FileConfiguration cfg;
    public static File getCfg() { return cfgFile; }

    public static FileConfiguration getFile() {
        return cfg;
    }

    //$ = -
    // _ = . (nowy wiersz)

    public static String USAGE$MESSAGE;
    public static String PERMISSION_PERMISSION;
    public static String PERMISSION_MESSAGE;
    public static String FIREWORK_NAME;
    public static boolean FIREWORK_GLOW;
    public static List<String> FIREWORK_LORE;

    public static void createDefaultFiles(Plugin plugin) {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists() && !dataFolder.mkdir()) {
            throw new RuntimeException("Wystąpił błąd podczas tworzenia folderu pluginu!");
        } else {
            cfgFile = new File(dataFolder, fileName);
            if (!cfgFile.exists()) {
                try {
                    Files.copy(plugin.getClass().getClassLoader().getResourceAsStream(fileName), Paths.get(cfgFile.toURI()), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException var3) {
                    throw new RuntimeException("Wystąpił błąd podczas tworzenia configu!", var3);
                }
            }

            cfg = YamlConfiguration.loadConfiguration(cfgFile);
        }
    }

    public static void load() {
        try {
            Field[] fields = ConfigStorage.class.getFields();
            for (Field field : fields) {
                if (isConfigField(field)) {
                    String path = getPath(field);
                    if (cfg.isSet(path)) {
                        field.set(null, cfg.get(path));
                    }
                }
            }

        } catch (Exception var5) {
            throw new RuntimeException("Wystąpił problem podczas ładowania configu!", var5);
        }
    }

    public static void save() {
        try {
            Field[] fields = ConfigStorage.class.getFields();
            for (Field field : fields) {
                if (isConfigField(field)) {
                    String path = getPath(field);
                    cfg.set(path, field.get(null));
                }
            }

            cfg.save(cfgFile);
        } catch (Exception var5) {
            throw new RuntimeException("Wystąpił problem podczas zapisywania configu!", var5);
        }
    }

    public static void reload() {
        cfg = YamlConfiguration.loadConfiguration(cfgFile);
        load();
        save();
    }

    private static String getPath(Field field) {
        return field.getName().toLowerCase().replace("$", "-").replace("_", ".");
    }

    private static boolean isConfigField(Field field) {
        return Modifier.isPublic(field.getModifiers())
                && Modifier.isStatic(field.getModifiers())
                && !Modifier.isFinal(field.getModifiers())
                && !Modifier.isTransient(field.getModifiers());
    }
}

