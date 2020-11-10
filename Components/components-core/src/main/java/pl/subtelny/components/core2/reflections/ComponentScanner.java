package pl.subtelny.components.core2.reflections;

import org.reflections.scanners.TypeAnnotationsScanner;
import pl.subtelny.components.core.api.Component;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ComponentScanner extends TypeAnnotationsScanner {

    @Override
    public void scan(Object cls) {
        String className = getMetadataAdapter().getClassName(cls);
        List<String> classAnnotationNames = getMetadataAdapter().getClassAnnotationNames(cls);
        classAnnotationNames.stream()
                .filter(this::acceptResult)
                .forEach(annotationName -> computeAnnotation(className, annotationName));
    }

    private void computeAnnotation(String className, String annotationName) {
        Optional<Class<?>> annotationClassOpt = findClassByName(annotationName);
        if (annotationClassOpt.isPresent()) {
            if (isComponent(annotationClassOpt.get())) {
                getStore().put(annotationName, className);
            }
        }
    }

    private Optional<Class<?>> findClassByName(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            return Optional.of(clazz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private boolean isComponent(Class<?> clazz) {
        if (isComponentAnnotation(clazz)) {
            return true;
        }
        Annotation[] annotations = clazz.getAnnotations();
        return Arrays.stream(annotations)
                .anyMatch(annotation -> isComponentAnnotation(annotation.annotationType()));
    }

    private boolean isComponentAnnotation(Class<?> clazz) {
        return clazz.getName().equals(Component.class.getName());
    }

}
