/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Connections;

import projekti.Account.AccountService;
import projekti.Account.Account;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author mirka
 */
@Controller
public class ConnectionsController {
    @Autowired
    AccountService accountService;
    
    @Autowired
    ConnectionsService connectionsService;
    
    // Network view
    @GetMapping("/network")
    public String viewNetwork(Model model, @ModelAttribute Account searchedUser) {
        Account current = accountService.getCurrentUser();
        
        List<Account> users = connectionsService.listUsers(searchedUser);
        List<Account> received = connectionsService.listReceivedRequests(current);
        List<Account> requested = connectionsService.listSentRequests(current);
        
        model.addAttribute("searchedUser", new Account());
        model.addAttribute("currentUser", current);
        model.addAttribute("accounts", users);
        model.addAttribute("connections", current.getConnections());
        model.addAttribute("requested", requested);
        model.addAttribute("received", received);
        
        return "network";
    }

    @GetMapping("/mynetwork")
    public String viewOwnConnections(Model model, @ModelAttribute Account searchedUser) {
        Account current = accountService.getCurrentUser();
        List<Account> users = connectionsService.listConnections(searchedUser, current);
        
        model.addAttribute("searchedUser", new Account());
        model.addAttribute("currentUser", current);
        model.addAttribute("accounts", users);
        
        return "mynetwork";
    }
    
    @GetMapping("/requests")
    public String viewPendingRequests(Model model) {
        Account current = accountService.getCurrentUser();
        
        List<Account> received = connectionsService.listReceivedRequests(current);
        List<Account> requested = connectionsService.listSentRequests(current);
        
        model.addAttribute("currentUser", current);
        model.addAttribute("received", received);
        model.addAttribute("requested", requested);
        
        return "requests";
    }
    
    // Add new connection request
    @PostMapping("/network/{id}/request")
    public String requestConnection(@PathVariable Long id) {
        connectionsService.requestNewConnection(id);
        
        return "redirect:/network";
    }
    
    @PostMapping("/network/{id}/remove")
    public String removeNetworkConnection(@PathVariable Long id) {
        connectionsService.removeFromConnections(id);
        
        return "redirect:/network";
    }
    
    @PostMapping("/network/{id}/accept")
    public String acceptNetworkRequest(@PathVariable Long id) {
        connectionsService.acceptRequest(id);
        
        return "redirect:/network";
    }
    
    @PostMapping("/network/{id}/reject")
    public String rejectNetworkRequest(@PathVariable Long id) {
        connectionsService.rejectRequest(id);
        
        return "redirect:/network";
    }
    
    @PostMapping("/network/{id}/cancel")
    public String cancelSentRequest(@PathVariable Long id) {
        connectionsService.cancelRequest(id);
        
        return "redirect:/network";
    }
    
    @PostMapping("/mynetwork/{id}/remove")
    public String removeConnection(@PathVariable Long id) {
        connectionsService.removeFromConnections(id);
        
        return "redirect:/mynetwork";
    }
    
    @PostMapping("/requests/{id}/accept")
    public String acceptRequestsPageRequest(@PathVariable Long id) {
        connectionsService.acceptRequest(id);
        
        return "redirect:/requests";
    }
    
    @PostMapping("/requests/{id}/reject")
    public String rejectRequestsPageRequest(@PathVariable Long id) {
        connectionsService.rejectRequest(id);
        
        return "redirect:/requests";
    }
    
    @PostMapping("/requests/{id}/cancel")
    public String cancelSentRequestsPageRequest(@PathVariable Long id) {
        connectionsService.cancelRequest(id);
        
        return "redirect:/requests";
    }
}
