package com.library.binhson.userservice;

import com.library.binhson.userservice.repository.UserRepository;
import com.library.binhson.userservice.service.IMyProfileService;
import com.library.binhson.userservice.service.impl.MyProfileServiceImpl;
import com.library.binhson.userservice.service.third_party_system.keycloak.KeycloakService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Slf4j
@WebMvcTest({ UserRepository.class,  IMyProfileService.class, MyProfileServiceImpl.class,  ModelMapper.class, KeycloakService.class})
@ExtendWith({SpringExtension.class})
public class MyProfileTest {
    @Autowired
    MockMvc mockMvc;
    @Test
    @WithAnonymousUser
    public void testEndpoint()throws Exception{

        // Perform the request and expect a 200 OK status
        MvcResult mvcResult= mockMvc.perform(MockMvcRequestBuilders.get("/replace-email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"new_email\": \"phuochuan@798987@gmail.com\",\n" +
                                "    \"password\":\"password\"\n" +
                                "}").contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String pageDto=mvcResult.getResponse().getContentAsString();
        log.info(pageDto);

    }

}
