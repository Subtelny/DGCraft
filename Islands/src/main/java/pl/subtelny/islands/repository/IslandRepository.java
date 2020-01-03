package pl.subtelny.islands.repository;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jooq.Configuration;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.database.DatabaseConfiguration;
import pl.subtelny.islands.model.Island;
import pl.subtelny.islands.model.IslandMember;
import pl.subtelny.islands.model.IslandMemberType;
import pl.subtelny.islands.model.IslandType;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.model.guild.Guild;
import pl.subtelny.islands.model.guild.GuildId;
import pl.subtelny.islands.model.guild.GuildIsland;
import pl.subtelny.islands.model.island.IslandId;
import pl.subtelny.islands.model.island.SkyblockIsland;
import pl.subtelny.islands.repository.loader.island.GuildIslandData;
import pl.subtelny.islands.repository.loader.island.IslandData;
import pl.subtelny.islands.repository.loader.island.IslandDataLoader;
import pl.subtelny.islands.repository.loader.island.IslandDataLoaderRequest;
import pl.subtelny.islands.repository.loader.island.SkyblockIslandData;
import pl.subtelny.islands.repository.loader.island.member.IslandMemberData;
import pl.subtelny.islands.repository.loader.island.member.IslandMemberLoader;
import pl.subtelny.islands.repository.loader.island.member.IslandMemberLoaderRequest;
import pl.subtelny.islands.repository.loader.island.member.IslandMemberLoaderResult;
import pl.subtelny.islands.utils.SkyblockIslandUtil;
import pl.subtelny.repository.Storage;
import pl.subtelny.utils.cuboid.Cuboid;

@Component
public class IslandRepository extends Storage<IslandId, Optional<Island>> {

	private final Configuration configuration;

	private final IslanderRepository islanderRepository;

	private final GuildRepository guildRepository;

	public IslandRepository(DatabaseConfiguration databaseConfiguration,
			IslanderRepository islanderRepository,
			GuildRepository guildRepository) {
		super(Caffeine.newBuilder().build());
		this.configuration = databaseConfiguration.getConfiguration();
		this.islanderRepository = islanderRepository;
		this.guildRepository = guildRepository;
	}

	@Override
	public Function<? super IslandId, ? extends Optional<Island>> computeData() {
		return this::findIsland;
	}

	public Optional<Island> findIsland(IslandId islandId) {
		IslandDataLoaderRequest request = IslandDataLoaderRequest.newBuilder()
				.where(islandId)
				.build();

		Optional<Island> islandOpt = loadIsland(request);
		if (islandOpt.isPresent()) {
			return Optional.empty();
		}
		Island island = islandOpt.get();
		IslandMemberLoaderRequest islandMembersRequest = IslandMemberLoaderRequest.newBuilder()
				.where(islandId)
				.build();

		List<IslandMember> islandMembers = loadIslandMembers(islandMembersRequest);
		islandMembers.forEach(island::addMember);
		return Optional.of(island);
	}

	private Optional<Island> loadIsland(IslandDataLoaderRequest request) {
		IslandDataLoader loader = new IslandDataLoader(configuration, request);
		List<IslandData> loadedData = loader.perform().getLoadedData();
		if (loadedData.size() != 1) {
			return Optional.empty();
		}
		IslandData islandData = loadedData.get(0);
		Island island = new IslandMapper().map(islandData);
		return Optional.of(island);
	}

	private List<IslandMember> loadIslandMembers(IslandMemberLoaderRequest islandMemberLoaderRequest) {
		IslandMemberLoader islandMemberLoader = new IslandMemberLoader(configuration, islandMemberLoaderRequest);
		IslandMemberLoaderResult result = islandMemberLoader.perform();
		return result.getIslandMembersData().stream()
				.map(i -> new IslandMemberMapper().map(i))
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toList());
	}

	private class IslandMemberMapper {

		public Optional<IslandMember> map(IslandMemberData data) {
			IslandMemberType type = data.getIslandMemberType();
			if (type == IslandMemberType.ISLANDER) {
				AccountId accountId = AccountId.of(UUID.fromString(data.getId()));
				Optional<Islander> islander = islanderRepository.getCache(accountId);
				if (islander.isPresent()) {
					return Optional.of(islander.get());
				}
			}
			if (type == IslandMemberType.GUILD) {
				GuildId guildId = GuildId.of(Integer.parseInt(data.getId()));
				Optional<Guild> cache = guildRepository.getCache(guildId);
				if (cache.isPresent()) {
					return Optional.of(cache.get());
				}
			}
			return Optional.empty();
		}

	}

	private class IslandMapper {

		public Island map(IslandData islandData) {
			IslandType type = islandData.getIslandType();
			Island island;
			if (type == IslandType.SKYBLOCK) {
				island = mapAsSkyblock((SkyblockIslandData) islandData);
			} else {
				island = mapAsGuild((GuildIslandData) islandData);
			}
			return island;
		}

		private GuildIsland mapAsGuild(GuildIslandData data) {
			GuildIsland guildIsland = new GuildIsland(data.getIslandId(), data.getCuboid(), data.getCreatedDate());

			GuildId owner = GuildId.of(data.getOwner());
			Optional<Guild> guildOpt = guildRepository.getCache(owner);
			if (guildOpt.isPresent()) {
				guildIsland.changeOwner(guildOpt.get());
			}
			return guildIsland;
		}

		private SkyblockIsland mapAsSkyblock(SkyblockIslandData data) {
			AccountId owner = AccountId.of(data.getOwner());
			Optional<Islander> ownerOpt = islanderRepository.getCache(owner);
			Cuboid cuboid = SkyblockIslandUtil.defaultCuboid(data.getIslandCoordinates());
			SkyblockIsland skyblockIsland = new SkyblockIsland(data.getIslandId(), data.getIslandCoordinates(), cuboid, data.getCreatedDate());
			if (ownerOpt.isPresent()) {
				skyblockIsland.changeOwner(ownerOpt.get());
			}
			return skyblockIsland;
		}

	}
}
