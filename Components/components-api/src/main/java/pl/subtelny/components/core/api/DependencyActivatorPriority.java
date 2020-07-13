package pl.subtelny.components.core.api;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DependencyActivatorPriority {

    Priority priority();

    enum Priority {

        HIGH(4),
        MEDIUM(3),
        LOW(2),
        LOWEST(1),
        SYSTEM(0);

        private int priority;

        Priority(int priority) {
            this.priority = priority;
        }

        public int getPriority() {
            return priority;
        }
    }

}
