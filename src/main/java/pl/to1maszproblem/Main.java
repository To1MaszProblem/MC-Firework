package pl.to1maszproblem;

import com.google.common.base.Stopwatch;
import org.bukkit.plugin.java.JavaPlugin;
import pl.to1maszproblem.commands.FireworkCommand;
import pl.to1maszproblem.config.ConfigStorage;
import pl.to1maszproblem.listeners.onFireworkExplodeListener;
import pl.to1maszproblem.utils.TextUtil;
import java.util.concurrent.TimeUnit;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        Stopwatch started = Stopwatch.createStarted();
        TextUtil.sendLogger("&6Loading plugin...", this);
        ConfigStorage.createDefaultFiles(this);
        ConfigStorage.load();
        TextUtil.sendLogger("&6Loading configuration file(s)...", this);
        TextUtil.sendLogger("&6Loading  command(s)...", this);
        getCommand("firework").setExecutor(new FireworkCommand());
        TextUtil.sendLogger("&6Loading  listener(s)...", this);
        getServer().getPluginManager().registerEvents(new onFireworkExplodeListener(), this);
        long millis = started.elapsed(TimeUnit.MILLISECONDS);
        TextUtil.sendLogger("&eStarted plugin in &6" + millis + "&ems!", this);
    }
}
