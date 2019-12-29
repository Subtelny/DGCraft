package pl.subtelny.islands.service;

import org.bukkit.entity.Player;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.islands.model.Islander;
import pl.subtelny.islands.repository.IslanderRepository;

@Component
public class IslanderService {

	private final IslanderRepository islanderRepository;

	@Autowired
	public IslanderService(IslanderRepository islanderRepository) {
		this.islanderRepository = islanderRepository;
	}

	public Islander getIslander(Player player) {
		return islanderRepository.getCache(AccountId.of(player.getUniqueId()));
	}

}
