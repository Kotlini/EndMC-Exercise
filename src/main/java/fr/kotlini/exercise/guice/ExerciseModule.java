package fr.kotlini.exercise.guice;

import com.google.inject.AbstractModule;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;

@RequiredArgsConstructor
public class ExerciseModule extends AbstractModule
{
    private final JavaPlugin plugin;

    @Override
    @SneakyThrows
    protected void configure() {
        super.bind(JavaPlugin.class).toInstance(plugin);
    }
}
