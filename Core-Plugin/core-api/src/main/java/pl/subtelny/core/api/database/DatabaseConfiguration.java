package pl.subtelny.core.api.database;

import org.jooq.Configuration;

import javax.sql.DataSource;

public interface DatabaseConfiguration {

    DataSource getDataSource();

    Configuration getConfiguration();

}