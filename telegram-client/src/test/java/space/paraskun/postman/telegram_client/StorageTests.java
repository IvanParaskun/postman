package space.paraskun.postman.telegram_client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import space.paraskun.postman.security.oauth2.google.GoogleCredential;
import space.paraskun.postman.storage.provider.StorageProvider;

@SpringBootTest
public class StorageTests {
    @Autowired
    private StorageProvider<GoogleCredential> provider;

    @Test
    public void prepare() {

    }
}
