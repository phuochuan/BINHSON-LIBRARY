package com.library.binhson.userservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.library.binhson.userservice.dto.PageRequest;
import com.library.binhson.userservice.dto.UserPageDto;
import com.library.binhson.userservice.repository.UserRepository;
import com.library.binhson.userservice.rest.UserController;
import com.library.binhson.userservice.service.IUserService;
import com.library.binhson.userservice.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@WebMvcTest({UserController.class, IUserService.class, UserServiceImpl.class, UserRepository.class, ModelMapper.class})
@ExtendWith({SpringExtension.class})
public class UserTest {
    @Autowired
    MockMvc mockMvc;
    private ObjectMapper objectMapper=new ObjectMapper();
    @Test
    public void testEndpoint()throws Exception{
        PageRequest validPageRequest = PageRequest.builder()
                .page(2)
                .size(20)
                .build();

        // Perform the request and expect a 200 OK status
        MvcResult mvcResult= mockMvc.perform(MockMvcRequestBuilders.get("/public")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validPageRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String pageDto=mvcResult.getResponse().getContentAsString();
        log.info(pageDto);

    }

}
