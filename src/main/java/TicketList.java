import java.util.logging.Logger;

public class TicketList {
    public static final String ROUTE = "/api/v2/tickets.json";
    Ticket[] tickets;
    boolean hasPrevious = false;
    boolean hasNext = false;
    static final Logger LOGGER = Logger.getLogger(HttpRequest.class.getName());
    TicketList(){}

    TicketList(Ticket[] tickets){
        this.tickets = tickets;
    }

    String listTickets(int pageNumber, int ticketsPerPage) {
        if(pageNumber < 1) {
            return "ERROR: Page Number must be > 0";
        } else if(ticketsPerPage < 1) {
            return "ERROR: Number of Records in a request must be > 0";
        }

        LOGGER.info("Processing your Request, Please Wait...\n");
        HttpRequest httpRequest = new HttpRequest();
        String parameters = "?page=" + pageNumber + "&per_page=" + ticketsPerPage;
        String response = httpRequest.get(ROUTE, parameters); // json string.

        int responseCode = httpRequest.getResponseCode();
        if(responseCode> 199 && responseCode < 400) {
            JsonParser jsonParser = new JsonParser();
            TicketList ticketList = jsonParser.parseTicketsList(response);
            if(ticketList == null) {
                return JsonParser.errorMessage;
            }
            this.tickets = ticketList.tickets;
            this.hasNext = ticketList.hasNext;
            this.hasPrevious = ticketList.hasPrevious;
            displayList(pageNumber);
            return "SUCCESS";
        }

        return httpRequest.displayErrorMessage(responseCode) ;
    }


    private void displayList(int page) {
        System.out.format("\n %66s \n", "** Displaying page number " + page + " **");
        displayHeadline();
        for(int i=0; i<tickets.length; i++) {
            displayInformation(tickets[i]);
        }
        displayFooter();
    }

    private void displayInformation(Ticket ticket) {
        String str = ticket.subject;
        if (str.length() >50) {
            str = str.substring(0, 46) + "...";
        }
        System.out.format("| %-10s | %-10s | %-50s | %-10s | %-25s |\n", ticket.id+"", ticket.type, str, ticket.priority, ticket.createdAt);
    }

    private void displayHeadline() {
        System.out.println(" ------------------------------------------------------------------------------------------------------------------------");
        //Align left
        System.out.format("| %-10s | %-10s | %-50s | %-10s | %-25s |\n", "ID", "Type", "Subject", "Priority", "Created");
        System.out.println(" ------------------------------------------------------------------------------------------------------------------------");
    }

    private void displayFooter() {
        System.out.println(" ------------------------------------------------------------------------------------------------------------------------");
    }
}
