package com.hoiio.redis.api;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.hoiio.model.Job;

@Repository
public class JobDAO {
    @Autowired
    RedisTemplate<String, Object> template;
    @Autowired
    ObjectMapper mapper;

    public Job getJob(String user) {
        String json = (String) template.opsForList().leftPop(user);
        Job job = null;
        try {
            job = mapper.readValue(json, Job.class);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return job;
    }

    public String addJob(String user, Job job) {
        try {
            String json = mapper.writeValueAsString(job) ;
            template.opsForList().rightPush(user, json);
            return "Done";
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
