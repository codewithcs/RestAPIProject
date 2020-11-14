import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.logging.Logger;

public class JsonParser {
    final int MAX = 25;
    String errorMessage;
    final Logger LOGGER = Logger.getLogger(HttpRequest.class.getName());

    Ticket parseSingleTicket(String jsonContent) {
        JSONParser jsonParser = new JSONParser();
        try{
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonContent);
            JSONObject ticketStructure = (JSONObject) jsonObject.get("ticket");

            if(ticketStructure.isEmpty()) {
                errorMessage = "ERROR: No Records Found";
                LOGGER.warning(errorMessage);
                return null;
            }
            return parseTicketStructure(ticketStructure);
        }
        catch (Exception e) {
            LOGGER.severe("Exception: " + e);
            errorMessage = "Exception: " + e;
            return null;
        }
    }

    private Ticket parseTicketStructure(JSONObject ticketStructure) {
        Ticket ticket = new Ticket();
        if(ticketStructure == null) {
            return null;
        }
        ticket.url = (String)ticketStructure.get("url");
        ticket.id = (Long)ticketStructure.get("id");
        ticket.createdAt = (String)ticketStructure.get("created_at");
        ticket.type = (String)ticketStructure.get("type");
        ticket.subject = (String)ticketStructure.get("subject");
        ticket.description = (String) ticketStructure.get("description");
        ticket.priority = (String) ticketStructure.get("priority");
        return ticket;
    }

    public TicketList parseTicketsList(String jsonContent) {
        TicketList ticketList = new TicketList();
        JSONParser jsonParser = new JSONParser();

        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(jsonContent);
            JSONArray ticketArray = (JSONArray)  jsonObject.get("tickets");
            if(ticketArray.isEmpty()) {
                errorMessage = "ERROR: No Records Found";
                LOGGER.warning(errorMessage);
                return null;
            }
            if(ticketArray.size() < MAX) {
                ticketList.tickets = new Ticket[ticketArray.size()];
            } else {
                ticketList.tickets = new Ticket[MAX];
            }

            for(int i=0; i<ticketList.tickets.length; i++) {
                JSONObject ticketStructure = (JSONObject)ticketArray.get(i);
                Ticket t = parseTicketStructure(ticketStructure);
                if (t == null) {
                    errorMessage = "Error: No Records Found";
                    return null;
                }
                ticketList.tickets[i]  = t;
            }

            parseNavigationFlags(jsonObject,ticketList);
            return ticketList;
        } catch (Exception e) {
            LOGGER.severe("Exception: " + e);
            errorMessage = "Exception: " + e;
            return null;
        }
    }

    // To set the flags for next and previous page.
    private void parseNavigationFlags(JSONObject jsonObject, TicketList ticketList) {
        String nextPageUrl = (String) jsonObject.get("next_page");
        String previousPageUrl = (String) jsonObject.get("previous_page");

        if(nextPageUrl == null){
            ticketList.hasNext = false;
        } else {
            ticketList.hasNext = true;
        }

        if(previousPageUrl == null) {
            ticketList.hasPrevious = false;
        } else{
            ticketList.hasPrevious = true;
        }
    }
}
