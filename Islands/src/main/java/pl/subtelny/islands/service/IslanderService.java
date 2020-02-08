package pl.subtelny.islands.service;

import org.bukkit.entity.Player;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.islander.Islander;
import pl.subtelny.islands.repository.islander.IslanderRepository;
import pl.subtelny.jobs.JobsProvider;

import java.util.Optional;

@Component
public class IslanderService {

	private final IslanderRepository islanderRepository;

	@Autowired
	public IslanderService(IslanderRepository islanderRepository) {
		this.islanderRepository = islanderRepository;
	}

	public void loadIslander(Player player) {
		JobsProvider.supplyAsync(() -> createPlayerIfAbsent(player));
	}

	private Islander createPlayerIfAbsent(Player player) {
		Optional<Islander> islanderOpt = isIslanderExist(player);
		if (islanderOpt.isEmpty()) {
			return createIslander(player);
		}
	}

	private Optional<Islander> isIslanderExist(Player player) {
		AccountId accountId = getAccountIdFromPlayer(player);
		return islanderRepository.findIslander(accountId);
	}

	private Islander createIslander(Player player) {
		Islander islander = new Islander(getAccountIdFromPlayer(player));
		JobsProvider.async(() -> islanderRepository.updateIslander(islander));
		return islander;
	}

	public Islander getIslander(Player player) {
		AccountId accountId = getAccountIdFromPlayer(player);
		return islanderRepository.getIslanderIfPresent(accountId).orElse(createIslander(player));
	}

	private AccountId getAccountIdFromPlayer(Player player) {
		return AccountId.of(player.getUniqueId());
	}
}
