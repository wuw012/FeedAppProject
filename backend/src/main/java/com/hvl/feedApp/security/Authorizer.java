package com.hvl.feedApp.security;


import com.hvl.feedApp.Agent;
import com.hvl.feedApp.Enums.Role;
import com.hvl.feedApp.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Hashtable;

@Component
public class Authorizer {

    Hashtable POST;
    Hashtable GET;
    Hashtable PUT;
    Hashtable DELETE;

    public Authorizer() {
        POST = new Hashtable<String, Role>();
        GET = new Hashtable<String, Role>();
        PUT = new Hashtable<String, Role>();
        DELETE = new Hashtable<String, Role>();

        // Lock endpoints
        GET.put("/agents", Role.ADMIN);
        GET.put("/agents/{id}", Role.USER);
        GET.put("/agents/test", Role.USER);
        POST.put("/agents", Role.ADMIN);
        POST.put("/agents/{id}", Role.ADMIN);
        DELETE.put("/agents/{id}", Role.ADMIN);
        PUT.put("/agents/{id}", Role.ADMIN);
        GET.put("/agents/byUsername/{username}", Role.USER);

        GET.put("/polls", Role.ADMIN);
        GET.put("/polls/{pollID}", Role.USER);
        GET.put("/polls/{username}/userPolls", Role.USER);
        POST.put("/polls", Role.USER);
        DELETE.put("/polls/{pollID}", Role.USER);
        PUT.put("/polls/{pollID}", Role.ADMIN);
        POST.put("/polls/{pollID}/batchVote", Role.USER);
        POST.put("/polls/{pollID}/votes", Role.USER);
        GET.put("/polls/{pollID}/votes", Role.ADMIN);

        GET.put("/vote", Role.ADMIN);
        GET.put("vote/{voteID}", Role.ADMIN);
        DELETE.put("vote/{voteID}", Role.ADMIN);
    }
    public boolean isAuthorized(Agent user, String path, String httpMethod) {
        Role userRole = user.getRole();
        switch (httpMethod) {
            case "POST":
                return isAllowed(POST, path, userRole);
            case "GET":
                return isAllowed(GET, path, userRole);
            case "PUT":
                return isAllowed(PUT, path, userRole);
            case "DELETE":
                return isAllowed(DELETE, path, userRole);
        }
        return false;
    }

    private boolean isAllowed(Hashtable<String, Role> permissionSet, String path, Role userRole) {
        if (permissionSet.containsKey(path)) {
            return roleAtLevel(userRole, permissionSet.get(path));
        }
        else return true;
    }


    public boolean roleAtLevel(Role role1, Role role2) {
        switch (role1) {
            case USER:
            case DEVICE:
                if (role2 == Role.ADMIN) {
                    return false;
                }else return true;
            case ADMIN:
                return true;
        }
        return false;
    }
}