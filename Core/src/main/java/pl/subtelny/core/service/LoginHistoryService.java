package pl.subtelny.core.service;

import pl.subtelny.beans.Autowired;
import pl.subtelny.beans.Component;
import pl.subtelny.generated.tables.daos.EntityLoginHistoryDao;

@Component
public class LoginHistoryService {

    private final EntityLoginHistoryDao loginHistoryDao;

    @Autowired
    public LoginHistoryService(EntityLoginHistoryDao loginHistoryDao) {
        this.loginHistoryDao = loginHistoryDao;
    }



}
