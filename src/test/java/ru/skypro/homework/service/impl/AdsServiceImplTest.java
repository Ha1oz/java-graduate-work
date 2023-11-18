package ru.skypro.homework.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.http.MediaType;
import ru.skypro.homework.controller.AdsController;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.inter.AdsService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
class AdsServiceImplTest {

    private MockMvc mockMvc;

    @Mock
    private AdsService adsService;
    @InjectMocks
    private AdsController adsController;
    @Mock
    private AdsRepository adsRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AdsMapper adsMapper;
    @Mock
    private ImageService imageService;
    @Mock
    private CommentRepository commentRepository;


    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(adsController).build();
    }
    @Test
    void getAllAds() throws Exception {
        mockMvc.perform(get("/ads")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAdsMe() throws Exception {
        mockMvc.perform(get("/ads/me?email=test@example.com")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void addAd() throws Exception {
        mockMvc.perform(post("/ads/add?email=user@ya.ru")
                        .content("request body here")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getAdById() throws Exception {
        mockMvc.perform(get("/ads/123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void removeAd() throws Exception {
        mockMvc.perform(delete("/ads/123")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateAds() throws Exception {
        mockMvc.perform(put("/ads/123")
                        .content("request body here")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testUploadImage() throws Exception {
        mockMvc.perform(multipart("/ads/123/image")
                        .file("image", "testimage.jpg".getBytes())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());
    }

    @Test
    void updateAdsImage() {
    }

    @Test
    void getImage() {
    }
}