package pl.subtelny.core.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import org.bukkit.entity.Player;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.api.AccountService;
import pl.subtelny.core.model.Account;
import pl.subtelny.core.model.AccountId;
import pl.subtelny.core.repository.AccountRepository;
import pl.subtelny.jobs.JobsProvider;

@Component
public class AccountServiceImpl implements AccountService {

	private static final long MIN_MINUTES_BETWEEN_DATES_TO_SAVE = 5;

	private final AccountRepository accountRepository;

	@Autowired
	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	public void syncAccountWithPlayer(Player player) {
		AccountId accountId = AccountId.of(player.getUniqueId());
		Optional<Account> accountOpt = accountRepository.findAccount(accountId);

		Account account;
		if (accountOpt.isPresent()) {
			account = accountOpt.get();
			if (!anyChanges(player, account)) {
				return;
			}
		} else {
			account = new Account(accountId);
		}
		updateAccount(player, account);
	}

	private void updateAccount(Player player, Account account) {
		account.setLastOnline(LocalDate.now());
		account.setName(player.getName());
		account.setDisplayName(player.getDisplayName());
		JobsProvider.async(() -> accountRepository.saveAccount(account));
	}

	private boolean anyChanges(Player player, Account account) {
		if (!player.getName().equals(account.getName())) {
			return true;
		}
		if (!player.getDisplayName().equals(account.getDisplayName())) {
			return true;
		}
		LocalDate lastOnline = account.getLastOnline();
		LocalDate now = LocalDate.now();

		long minutesBetween = ChronoUnit.MINUTES.between(lastOnline, now);
		return minutesBetween > MIN_MINUTES_BETWEEN_DATES_TO_SAVE;
	}

	@Override
	public Account getAccount(Player player) {
		AccountId accountId = AccountId.of(player.getUniqueId());
		Optional<Account> cache = accountRepository.findAccount(accountId);
		return cache.orElseGet(() -> createNewAccount(player));
	}

	private Account createNewAccount(Player player) {
		AccountId accountId = AccountId.of(player.getUniqueId());
		Account account = new Account(accountId);
		account.setDisplayName(player.getDisplayName());
		account.setLastOnline(LocalDate.now());
		account.setName(player.getName());
		saveAccount(account);
		return account;
	}

	@Override
	public void saveAccount(Account account) {
		JobsProvider.async(() -> accountRepository.saveAccount(account));
	}
}
