package pl.subtelny.core.api.account;

import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface Accounts {

    default CompletableFuture<Optional<Account>> findAccountAsync(Player player) {
        return findAccountAsync(AccountId.of(player.getUniqueId()));
    }

    default Optional<Account> findAccount(Player player) {
        return findAccount(AccountId.of(player.getUniqueId()));
    }

    default boolean isAccountLoaded(Player player) {
        return isAccountLoaded(AccountId.of(player.getUniqueId()));
    }

    Optional<Account> findAccount(AccountId accountId);

    CompletableFuture<Optional<Account>> findAccountAsync(AccountId accountId);

    boolean isAccountLoaded(AccountId accountId);

    void createAccount(Player player, CityType cityType);

    void saveAccount(Account account);

}
