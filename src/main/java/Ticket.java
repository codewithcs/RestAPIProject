import java.util.ArrayList;
import java.util.logging.Logger;

public class Ticket {
    final String route = "/api/v2/tickets";
    String url;
    long id;
    String createdAt;
    String subject;
    String description;
    String type;
    String priority;

    @Override
    public String toString(){
        return "[" + this.url +", " + this.id + ", " + this.createdAt + ", " + this.subject + ", " + this.description + ", " + this.type + ", " + this.priority + "]";
    }

    static final Logger LOGGER = Logger.getLogger(HttpRequest.class.getName());

    public String getTicketData(int ticketId) {
        if(ticketId <= 0) {
            return "ERROR: ID must be > 0";
        }

        LOGGER.info("Processing your request, please wait...\n");
        HttpRequest httpRequest = new HttpRequest();
        String url = route + "/" + ticketId + ".json";
        String response = httpRequest.get(url, null);
        int responseCode = httpRequest.getResponseCode();

        if(responseCode> 199 && responseCode < 400) {
            JsonParser jsonParser = new JsonParser();
            Ticket ticket = jsonParser.parseSingleTicket(response);
            if(ticket == null) {
                return JsonParser.errorMessage;
            }

            ticket.displayTicketData();
            return "SUCCESS";
        }

        return httpRequest.displayErrorMessage(responseCode);
    }

    void displayTicketData(){
        System.out.format("\n %66s \n", "** Displaying ticket number " + this.id + " **");
        System.out.println(" ------------------------------------------------------------------------------------------------------------------------");
        System.out.format("| Subject: %-82s  Created at: %-25s |\n", this.subject, this.createdAt);
        System.out.format("|%-132s|\n| Description: %-116s  |\n", " ","");
        int length = this.description.length();
        if(length <= 130) {
            System.out.format("| %-130s |\n", this.description);
        }
        ArrayList<String> descWrapped = WrapTextFullWords(this.description, 130);
        for (int i=0; i< descWrapped.size(); i++) {
            System.out.format("| %-130s |\n", descWrapped.get(i));
        }

        System.out.println(" ------------------------------------------------------------------------------------------------------------------------");
    }

    private ArrayList<String> WrapTextFullWords (String str, int maxLength) {
        ArrayList<String> result = new ArrayList<String>();
        str = str.replace("\n", "");
        String[] wordsArray = str.split(" ");
        String line = "";
        //Wrap text
        for(int i=0; i<wordsArray.length; i++) {
            if(line.length() + wordsArray[i].length() <= maxLength-1) {
                line = line.concat(wordsArray[i] + " ");
            } else {
                i--;
                result.add(line);
                line = "";
            }
        }
        result.add(line);
        return result;
    }
}