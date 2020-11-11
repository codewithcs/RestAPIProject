import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TicketListTest {
    TicketList ticketList = new TicketList();

    @Test
    public void listTickets(){
        String result = ticketList.listTickets(1, 25);
        assertEquals("SUCCESS", result);
    }

    @Test
    public void negativePageNumber(){
        String result = ticketList.listTickets(-1, 25);
        assertEquals("ERROR: Page Number must be > 0", result);
    }

    @Test
    public void zeroPageNumber(){
        String result = ticketList.listTickets(0, 25);
        assertEquals("ERROR: Page Number must be > 0", result);
    }


    @Test
    public void invalidTicketsPerPage(){
        String result = ticketList.listTickets(1, -1);
        assertEquals("ERROR: Number of Records in a request must be > 0", result);
    }

    @Test
    public void nonExistingPageNumber(){
        String result = ticketList.listTickets(100, 5);
        assertEquals("ERROR: No Records Found", result);
    }
}
