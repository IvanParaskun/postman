package space.paraskun.postman.google.sheets;

import space.paraskun.postman.google.account.OAuthCredential;
import space.paraskun.postman.google.oauth.RefreshTokenRevokedException;

public interface SheetsService {
	String getSpreadsheetOrCreate(OAuthCredential credential) throws RefreshTokenRevokedException;
}
