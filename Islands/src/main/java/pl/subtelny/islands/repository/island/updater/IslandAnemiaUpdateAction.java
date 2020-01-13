package pl.subtelny.islands.repository.island.updater;

import java.sql.Timestamp;
import org.jooq.Configuration;
import org.jooq.impl.DSL;
import pl.subtelny.core.generated.enums.Islandtype;
import pl.subtelny.core.generated.tables.Islands;
import pl.subtelny.core.generated.tables.records.IslandsRecord;
import pl.subtelny.islands.repository.island.IslandAnemia;
import pl.subtelny.islands.utils.LocationSerializer;
import pl.subtelny.repository.UpdateAction;

public class IslandAnemiaUpdateAction implements UpdateAction<IslandAnemia> {

	private final Configuration configuration;

	public IslandAnemiaUpdateAction(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public void perform(IslandAnemia islandAnemia) {
		saveIslandAnemia(islandAnemia);
	}

	private void saveIslandAnemia(IslandAnemia islandAnemia) {
		IslandsRecord islandsRecord = DSL.using(configuration).newRecord(Islands.ISLANDS);
		Timestamp createdDate = Timestamp.valueOf(islandAnemia.getCreatedDate());
		islandsRecord.setCreatedDate(createdDate);

		String serializedSpawn = LocationSerializer.serializeMinimalistic(islandAnemia.getSpawn());
		islandsRecord.setSpawn(serializedSpawn);

		Islandtype islandType = Islandtype.valueOf(islandAnemia.getIslandType().name());
		islandsRecord.setType(islandType);

		if (islandAnemia.getIslandId().getId() != null) {
			islandsRecord.setId(Math.toIntExact(islandAnemia.getIslandId().getId()));
			islandsRecord.update();
		} else {
			islandsRecord.store();
		}
	}
}
