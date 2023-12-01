package com.coinlibrary.backend.controller;

import com.coinlibrary.backend.model.Coin;
import com.coinlibrary.backend.model.Edition;
import com.coinlibrary.backend.model.Librarian;
import com.coinlibrary.backend.repository.CoinRepository;
import com.coinlibrary.backend.repository.LibrarianRepository;
import com.coinlibrary.backend.service.CoinService;
import com.coinlibrary.backend.service.LibrarianService;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@WebMvcTest(CoinController.class)
@AutoConfigureMockMvc(addFilters = false)
class CoinControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CoinRepository<Coin, Long> coinRepository;
    @MockBean
    private LibrarianRepository<Librarian, Long> librarianRepository;
    @MockBean
    private CoinService coinService;
    @SpyBean
    private LibrarianService librarianService;

    @org.junit.jupiter.api.Test
    void getCoins() throws Exception {

        Edition edition = new Edition();
        edition.setEdition(1);
        edition.setCountry("de");
        edition.setYearFrom(1999);
        edition.setYearTo(2006);
        edition.setId(1);

        Coin coin1 = new Coin();
        coin1.setEdition(edition);
        coin1.setId(1L);
        coin1.setSize(1);

        Coin coin2 = new Coin();
        coin2.setEdition(edition);
        coin2.setId(2L);
        coin2.setSize(2);

        Librarian testUser = new Librarian();
        testUser.setId(1L);
        testUser.setUuid(UUID.randomUUID());
        testUser.setLibrarianEmail("testUser@testuser.de");


        when(coinRepository.findAll((Specification<Coin>) ArgumentMatchers.any())).thenReturn(List.of(coin1, coin2));
        when(coinRepository.findCoinsByLibrarians(testUser)).thenReturn(List.of(coin1));
        when(librarianRepository.findByLibrarianEmail("testUser@testuser.de")).thenReturn(Optional.of(testUser));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/coin")
                        .param("editionId", String.valueOf(1))
                        .param("librarianIdentification", "testUser@testuser.de"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        assertThat(jsonResponse).contains("\"available\":true").hasLineCount(1);
    }
}