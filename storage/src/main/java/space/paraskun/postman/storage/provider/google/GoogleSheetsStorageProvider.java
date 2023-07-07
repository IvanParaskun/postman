package space.paraskun.postman.storage.provider.google;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import space.paraskun.postman.security.AuthenticationException;
import space.paraskun.postman.security.config.GoogleServices;
import space.paraskun.postman.security.oauth2.google.GoogleCredential;
import space.paraskun.postman.storage.StorageException;
import space.paraskun.postman.storage.provider.StorageProvider;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GoogleSheetsStorageProvider implements StorageProvider<GoogleCredential> {
    private final GoogleServices googleServices;

    @Override
    public GoogleCredential prepareStorage(GoogleCredential credential) throws AuthenticationException, StorageException {
        Drive drive = googleServices.drive(credential);

        try {
            FileList result = drive.files().list()
                    .setQ("name='Postman' and mimeType='application/vnd.google-apps.spreadsheet'")
                    .execute();

            for (File file: result.getFiles()) {

            }

            return null;
        } catch (IOException e) {
            throw new StorageException();
        }
    }

    @Override
    public List<Map<Object, Object>> extractData(GoogleCredential credential, String sheet) throws AuthenticationException, StorageException {
        return null;
    }
}
