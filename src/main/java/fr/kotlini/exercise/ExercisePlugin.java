package fr.kotlini.exercise;


import com.google.inject.Guice;
import com.google.inject.Injector;
import fr.kotlini.exercise.guice.CommandInitializer;
import fr.kotlini.exercise.guice.ExerciseModule;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;


@Getter
public class ExercisePlugin extends JavaPlugin
{
    private Injector injector;

    @Override
    public void onEnable() {
        injector = Guice.createInjector(new ExerciseModule(this));

        final CommandInitializer commandInitializer = injector.getInstance(CommandInitializer.class);
        commandInitializer.init();
    }
}