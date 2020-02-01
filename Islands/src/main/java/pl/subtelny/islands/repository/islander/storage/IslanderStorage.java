package pl.subtelny.islands.repository.islander.storage;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.bukkit.entity.Player;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.api.Accounts;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.repository.Storage;

public class IslanderStorage extends Storage<AccountId, Optional<Islander>> {

	public IslanderStorage() {
		super(Caffeine.newBuilder().build());
	}

}
