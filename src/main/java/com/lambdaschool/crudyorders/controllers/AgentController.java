package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Agent;
import com.lambdaschool.crudyorders.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agents")
public class AgentController {
    @Autowired
    private AgentService agentService;

    //http://localhost:2019/agents/agent/9
    @GetMapping(value = "/agent/{agentcode}", produces = "application/json")
    public ResponseEntity<?> findAgentById(@PathVariable long agentcode) {
        Agent myAgent = agentService.findAgentById(agentcode);
        return new ResponseEntity<>(myAgent, HttpStatus.OK);
    }
}
