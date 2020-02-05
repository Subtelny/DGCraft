package pl.subtelny.islands.repository.islander;

import org.jooq.Configuration;
import pl.subtelny.islands.repository.islander.anemia.IslanderAnemia;
import pl.subtelny.repository.LoadAction;
import pl.subtelny.repository.LoaderResult;

import java.util.List;

public class IslanderAnemiaLoadAction implements LoadAction<IslanderAnemia> {

    private final Configuration configuration;

    private final IslanderLoadRequest request;

    public IslanderAnemiaLoadAction(Configuration configuration, IslanderLoadRequest request) {
        this.configuration = configuration;
        this.request = request;
    }

    @Override
    public LoaderResult<IslanderAnemia> perform() {
        return null;
    }

    @Override
    public LoaderResult<List<IslanderAnemia>> performList() {
        return null;
    }
}
