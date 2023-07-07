package space.paraskun.postman.storage.provider;

import space.paraskun.postman.security.AuthenticationException;
import space.paraskun.postman.security.model.Credential;
import space.paraskun.postman.storage.StorageException;

import java.util.List;
import java.util.Map;

public interface StorageProvider<T extends Credential> {
    T prepareStorage(T credential) throws AuthenticationException, StorageException;
    List<Map<Object, Object>> extractData(T credential, String sheet) throws AuthenticationException, StorageException;
}
