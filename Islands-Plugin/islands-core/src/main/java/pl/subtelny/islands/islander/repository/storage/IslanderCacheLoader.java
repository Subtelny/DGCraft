package pl.subtelny.islands.islander.repository.storage;

import com.github.benmanes.caffeine.cache.CacheLoader;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jooq.DSLContext;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.account.Accounts;
import pl.subtelny.core.api.database.ConnectionProvider;
import pl.subtelny.islands.island.IslandId;
import pl.subtelny.islands.island.IslandMemberId;
import pl.subtelny.islands.island.IslanderId;
import pl.subtelny.islands.island.membership.IslandMembershipQueryService;
import pl.subtelny.islands.island.membership.model.IslandMembership;
import pl.subtelny.islands.islander.model.Islander;
import pl.subtelny.islands.islander.repository.anemia.IslanderAnemia;
import pl.subtelny.islands.islander.repository.loader.IslanderAnemiaLoadAction;
import pl.subtelny.islands.islander.repository.loader.IslanderLoadRequest;
import pl.subtelny.utilities.NullObject;
import pl.subtelny.utilities.exception.ValidationException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class IslanderCacheLoader implements CacheLoader<IslanderId, NullObject<Islander>> {

    private final ConnectionProvider connectionProvider;

    private final IslandMembershipQueryService islandMembershipQueryService;

    private final Accounts accounts;

    public IslanderCacheLoader(ConnectionProvider connectionProvider,
                               IslandMembershipQueryService islandMembershipQueryService,
                               Accounts accounts) {
        this.connectionProvider = connectionProvider;
        this.islandMembershipQueryService = islandMembershipQueryService;
        this.accounts = accounts;
    }

    @Override
    public @Nullable NullObject<Islander> load(@NonNull IslanderId islanderId) {
        IslanderLoadRequest request = IslanderLoadRequest.newBuilder()
                .where(islanderId)
                .build();
        Optional<IslanderAnemia> anemiaOpt = performAction(request);
        return anemiaOpt.map(this::mapAnemiaIntoDomain)
                .map(NullObject::of)
                .orElse(NullObject.empty());
    }

    private Optional<IslanderAnemia> performAction(IslanderLoadRequest request) {
        IslanderAnemiaLoadAction action = getIslanderAnemiaLoadAction(request);
        return Optional.ofNullable(action.perform());
    }

    private IslanderAnemiaLoadAction getIslanderAnemiaLoadAction(IslanderLoadRequest request) {
        DSLContext connection = connectionProvider.getCurrentConnection();
        return new IslanderAnemiaLoadAction(connection, request);
    }

    private Islander mapAnemiaIntoDomain(IslanderAnemia anemia) {
        IslanderId islanderId = anemia.getIslanderId();
        List<IslandId> islandIds = getIslandIds(islanderId);
        Account account = accounts.findAccount(AccountId.of(islanderId.getInternal()))
                .orElseThrow(() -> ValidationException.of("islander-cache-loader.account_not_found", islanderId));
        return new Islander(islanderId, islandIds, account);
    }

    private List<IslandId> getIslandIds(IslanderId islanderId) {
        IslandMemberId islandMemberId = IslandMemberId.of(Islander.ISLAND_MEMBER_TYPE.getInternal(), islanderId.getInternal().toString());
        return islandMembershipQueryService.loadIslandMemberships(islandMemberId)
                .stream()
                .map(IslandMembership::getIslandId)
                .collect(Collectors.toList());
    }

}
