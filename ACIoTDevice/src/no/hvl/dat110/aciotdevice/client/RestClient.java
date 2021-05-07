package no.hvl.dat110.aciotdevice.client;

import java.io.BufferedReader;

//import static spark.Spark.post;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.http.HttpClient;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class RestClient {

	public RestClient() {
		// TODO Auto-generated constructor stub
	}

	private static String logpath = "/accessdevice/log";

	// public static final MediaType JSON = MediaType.parse("application/json;
	// charset=utf-8");

	public void doPostAccessEntry(String message) {

		// TODO: implement a HTTP POST on the service to post the message
		String msg = "{" + "\"message\": \"" + message + "\"" + "}";
		System.out.println(msg);
		try {
			URL url = new URL("http://localhost:8080" + logpath);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setDoOutput(true);
			con.addRequestProperty("Content-Type", "application/json");
			con.getOutputStream().write(msg.getBytes("UTF8"));
			con.getResponseCode();
			con.getResponseMessage();
			con.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static String codepath = "/accessdevice/code";

	public AccessCode doGetAccessCode() {

		AccessCode code = new AccessCode();

		// TODO: implement a HTTP GET on the service to get current access code
		String line = null;

		try {
			URL url = new URL("http://localhost:8080" + codepath);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(true);
			con.addRequestProperty("Content-Type", "application/json");
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			line = rd.readLine();
			rd.close();
			con.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		line = line.replaceAll("[^0-9]", "");
		char[] charArray = line.toCharArray();
		int[] intArray = new int[charArray.length];

		String accessCode = "{" + "AccessCode:" + "[";
		for (int i = 0; i < charArray.length; i++) {
			intArray[i] = charArray[i] - 48;
			System.out.println("int:" + intArray[i]);
			accessCode += intArray[i];

			if (i != charArray.length - 1) {
				accessCode += ",";
			}
		}

		code.setAccesscode(intArray);
		accessCode += "]";
		accessCode += "}";
		System.out.println(accessCode);

		return code;
	}
}
