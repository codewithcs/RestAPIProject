### Author: Shikhar Chaudhary
This project is available on GitHub at: https://github.com/codewithcs/RestAPIProject


## Pull the project from GitHub
1. git clone https://github.com/codewithcs/RestAPIProject.git 

## Command to Run Unit Tests and start the Ticket Viewer Java App: 
2. cd into the project directory and run the following maven command: 
* mvn clean install exec:java 

# Zendesk Ticket Viewer
Ticket Viewer is a tool (Menu Driven program), which connects to Zendesk API and allows us to:
* Display all tickets by pressing '1'. 
* Page through tickets when more than 25 are returned (by pressing 'n' or 'p').

# General Information
This is a Maven project including the following important files:
1. Source files directory is 'src/main/java' (HttpRequest.java, JsonParser.java, Ticket.java, TicketList.java, TicketViewer.java)
2. Test files directory is 'src/test/java' (HttpRequestTest.java, JsonParserTest.java, TicketList.java, TicketTest.java)
3. README.md
4. pom.xml
5. .gitignore

##Requirements:
* Apache Maven and external plugins and dependencies specified in pom.xml
* Java 8
