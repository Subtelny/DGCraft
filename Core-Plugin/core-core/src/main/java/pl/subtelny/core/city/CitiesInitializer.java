package pl.subtelny.core.city;

import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.components.core.api.DependencyActivator;
import pl.subtelny.core.Core;
import pl.subtelny.core.city.repository.FileCityRepository;

@Component
public class CitiesInitializer implements DependencyActivator {

    private final FileCityRepository fileCityRepository;

    @Autowired
    public CitiesInitializer(FileCityRepository fileCityRepository) {
        this.fileCityRepository = fileCityRepository;
    }

    @Override
    public void activate() {
        try {
            fileCityRepository.initializeFile(Core.plugin);
            fileCityRepository.loadCitiesFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
