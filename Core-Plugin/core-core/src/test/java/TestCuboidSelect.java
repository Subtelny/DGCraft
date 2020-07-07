import org.bukkit.Location;
import org.junit.Test;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.components.core.api.DependencyActivatorPriority;
import pl.subtelny.core.configuration.ConfigurationInitializer;
import pl.subtelny.core.cuboidselector.CuboidSelectSession;
import pl.subtelny.core.database.DatabaseInitializer;

import java.util.Arrays;

public class TestCuboidSelect {

    @Test
    public void test() {
        ConfigurationInitializer initializer = new ConfigurationInitializer(null,null,null);
        DependencyActivator activator = initializer;

        System.out.println(activator.getClass().getName());
        System.out.println(Arrays.toString(activator.getClass().getAnnotations()));
        System.out.println(Arrays.toString(activator.getClass().getAnnotatedInterfaces()));
        System.out.println(Arrays.toString(activator.getClass().getDeclaredAnnotations()));
        Class<?>[] interfaces = activator.getClass().getInterfaces();
        System.out.println(Arrays.toString(interfaces));
        for (Class<?> anInterface : interfaces) {
            System.out.println("#1");
            System.out.println(Arrays.toString(anInterface.getAnnotations()));
            System.out.println(Arrays.toString(anInterface.getAnnotatedInterfaces()));
            System.out.println(Arrays.toString(anInterface.getDeclaredAnnotations()));
        }
    }

}
