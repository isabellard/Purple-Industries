package co.edu.unbosque.purpleindustries.persistance;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.DeleteDimensionRequest;
import com.google.api.services.sheets.v4.model.DimensionRange;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class SheetsManager {

	private static Sheets sheetService;
	private static String APLICATION_NAME = "Google Sheets Example";
	private static String SPREADSHEET_ID = "1GG7eWByvVlWKzcgyYBlHTw_CNUjWros9LkZcIxcKHj0";

	private static Credential authorize() {
		try {
			InputStream in = SheetsManager.class.getResourceAsStream("/credenciales.json");

			GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(GsonFactory.getDefaultInstance(),
					new InputStreamReader(in));

			List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);

			GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
					GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(), clientSecrets,
					scopes).setDataStoreFactory(new FileDataStoreFactory(new File("tokens"))).setAccessType("offline")
					.build();

			Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver())
					.authorize("user");

			return credential;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static Sheets getSheetsService() {
		try {
			Credential credential;
			credential = authorize();
			return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance(),
					credential).setApplicationName(APLICATION_NAME).build();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		return null;

	}

	public static void leerDatos(String nombreHoja, String rango) {
		try {
			sheetService = getSheetsService();

			ValueRange response = sheetService.spreadsheets().values().get(SPREADSHEET_ID, "nombreHoja!"+rango).execute();

			List<List<Object>> values = response.getValues();

			if (values == null || values.isEmpty()) {
				System.out.println("No data found.");
			} else {
				for (List row : values) {
					System.out.printf("%s %s form %s\n", row.get(5), row.get(4), row.get(1));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<List<String>> leerDesdeSheets(String nombreHoja, String rango) {
	    List<List<String>> datos = new ArrayList<>();
	    
	    try {
	        sheetService = getSheetsService();
	        ValueRange response = sheetService.spreadsheets().values()
	            .get(SPREADSHEET_ID, nombreHoja + "!" + rango)
	            .execute();

	        List<List<Object>> values = response.getValues();

	        if (values == null || values.isEmpty()) {
	            return datos;
	        }
	      
	        for (List<Object> filaObjeto : values) {
	            List<String> filaString = new ArrayList<>();
	            for (Object celda : filaObjeto) {
	                filaString.add(celda != null ? celda.toString() : "");
	            }
	            datos.add(filaString);
	        }
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    return datos;
	}

	public static void crear(List<List<Object>> datos, String nombreHoja) {
		sheetService = getSheetsService();
		eliminarTodo(nombreHoja); 
		ValueRange appendBody = new ValueRange()
				.setValues(datos)
		        .setMajorDimension("ROWS");
		try {
			AppendValuesResponse appendResult = getSheetsService().spreadsheets().values()
					.append(SPREADSHEET_ID, nombreHoja, appendBody).setValueInputOption("USER_ENTERED")
					.setInsertDataOption("INSERT_ROWS").setIncludeValuesInResponse(true).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void actualizar(String url) {
		sheetService = getSheetsService();
		ValueRange body = new ValueRange().setValues(Arrays.asList(Arrays.asList("updated")));
		try {
			UpdateValuesResponse result = getSheetsService().spreadsheets().values().update(url, "C543", body)
					.setValueInputOption("RAW").execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void eliminar(String url, int lasturl) {
		sheetService = getSheetsService();
		DeleteDimensionRequest deleteRequest = new DeleteDimensionRequest()
				.setRange(new DimensionRange().setSheetId(lasturl).setDimension("ROWS").setStartIndex(20));

		List<Request> requests = new ArrayList<>();
		requests.add(new Request().setDeleteDimension(deleteRequest));

		BatchUpdateSpreadsheetRequest body = new BatchUpdateSpreadsheetRequest().setRequests(requests);
		try {
			sheetService.spreadsheets().batchUpdate(url, body).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void eliminarTodo(String nombreHoja) {
		sheetService = getSheetsService();
		try {
			sheetService.spreadsheets().values().clear(SPREADSHEET_ID, nombreHoja, new ClearValuesRequest()).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
