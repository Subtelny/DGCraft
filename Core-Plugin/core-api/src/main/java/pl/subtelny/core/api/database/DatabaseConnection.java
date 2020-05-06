package pl.subtelny.core.api.database;

import org.jooq.Configuration;

import javax.sql.DataSource;

public interface DatabaseConnection {

    DataSource getDataSource();

    Configuration getConfiguration();

}
