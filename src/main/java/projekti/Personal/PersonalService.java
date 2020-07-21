/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.Personal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
public class PersonalService {
    @Autowired
    SkillRepository skillRepository;
    
    @Autowired
    AccountRepository accountRepository;
    
    @Autowired
    AccountService accountService;
    
    public Map<Skill, Integer> sortSkills(List<Skill> skills) {
        Map<Skill, Integer> upvoters = new HashMap<>();
        
        for(Skill skill: skills) {
            upvoters.put(skill, skill.getUpvoters().size());
        }
        
        return upvoters.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new
                ));
    }
    
    public List<Skill> mostUpvotedSkills(Map<Skill, Integer> skills) {
        List<Skill> topSkills = new ArrayList<>();
        
        if(skills.size() <= 3) {
            for(Skill skill : skills.keySet()) {
                topSkills.add(skill);
            }
        } else {
            List<Skill> allSortedSkills = new ArrayList<>();
            
            for(Skill skill : skills.keySet()) {
                allSortedSkills.add(skill);
            }
            
            for(int i = 0; i < 3; i++) {
                topSkills.add(allSortedSkills.get(i));
            }
        }
        
        return topSkills;
    }
    
    public void addNewSkill(Skill skill) {
        Account current = accountService.getCurrentUser();
        
        skill.setAccount(current);
        skillRepository.save(skill);
        
        current.getSkills().add(skill);
        accountRepository.save(current);
    }
    
    public void addNewUpvote(Long id) {
        Account upvoter = accountService.getCurrentUser();
        Skill upvoted = skillRepository.getOne(id);
        
        if(!upvoted.getUpvoters().contains(upvoter)) {
            upvoted.getUpvoters().add(upvoter);
            skillRepository.save(upvoted);
        }
    }
}
