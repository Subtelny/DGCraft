package pl.subtelny.islands;

import pl.subtelny.components.core.api.BeanContext;
import pl.subtelny.components.core.api.BeanContextId;
import pl.subtelny.plugin.DGPlugin;

import java.util.Map;

public class Islands extends DGPlugin {

    @Override
    public void onLoaded() {
        System.out.println("ON LOADED DG-ISLAND");
    }

    @Override
    public void onEnabled() {
        System.out.println("ON ENABLED DG-ISLAND");


        Map<String, Object> beans = BeanContext.getBeans(BeanContextId.of("DG-Core"));
        beans.forEach((s, o) -> {
            System.out.println(String.format("%s - %s", s.toString(), o.toString()));
        });
    }
}
