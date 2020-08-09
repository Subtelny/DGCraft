package pl.subtelny.core.city.create;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.city.CityId;
import pl.subtelny.core.city.City;
import pl.subtelny.core.city.CityPortal;
import pl.subtelny.core.city.repository.CityRepository;
import pl.subtelny.utilities.Validation;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class CityCreateService {

    private final CityRepository cityRepository;

    private Map<Player, CityCreateSession> sessions = new HashMap<>();

    @Autowired
    public CityCreateService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public void createSession(Player player, CityId cityId) {
        Validation.isTrue(!isAlreadyExistSessionForCity(cityId), "city.create.city_session_already_exist");
        CityCreateSession session = cityRepository.find(cityId)
                .map(this::createSessionBasedOnCity)
                .orElse(new CityCreateSession(cityId));
        sessions.put(player, session);
    }

    public CityCreateSession getSession(Player player) {
        return sessions.get(player);
    }

    public boolean hasSession(Player player) {
        return sessions.containsKey(player);
    }

    public void cancelSession(Player player) {
        sessions.remove(player);
    }

    public void completeSession(Player player) {
        CityCreateSession session = getSession(player);
        Validation.isTrue(session.isReadyToCreateCity(), "city.create.not_ready_to_complete");
        City city = mapSessionIntoCity(session);
        cityRepository.save(city);
        cancelSession(player);
    }

    private CityCreateSession createSessionBasedOnCity(City city) {
        CityPortal cityPortal = city.getCityPortal();
        return new CityCreateSession(
                city.getCityId(),
                city.getSpawn(),
                city.getCuboid(),
                cityPortal.getTeleportTarget(),
                cityPortal.getCuboid()
        );
    }

    private City mapSessionIntoCity(CityCreateSession session) {
        CityId cityId = session.getCityId();
        Cuboid cityCuboid = session.getCityCuboid();
        Location citySpawn = session.getCitySpawn();
        CityPortal cityPortal = new CityPortal(session.getTeleportCubiod(), session.getTeleportTarget());
        return new City(cityId, citySpawn, cityCuboid, cityPortal);
    }


    private boolean isAlreadyExistSessionForCity(CityId cityId) {
        return sessions.values().stream()
                .anyMatch(cityCreateSession -> cityCreateSession.getCityId().equals(cityId));
    }

}
