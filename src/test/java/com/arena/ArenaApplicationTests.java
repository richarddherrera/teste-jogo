package com.arena;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ArenaApplicationTests {

    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {
        // simply ensure Spring context starts
    }

    @Test
    void getJogadoresShouldReturnOk() throws Exception {
        mvc.perform(get("/api/jogadores"))
                .andExpect(status().isOk());
    }

    @Test
    void getTimesShouldReturnOk() throws Exception {
        mvc.perform(get("/api/times"))
                .andExpect(status().isOk());
    }
}
