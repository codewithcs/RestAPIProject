import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TicketViewer {
    public static final int PER_PAGE = 25;
    public static Ticket ticket = new Ticket();
    public static TicketList ticketList = new TicketList();
    public static String response;
    public static boolean hasNext = false, hasPrev = false;
    public static boolean displayOptions = true;

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
                        //Gate ticket list of 25 tickets
                        showTickets(page);
                        break;
                    }
                    case "n": {
                        if(hasNext != false) {
                            page++;
                            //Gate ticket list of 25 tickets
                            showTickets(page);
                        }
                        else {
                            System.out.println("INPUT ERROR: Cannot navigate to the next page");
                        }
                        break;
                    }
                    //Navigate to previous page, available only in list view
                    case "p": {
                        if(hasPrev != false && page>1) {
                            page--;
                            //Gate ticket list of 25 tickets
                            showTickets(page);
                        }
                        else {
                            System.out.println("INPUT ERROR: Cannot navigate to the previous page");
                        }
                        break;
                    }
                    //Exit
                    case "quit": {
                        System.out.println("Thank you for using Ticket Viewer. Hope you enjoyed our service");
                        return;
                    }
                    default:
                        System.out.println("INVALID INPUT: Please Try Again");
                }
            }
            catch (IOException e) {
                System.out.println("ERROR: An error occured while reading input");
                System.out.println(e.getMessage());
                System.out.println(e.getStackTrace());
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
