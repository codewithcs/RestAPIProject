import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TicketTest {
    Ticket ticket = new Ticket();

    @Test
    public void validTicket(){
        String result = ticket.getTicketData(1);
        assertEquals("SUCCESS", result);
    }

    @Test
    public void invalidTicket(){
        String result = ticket.getTicketData(-1);
        assertEquals("ERROR: ID must be > 0", result);
    }

    @Test
    public void nonExistingTicket(){
        String result = ticket.getTicketData(1000);
        assertEquals("Not Found: Requested resource cannot be found", result);
    }

}
