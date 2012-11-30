package com.hoiio.controller.api;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hoiio.model.Job;
import com.hoiio.redis.api.JobDAO;

@Service
public class Get {
    @Autowired
    JobDAO jobDAO;
    @Autowired
    ObjectMapper mapper;

    public String get() {
        Job job = jobDAO.getJob("test");

        try {
            return mapper.writeValueAsString(job) ;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Failed";
    }
}
