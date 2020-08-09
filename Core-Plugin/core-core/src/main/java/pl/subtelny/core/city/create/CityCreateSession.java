package pl.subtelny.core.city.create;

import org.bukkit.Location;
import pl.subtelny.core.api.account.CityType;
import pl.subtelny.core.api.city.CityId;
import pl.subtelny.utilities.cuboid.Cuboid;

import java.util.Objects;

public class CityCreateSession {

    private final CityId cityId;

    private Location citySpawn;

    private Cuboid cityCuboid;

    private Location teleportTarget;

    private Cuboid teleportCubiod;

    public CityCreateSession(CityId cityId,
                             Location citySpawn,
                             Cuboid cityCuboid,
                             Location teleportTarget,
                             Cuboid teleportCubiod) {
        this.cityId = cityId;
        this.citySpawn = citySpawn;
        this.cityCuboid = cityCuboid;
        this.teleportTarget = teleportTarget;
        this.teleportCubiod = teleportCubiod;
    }

    public CityCreateSession(CityId cityId) {
        this.cityId = cityId;
    }

    public CityId getCityId() {
        return cityId;
    }

    public Location getCitySpawn() {
        return citySpawn;
    }

    public void setCitySpawn(Location citySpawn) {
        this.citySpawn = citySpawn;
    }

    public Cuboid getCityCuboid() {
        return cityCuboid;
    }

    public void setCityCuboid(Cuboid cityCuboid) {
        this.cityCuboid = cityCuboid;
    }

    public Location getTeleportTarget() {
        return teleportTarget;
    }

    public void setTeleportTarget(Location teleportTarget) {
        this.teleportTarget = teleportTarget;
    }

    public Cuboid getTeleportCubiod() {
        return teleportCubiod;
    }

    public void setTeleportCubiod(Cuboid teleportCubiod) {
        this.teleportCubiod = teleportCubiod;
    }

    public boolean isReadyToCreateCity() {
        return citySpawn != null &&
                cityCuboid != null &&
                teleportCubiod != null &&
                teleportTarget != null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CityCreateSession that = (CityCreateSession) o;
        return Objects.equals(cityId, that.cityId) &&
                Objects.equals(citySpawn, that.citySpawn) &&
                Objects.equals(cityCuboid, that.cityCuboid) &&
                Objects.equals(teleportTarget, that.teleportTarget) &&
                Objects.equals(teleportCubiod, that.teleportCubiod);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityId, citySpawn, cityCuboid, teleportTarget, teleportCubiod);
    }
}
