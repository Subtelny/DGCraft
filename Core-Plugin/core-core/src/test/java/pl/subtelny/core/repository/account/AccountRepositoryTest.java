package pl.subtelny.core.repository.account;

import org.junit.Assert;
import org.junit.Test;
import pl.subtelny.core.api.account.Account;
import pl.subtelny.core.api.account.AccountId;
import pl.subtelny.core.api.account.CreateAccountRequest;

import java.util.Optional;
import java.util.UUID;

public class AccountRepositoryTest {

    private final AccountRepository accountRepository;

    public AccountRepositoryTest() {
        this.accountRepository = new AccountRepositoryImplTest();
    }

    @Test
    public void createAccount() {
        //given
        AccountId accountId = AccountId.of(UUID.randomUUID());
        CreateAccountRequest request = request(accountId);

        //when
        Account account = createAccount(request);

        //then
        Assert.assertNotNull(account);
        Assert.assertEquals(account.getAccountId(), accountId);
    }

    @Test
    public void accountExistInStorage() {
        //given
        AccountId existAccountId = AccountId.of(UUID.randomUUID());
        AccountId notExistAccountId = AccountId.of(UUID.randomUUID());

        //when
        createAccount(existAccountId);

        //then
        Optional<Account> existAccountOpt = getAccountIfPresent(existAccountId);
        Optional<Account> notExistAccountOpt = getAccountIfPresent(notExistAccountId);

        Assert.assertTrue(notExistAccountOpt.isEmpty());
        Assert.assertTrue(existAccountOpt.isPresent());
        Assert.assertEquals(existAccountOpt.get().getAccountId(), existAccountId);
    }

    @Test
    public void findAccount() {
        //given
        AccountId notExistAccountId = AccountId.of(UUID.randomUUID());
        Account account = createAccount(AccountId.of(UUID.randomUUID()));

        //when
        Optional<Account> notExistAccount = findAccount(notExistAccountId);
        Optional<Account> existAccount = findAccount(account.getAccountId());

        Assert.assertTrue(notExistAccount.isEmpty());
        Assert.assertTrue(existAccount.isPresent());
        Assert.assertEquals(existAccount.get().getAccountId(), account.getAccountId());
    }

    @Test
    public void saveAccount() {
        //given
        String oldName = "first_name";
        String newName = "new_name";
        AccountId accountId = AccountId.of(UUID.randomUUID());
        Account account = createAccount(accountId, oldName);

        //when
        account.setName(newName);
        saveAccount(account);

        //then
        Account savedAccount = getAccountIfPresent(accountId).orElseThrow();

        Assert.assertEquals(savedAccount.getAccountId(), accountId);
        Assert.assertEquals(savedAccount.getName(), newName);
    }

    private Optional<Account> findAccount(AccountId notExistAccountId) {
        return accountRepository.findAccount(notExistAccountId);
    }

    private Optional<Account> getAccountIfPresent(AccountId existAccountId) {
        return accountRepository.getAccountIfPresent(existAccountId);
    }

    private Account createAccount(CreateAccountRequest request) {
        return accountRepository.createAccount(request);
    }

    private void saveAccount(Account account) {
        accountRepository.saveAccount(account);
    }

    private Account createAccount(AccountId accountId) {
        return createAccount(accountId, "Test");
    }

    private Account createAccount(AccountId accountId, String name) {
        CreateAccountRequest request = request(accountId, name);
        return createAccount(request);
    }

    private CreateAccountRequest request(AccountId accountId) {
        return request(accountId, "Test");
    }

    private CreateAccountRequest request(AccountId accountId, String name) {
        return CreateAccountRequest.of(accountId, name,"Test");
    }

}
