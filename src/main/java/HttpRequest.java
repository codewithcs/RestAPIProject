import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.logging.Logger;

public class HttpRequest {
    final String USERNAME;
    final char[] PASSWORD;
    final String SUBDOMAIN;
    static final Logger LOGGER = Logger.getLogger(HttpRequest.class.getName());
    static int responseCode = 0;

    HttpRequest(){
        this.USERNAME = "schaudha@tcd.ie";
        this.PASSWORD = "Typescript@123".toCharArray();
        this.SUBDOMAIN = "https://codewithcs.zendesk.com";
    }

    HttpRequest(String username, char[] password, String subdomain){
        this.USERNAME = username;
        this.PASSWORD = password;
        this.SUBDOMAIN = subdomain;
    }

    int getResponseCode(){
        return responseCode;
    }

    void setResponseCode(int responseCode){
        this.responseCode = responseCode;
    }

    public String get(String route, String urlParameters){
        HttpURLConnection connection = null;
        String url = SUBDOMAIN + route;

        try{
            URL object = new URL(url+ urlParameters);
            connection = (HttpURLConnection) object.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            Authenticator.setDefault(new Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            });

            byte[] message = (USERNAME + ":" + String.valueOf(PASSWORD)).getBytes("UTF-8");
            String encoding = DatatypeConverter.printBase64Binary(message);
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");

            responseCode = connection.getResponseCode();

            if(responseCode < 300 && responseCode > 199){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = reader.readLine()) != null) {
                    response.append(inputLine);
                }
                reader.close();
                return response.toString();
            } else {
                return displayErrorMessage(responseCode);
            }

        } catch(IOException e){
            LOGGER.severe("IOException occurred : " + e);
            return "IOException occurred : " + e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    String displayErrorMessage(int code){
        switch(code){
            case 400:
                return "Bad Request: Client Error";
            case 401:
                return "Unauthorized: Authentication is Required";
            case 403:
                return "Forbidden: Not enough permissions";
            case 404:
                return "Not Found: Requested resource cannot be found";
            default:
                return "Some Error occurred. Please debug the issue.";
        }
    }

}
