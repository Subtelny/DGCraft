package pl.subtelny.beans;

import java.lang.annotation.*;

@Target({ElementType.CONSTRUCTOR, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

}
