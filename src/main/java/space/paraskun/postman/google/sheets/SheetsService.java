package space.paraskun.postman.google.sheets;

import space.paraskun.postman.google.model.GoogleAccount;

public interface SheetsService {
    String createSpreadsheet(GoogleAccount.Credential credential);
}
