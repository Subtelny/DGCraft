package pl.subtelny.core.city.create;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.subtelny.components.core.api.Autowired;
import pl.subtelny.components.core.api.Component;
import pl.subtelny.core.api.account.CityType;
import pl.subtelny.core.city.City;
import pl.subtelny.core.city.CityPortal;
import pl.subtelny.core.city.repository.CityRepository;
import pl.subtelny.utilities.cuboid.Cuboid;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.HashMap;
import java.util.Map;

@Component
public class CityCreateService {

    private final CityRepository cityRepository;

    private Map<Player, CityCreateSession> sessions = new HashMap<>();

    @Autowired
    public CityCreateService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public CityCreateSession createSession(Player player, CityType cityType) {
        if (isAlreadyExistSessionForCity(cityType)) {
            throw ValidationException.of("city_create_session_already_exist_city");
        }
        City city = cityRepository.get(cityType);
        if (city != null) {
            return sessions.put(player, createSessionBasedOnCity(city));
        }
        return sessions.put(player, new CityCreateSession(cityType));
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
        if (!session.isReadyToCreateCity()) {
            throw ValidationException.of("city_create_session_not_ready_to_complete");
        }
        City city = mapSessionIntoCity(session);
        cityRepository.save(city);
        cancelSession(player);
    }

    private CityCreateSession createSessionBasedOnCity(City city) {
        CityPortal cityPortal = city.getCityPortal();
        return new CityCreateSession(
                city.getCityType(),
                city.getSpawn(),
                city.getCuboid(),
                cityPortal.getTeleportTarget(),
                cityPortal.getCuboid()
        );
    }

    private City mapSessionIntoCity(CityCreateSession session) {
        CityType cityType = session.getCityType();
        Cuboid cityCuboid = session.getCityCuboid();
        Location citySpawn = session.getCitySpawn();
        CityPortal cityPortal = new CityPortal(session.getTeleportCubiod(), session.getTeleportTarget());
        return new City(cityType, citySpawn, cityCuboid, cityPortal);
    }


    private boolean isAlreadyExistSessionForCity(CityType cityType) {
        return sessions.values().stream().anyMatch(cityCreateSession -> cityCreateSession.getCityType() == cityType);
    }

}
