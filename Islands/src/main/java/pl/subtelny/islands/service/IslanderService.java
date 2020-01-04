package pl.subtelny.islands.service;

import java.util.Optional;
import org.bukkit.entity.Player;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.api.AccountService;
import pl.subtelny.core.api.Accounts;
import pl.subtelny.core.api.CoreAPI;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.repository.IslanderRepository;
import pl.subtelny.jobs.JobsProvider;

@Component
public class IslanderService {

	private final IslanderRepository islanderRepository;

	private final AccountService accountService;

	@Autowired
	public IslanderService(IslanderRepository islanderRepository) {
		this.islanderRepository = islanderRepository;
		this.accountService = CoreAPI.getAccountService();
	}

	public Islander getIslander(Player player) {
		Optional<Islander> cache = islanderRepository.getCache(AccountId.of(player.getUniqueId()));
		return cache.orElseGet(() -> createNewIslander(player));
	}

	private Islander createNewIslander(Player player) {
		Account account = accountService.getAccount(player);
		return new Islander(account);
	}

	public void saveIslander(Islander islander) {
		JobsProvider.async(() -> islanderRepository.saveIslander(islander));
	}

}
