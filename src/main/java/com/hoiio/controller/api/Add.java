package com.hoiio.controller.api;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoiio.model.Job;
import com.hoiio.redis.api.JobDAO;

@Service
public class Add {
    @Autowired
    JobDAO jobDAO;
    @Autowired
    ObjectMapper mapper;

    public String add(Job job) {
        return jobDAO.addJob("test", job);
    }
}
