import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class HttpRequestTest {
    HttpRequest httpRequest = new HttpRequest();

    @Test
    public void getRequest(){
        httpRequest.get("/api/v2/tickets.json", null);
        String result = httpRequest.get("/api/v2/tickets.json", "?page=2&per_page=2");
        System.out.println("Result is : " + result);
        assertEquals(200, httpRequest.getResponseCode());
    }

    @Test
    public void getRequestWithParameters(){
        httpRequest.get("/api/v2/tickets.json", "?page=1&per_page=2");
        assertEquals(200, httpRequest.getResponseCode());
    }

    @Test
    public void getRequestWithNonExistingRoute(){
        String response = httpRequest.get("/api/v2/tickets00000.json", "?page=1&per_page=2");
        assertEquals(404, httpRequest.getResponseCode());
        assertEquals("Not Found: Requested resource cannot be found", response);
    }

    @Test
    public void invalidCredentials(){
        httpRequest = new HttpRequest("abc", "def".toCharArray(), "https://codewithcs.zendesk.com");
        String response = httpRequest.get("/api/v2/tickets.json", null);
        assertEquals(403, httpRequest.getResponseCode());
        assertEquals("Forbidden: Not enough permissions", response);
    }

    @Test
    public void invalidURL(){
        httpRequest = new HttpRequest("abc", "def".toCharArray(), "xyz");
        String response = httpRequest.get("/api/v2/tickets.json", null);
        assertEquals(0, httpRequest.getResponseCode());
        assertEquals("IOException occurred : java.net.MalformedURLException: no protocol: xyz/api/v2/tickets.jsonnull", response);
    }

    @Test
    public void badClientRequest(){
        httpRequest.get("/api/v2/tickets.json?page=2&per_page=2", null);
        assertEquals(400, httpRequest.getResponseCode());
    }
}
