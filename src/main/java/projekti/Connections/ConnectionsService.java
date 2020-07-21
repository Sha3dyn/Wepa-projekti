/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Connections;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import projekti.Account.Account;
import projekti.Account.AccountRepository;
import projekti.Account.AccountService;

/**
 *
 * @author mirka
 */
@Service
public class ConnectionsService {
    @Autowired
    ConnectionsRepository connectionsRepository;
    
    @Autowired
    AccountService accountService;
    
    @Autowired
    AccountRepository accountRepository;
    
    public void acceptRequest(Long id) {
        Account receiver = accountService.getCurrentUser();
        Account requester = accountRepository.getOne(id);
        
        Connections connections = new Connections(requester, receiver);
        
        // Remove pending requests
        requester.getSentRequests().remove(connections);
        receiver.getReceivedRequests().remove(connections);
        
        // Add user to connections
        requester.getConnections().add(receiver);
        receiver.getConnections().add(requester);
        
        accountRepository.save(requester);
        accountRepository.save(receiver);
    }
    
    public void rejectRequest(Long id) {     
        Account receiver = accountService.getCurrentUser();
        Account requester = accountRepository.getOne(id);
        
        Connections connections = new Connections(requester, receiver);
        
        // Remove pending requests
        requester.getSentRequests().remove(connections);
        receiver.getReceivedRequests().remove(connections);
        
        accountRepository.save(requester);
        accountRepository.save(receiver); 
    }
    
    public void cancelRequest(Long id) {
        Account requester = accountService.getCurrentUser();
        Account receiver = accountRepository.getOne(id);
        
        Connections connections = new Connections(requester, receiver);
        
        // Remove pending requests
        requester.getSentRequests().remove(connections);
        receiver.getReceivedRequests().remove(connections);
        
        accountRepository.save(requester);
        accountRepository.save(receiver); 
    }
    
    public List<Account> listUsers(Account searchedUser) {
        Account searched = accountRepository.findByUsername(searchedUser.getUsername());
        
        List<Account> users = new ArrayList<>();
        
        if(searched != null) {
            users.add(searched);
        } else {
            users = accountRepository.findAll();
        }
        
        return users;
    }
    
    public List<Account> listReceivedRequests(Account current) {
        List<Connections> got = current.getReceivedRequests();
        List<Account> received = new ArrayList<>();
        
        for(Connections connection: got) {
            received.add(connection.getRequester());
        }
        
        return received;
    }
    
    public List<Account> listSentRequests(Account current) {
        List<Connections> sent = current.getSentRequests();
        List<Account> requested = new ArrayList<>();
        
        for(Connections connection: sent) {
            requested.add(connection.getReceiver());
        }
        
        return requested;
    }
    
    public void requestNewConnection(Long id) {
        Account requester = accountService.getCurrentUser();
        Account receiver = accountRepository.getOne(id); 

        Connections connections = new Connections(requester, receiver);
        
        boolean pendingRequest = false;
        if(receiver.getReceivedRequests().contains(connections) || requester.getReceivedRequests().contains(connections)) {
            pendingRequest = true;
        }
        
        boolean alreadyConnected = false;
        if(receiver.getConnections().contains(requester) || requester.getConnections().contains(receiver)) {
            alreadyConnected = true;
        }
        
        if(pendingRequest == false && alreadyConnected == false) {
            receiver.getReceivedRequests().add(connections);
            requester.getSentRequests().add(connections);

            accountRepository.save(receiver);
            accountRepository.save(requester);
        }
    }
    
    public List<Account> listConnections(Account searchedUser, Account current) {
        Account searched = accountRepository.findByUsername(searchedUser.getUsername());
        
        List<Account> users = new ArrayList<>();
        
        if(current.getConnections().contains(searched)) {
            users.add(searched);
        } else {
            users = current.getConnections();
        }
        
        return users;
    }
    
    public void removeFromConnections(Long id) {
        Account current = accountService.getCurrentUser();
        Account connectedUser = accountRepository.getOne(id);
        
        current.getConnections().remove(connectedUser);
        connectedUser.getConnections().remove(current);
        
        accountRepository.save(current);
        accountRepository.save(connectedUser);
    }
}
