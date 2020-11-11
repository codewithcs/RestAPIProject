import static org.junit.Assert.*;
import org.junit.Test;

public class JsonParserTest {
    JsonParser parser = new JsonParser();
    Ticket ticket = new Ticket();

    String ticket2Json = "{\"ticket\":{\"url\":\"https://codewithcs.zendesk.com/api/v2/tickets/2.json\",\"id\":2,\"external_id\":null,\"via\":{\"channel\":\"sample_ticket\",\"source\":{\"from\":{},\"to\":{},\"rel\":null}},\"created_at\":\"2020-11-07T10:50:53Z\",\"updated_at\":\"2020-11-11T11:02:19Z\",\"type\":\"incident\",\"subject\":\"I need help\",\"raw_subject\":\"I need help\",\"description\":\"Hello,\\nSomething dramatic happened and I could really use your help.\\nThanks in advance,\\nCustomer\\n\",\"priority\":\"normal\",\"status\":\"closed\",\"recipient\":null,\"requester_id\":406012916212,\"submitter_id\":406012915092,\"assignee_id\":406012915092,\"organization_id\":null,\"group_id\":360013591172,\"collaborator_ids\":[],\"follower_ids\":[],\"email_cc_ids\":[],\"forum_topic_id\":null,\"problem_id\":null,\"has_incidents\":false,\"is_public\":true,\"due_at\":null,\"tags\":[\"sample\",\"support\",\"zendesk\"],\"custom_fields\":[],\"satisfaction_rating\":null,\"sharing_agreement_ids\":[],\"fields\":[],\"followup_ids\":[],\"brand_id\":360004878252,\"allow_channelback\":false,\"allow_attachments\":true}}";
    String expectedTicket2 = "[https://codewithcs.zendesk.com/api/v2/tickets/2.json, 2, 2020-11-07T10:50:53Z, I need help, Hello,\nSomething dramatic happened and I could really use your help.\nThanks in advance,\nCustomer\n, incident, normal]";

    String ticket3And4Json = "{\"tickets\":[{\"url\":\"https://codewithcs.zendesk.com/api/v2/tickets/3.json\",\"id\":3,\"external_id\":null,\"via\":{\"channel\":\"api\",\"source\":{\"from\":{},\"to\":{},\"rel\":null}},\"created_at\":\"2020-11-07T11:52:11Z\",\"updated_at\":\"2020-11-11T14:34:32Z\",\"type\":\"problem\",\"subject\":\"velit eiusmod reprehenderit officia cupidatat\",\"raw_subject\":\"velit eiusmod reprehenderit officia cupidatat\",\"description\":\"Aute ex sunt culpa ex ea esse sint cupidatat aliqua ex consequat sit reprehenderit. Velit labore proident quis culpa ad duis adipisicing laboris voluptate velit incididunt minim consequat nulla. Laboris adipisicing reprehenderit minim tempor officia ullamco occaecat ut laborum.\\n\\nAliquip velit adipisicing exercitation irure aliqua qui. Commodo eu laborum cillum nostrud eu. Mollit duis qui non ea deserunt est est et officia ut excepteur Lorem pariatur deserunt.\",\"priority\":\"low\",\"status\":\"open\",\"recipient\":null,\"requester_id\":406012915092,\"submitter_id\":406012915092,\"assignee_id\":406012915092,\"organization_id\":361223187292,\"group_id\":360013591172,\"collaborator_ids\":[],\"follower_ids\":[],\"email_cc_ids\":[],\"forum_topic_id\":null,\"problem_id\":null,\"has_incidents\":false,\"is_public\":true,\"due_at\":null,\"tags\":[\"est\",\"incididunt\",\"nisi\"],\"custom_fields\":[],\"satisfaction_rating\":null,\"sharing_agreement_ids\":[],\"fields\":[],\"followup_ids\":[],\"brand_id\":360004878252,\"allow_channelback\":false,\"allow_attachments\":true},{\"url\":\"https://codewithcs.zendesk.com/api/v2/tickets/4.json\",\"id\":4,\"external_id\":null,\"via\":{\"channel\":\"api\",\"source\":{\"from\":{},\"to\":{},\"rel\":null}},\"created_at\":\"2020-11-07T11:52:12Z\",\"updated_at\":\"2020-11-11T14:35:18Z\",\"type\":\"question\",\"subject\":\"excepteur laborum ex occaecat Lorem\",\"raw_subject\":\"excepteur laborum ex occaecat Lorem\",\"description\":\"Exercitation amet in laborum minim. Nulla et veniam laboris dolore fugiat aliqua et sit mollit. Dolor proident nulla mollit culpa in officia pariatur officia magna eu commodo duis.\\n\\nAliqua reprehenderit aute qui voluptate dolor deserunt enim aute tempor ad dolor fugiat. Mollit aliquip elit aliqua eiusmod. Ex et anim non exercitation consequat elit dolore excepteur. Aliqua reprehenderit non culpa sit consequat cupidatat elit.\",\"priority\":\"normal\",\"status\":\"open\",\"recipient\":null,\"requester_id\":406012915092,\"submitter_id\":406012915092,\"assignee_id\":406012915092,\"organization_id\":361223187292,\"group_id\":360013591172,\"collaborator_ids\":[],\"follower_ids\":[],\"email_cc_ids\":[],\"forum_topic_id\":null,\"problem_id\":null,\"has_incidents\":false,\"is_public\":true,\"due_at\":null,\"tags\":[\"amet\",\"labore\",\"voluptate\"],\"custom_fields\":[],\"satisfaction_rating\":null,\"sharing_agreement_ids\":[],\"fields\":[],\"followup_ids\":[],\"brand_id\":360004878252,\"allow_channelback\":false,\"allow_attachments\":true}],\"next_page\":\"https://codewithcs.zendesk.com/api/v2/tickets.json?page=3&per_page=2\",\"previous_page\":\"https://codewithcs.zendesk.com/api/v2/tickets.json?page=1&per_page=2\",\"count\":103}";
    String expectedTicket3And4Json = "[https://codewithcs.zendesk.com/api/v2/tickets/3.json, 3, 2020-11-07T11:52:11Z, velit eiusmod reprehenderit officia cupidatat, Aute ex sunt culpa ex ea esse sint cupidatat aliqua ex consequat sit reprehenderit. Velit labore proident quis culpa ad duis adipisicing laboris voluptate velit incididunt minim consequat nulla. Laboris adipisicing reprehenderit minim tempor officia ullamco occaecat ut laborum.\n" +
            "\n" +
            "Aliquip velit adipisicing exercitation irure aliqua qui. Commodo eu laborum cillum nostrud eu. Mollit duis qui non ea deserunt est est et officia ut excepteur Lorem pariatur deserunt., problem, low][https://codewithcs.zendesk.com/api/v2/tickets/4.json, 4, 2020-11-07T11:52:12Z, excepteur laborum ex occaecat Lorem, Exercitation amet in laborum minim. Nulla et veniam laboris dolore fugiat aliqua et sit mollit. Dolor proident nulla mollit culpa in officia pariatur officia magna eu commodo duis.\n" +
            "\n" +
            "Aliqua reprehenderit aute qui voluptate dolor deserunt enim aute tempor ad dolor fugiat. Mollit aliquip elit aliqua eiusmod. Ex et anim non exercitation consequat elit dolore excepteur. Aliqua reprehenderit non culpa sit consequat cupidatat elit., question, normal]";

    @Test
    public void parseSingleTicket(){
        ticket = parser.parseSingleTicket(ticket2Json);
        assertEquals( expectedTicket2, ticket.toString());
    }

    @Test
    public void parseNonJson(){
        String content = "This is a simple string";
        ticket = parser.parseSingleTicket(content);
        assertEquals("Exception: Unexpected character (T) at position 0.", parser.errorMessage);
        assertEquals(null, ticket);
    }

    @Test
    public void parseEmptyTicket(){
        String content = "{\"ticket\":{},\"previous_page\":\"https://codewithcs.zendesk.com/api/v2/tickets.json?page=11\",\"count\":111}";
        ticket = parser.parseSingleTicket(content);
        assertEquals(null, ticket);
        assertEquals("ERROR: No Records Found", parser.errorMessage);
    }

    @Test
    public void parseValidTicketsList(){
        TicketList ticketList = parser.parseTicketsList(ticket3And4Json);
        Ticket[] ticket = ticketList.tickets;
        StringBuilder actualTicket3and4Json = new StringBuilder();
        for(Ticket t : ticket){
            actualTicket3and4Json.append(t.toString());
        }
        assertEquals(expectedTicket3And4Json, actualTicket3and4Json.toString());
    }

    @Test
    public void parseEmptyTickets(){
        TicketList ticketList = new TicketList();
        String content = "{\"tickets\":[],\"next_page\":null,\"previous_page\":\"https://codewithcs.zendesk.com/api/v2/tickets.json?page=1233\",\"count\":101}";
        ticketList = parser.parseTicketsList(content);
        assertEquals(null, ticketList);
        assertEquals("ERROR: No Records Found", parser.errorMessage);
    }

    @Test
    public void TicketListParsingForNonJsonContent(){
        TicketList ticketList = new TicketList();
        String content = "Simple String";
        ticketList = parser.parseTicketsList(content);
        assertEquals(null, ticketList);
        assertEquals("Exception: Unexpected character (S) at position 0.", parser.errorMessage);
    }
}