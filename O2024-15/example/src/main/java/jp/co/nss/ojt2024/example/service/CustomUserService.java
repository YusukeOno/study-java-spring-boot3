package jp.co.nss.ojt2024.example.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.nss.ojt2024.example.model.CustomUser;

@Service
public class CustomUserService {

    private List<CustomUser> users;

    public CustomUserService() throws StreamReadException, DatabindException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        users = mapper.readValue(new File("user.json"),
                mapper.getTypeFactory().constructCollectionType(List.class, CustomUser.class));
    }

    public CustomUser findByUsername(String username) {
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }
}
