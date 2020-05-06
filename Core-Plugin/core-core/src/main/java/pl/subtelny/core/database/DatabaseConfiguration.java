package pl.subtelny.core.database;

public class DatabaseConfiguration {

    private final String dbType;

    private final String dbHost;

    private final String dbPort;

    private final String dbBase;

    private final String dbOptions;

    private final String dbDriver;

    private final String dbUser;

    private final String dbPassword;

    public DatabaseConfiguration(
            String dbType,
            String dbHost,
            String dbPort,
            String dbBase,
            String dbOptions,
            String dbDriver,
            String dbUser,
            String dbPassword) {
        this.dbType = dbType;
        this.dbHost = dbHost;
        this.dbPort = dbPort;
        this.dbBase = dbBase;
        this.dbOptions = dbOptions;
        this.dbDriver = dbDriver;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
    }

    public String getDbType() {
        return dbType;
    }

    public String getDbHost() {
        return dbHost;
    }

    public String getDbPort() {
        return dbPort;
    }

    public String getDbBase() {
        return dbBase;
    }

    public String getDbOptions() {
        return dbOptions;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    @Override
    public String toString() {
        return "DatabaseConfiguration{" +
                "dbType='" + dbType + '\'' +
                ", dbHost='" + dbHost + '\'' +
                ", dbPort='" + dbPort + '\'' +
                ", dbBase='" + dbBase + '\'' +
                ", dbOptions='" + dbOptions + '\'' +
                ", dbDriver='" + dbDriver + '\'' +
                ", dbUser='" + dbUser + '\'' +
                ", dbPassword='" + dbPassword + '\'' +
                '}';
    }
}
