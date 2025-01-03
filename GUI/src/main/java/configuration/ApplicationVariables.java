package configuration;

public class ApplicationVariables {
    public static final String APPLICATION_URL = ConfigurationProperties.getProperties().getProperty("url");
    public static final String FILE_NAME = ConfigurationProperties.getProperties().getProperty("fileName");
}
