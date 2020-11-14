import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;

public class TicketViewer {
    static final int PER_PAGE = 25;
    static Ticket ticket = new Ticket();
    static TicketList ticketList = new TicketList();
    static String response;
    static boolean hasNext = false, hasPrev = false;
    static boolean displayOptions = true;
    static final Logger LOGGER = Logger.getLogger(HttpRequest.class.getName());

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int page = 1;

        while(true) {
            try {
                if(displayOptions) {
                    displayOptions();
                }
                String userInput = reader.readLine();
                switch(userInput) {

                    case "1": {
                        showTickets(page);
                        break;
                    }
                    case "n": {
                        if(hasNext != false) {
                            page++;
                            showTickets(page);
                        } else {
                            LOGGER.info("INPUT ERROR: Cannot navigate to the next page\n");
                            System.out.println("Type 'p' to move to the previous page");
                            System.out.println("Type 'quit' to exit");
                        }
                        break;
                    }
                    case "p": {
                        if(hasPrev != false && page>1) {
                            page--;
                            showTickets(page);
                        } else {
                            LOGGER.info("INPUT ERROR: Cannot navigate to the previous page");
                            System.out.println("Type 'n' to move to the next page");
                            System.out.println("Type 'quit' to exit");
                        }
                        break;
                    }
                    case "quit": {
                        LOGGER.info("Thank you for using Ticket Viewer. Hope you enjoyed our service");
                        return;
                    }
                    default:
                        LOGGER.info("INVALID INPUT: Please Try Again");
                }
            }
            catch (IOException e) {
                LOGGER.severe("ERROR: An error occurred while reading input " + e);
            }
        }
    }

    private static void showTickets(int page) {
        response = ticketList.listTickets(page, PER_PAGE);
        if(!response.equals("SUCCESS")){
            System.out.println(response);
        }
        hasNext = ticketList.hasNext;
        hasPrev = ticketList.hasPrevious;
        displayNavigationMenu();
    }

    private static void displayOptions() {
        System.out.println("\nSelect view option:");
        System.out.println("Type '1' to view all the tickets");
        System.out.println("Type 'quit' to exit");
    }

    private static void displayNavigationMenu() {
        displayOptions = false;
        System.out.println("\nType 'n' to move to the next page");
        System.out.println("Type 'p' to move to the previous page");
        System.out.println("Type 'quit' to exit");
    }
}
