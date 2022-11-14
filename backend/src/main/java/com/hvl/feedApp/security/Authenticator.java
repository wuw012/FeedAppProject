package com.hvl.feedApp.security;

import java.util.Base64;
import java.util.List;

import com.hvl.feedApp.Agent;
import com.hvl.feedApp.repository.AgentRepository;
import com.hvl.feedApp.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class Authenticator {


    private AgentService agentService;
    private Agent user;
    private Boolean authenticated;

    @Autowired
    public Authenticator(AgentService agentService) {
        this.agentService = agentService;
    }

    // Method for confirming basic authentications
    public Boolean isAuthenticated(String authBody) {
        String bAuthSliced = authBody.substring(6, authBody.length()); //Remove "Basic " from string
        byte[] decodedBAuth = Base64.getDecoder().decode(bAuthSliced); // Decode base64 string
        String authString = new String(decodedBAuth); // Convert decoded base64 to string


        String[] unamePwdSplit = authString.split(":", 2); // Split out username and password
        String uname = unamePwdSplit[0]; // Extract username
        String pwd = unamePwdSplit[1]; // Extract password

        List<Agent> users = agentService.getAgents();

   //     System.out.println("Username: "+ uname + " Password: "+pwd);
        for (Agent user : users) {
  //          String foundUname = user.getUsername();
//            System.out.println(user.getUsername() +" == " +uname + " is "+(user.getUsername().equals(uname)) + " && " +user.getPassword() +" == " +pwd);

            if ((user.getUsername().equals(uname)) && (user.getPassword().equals(pwd))) {
                this.setUser(user);
                return true;
            }
        }
        return false;
    };

    private void setUser(Agent user) {
        this.user = user;
    }

    public Agent getUser() {
        return user;
    }

    public Boolean getAuthenticated(){
        return authenticated;
    }

}
