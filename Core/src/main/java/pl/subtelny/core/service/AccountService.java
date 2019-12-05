package pl.subtelny.core.service;

import org.bukkit.entity.Player;
import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.core.model.AccountDTO;
import pl.subtelny.core.repository.AccountRepository;

@Component
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository userRepository) {
        this.accountRepository = userRepository;
    }

    public AccountDTO getAccountByPlayer(Player player) {
        return accountRepository.getAccountDTO(player.getUniqueId());
    }

    public void createUserIfNotExists(Player player) {
        AccountDTO account = getAccountByPlayer(player);
        if(account == null) {
            account = createUserForPlayer(player);
            accountRepository.saveUser(account);
        }
    }

    private AccountDTO createUserForPlayer(Player player) {
        //Account account = new Account();
        //account.setUuid(player.getUniqueId());
        //account.setNickname(player.getName());
        return null;
    }

}
