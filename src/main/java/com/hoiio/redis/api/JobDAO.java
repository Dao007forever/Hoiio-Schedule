package com.hoiio.redis.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public List<String> getUsers() {
        List<String> result = new ArrayList<String>();
        List<Object> list = template.opsForList().range("users", 0, Integer.MAX_VALUE);
        for (Object user : list) {
            result.add((String) user);
        }
        return result;
    }

    public Job getJob(String user) {
        String json = (String) template.opsForList().leftPop(user);
        return deserialize(json);
    }

    public List<Job> getJobs(String user) {
        long len = template.opsForList().size(user);
        List<Object> list = template.opsForList().range(user, 0, len-1);
        List<Job> result = new ArrayList<Job>();
        for (Object job : list) {
            result.add(deserialize((String) job));
        }
        return result;
    }

    public long len(String user) {
        return template.opsForList().size(user);
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

    public Job deserialize(String json) {
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
}
