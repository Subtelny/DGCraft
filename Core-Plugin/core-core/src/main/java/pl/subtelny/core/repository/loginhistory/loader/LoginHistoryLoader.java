package pl.subtelny.core.repository.loginhistory.loader;

import pl.subtelny.core.api.database.DatabaseConnection;
import pl.subtelny.core.api.loginhistory.LoginHistory;
import pl.subtelny.core.repository.loginhistory.LoginHistoryAnemia;
import pl.subtelny.core.repository.loginhistory.entity.LoginHistoryEntity;
import pl.subtelny.utilities.Period;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LoginHistoryLoader {

    private final DatabaseConnection databaseConnection;

    public LoginHistoryLoader(DatabaseConnection databaseConnection) {
        this.databaseConnection = databaseConnection;
    }

    public Optional<LoginHistory> loadLoginHistory(LoginHistoryLoadRequest request) {
        Optional<LoginHistoryAnemia> anemiaOpt = load(request);
        return anemiaOpt.map(this::mapIntoDomain);
    }

    public List<LoginHistory> loadLoginHistories(LoginHistoryLoadRequest request) {
        List<LoginHistoryAnemia> loginHistoryAnemias = loadAll(request);
        return loginHistoryAnemias.stream()
                .map(this::mapIntoDomain)
                .collect(Collectors.toList());
    }

    private Optional<LoginHistoryAnemia> load(LoginHistoryLoadRequest request) {
        LoginHistoryAnemiaLoadAction action = new LoginHistoryAnemiaLoadAction(databaseConnection.getConfiguration(), request);
        return Optional.ofNullable(action.perform());
    }

    private List<LoginHistoryAnemia> loadAll(LoginHistoryLoadRequest request) {
        LoginHistoryAnemiaLoadAction action = new LoginHistoryAnemiaLoadAction(databaseConnection.getConfiguration(), request);
        return action.performList();
    }


    private LoginHistory mapIntoDomain(LoginHistoryAnemia anemia) {
        return new LoginHistoryEntity(
                anemia.getId(),
                Period.of(anemia.getLoginTime(), anemia.getLogoutTime()),
                anemia.getAccountId());
    }

}
