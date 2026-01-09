package com.example.stats.controller;

import com.example.stats.pojo.MainItemInt;
import com.example.stats.service.StatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.Collections;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StatsController.class)
class StatsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatsService statserv;

    @Test
    void testGetMainItems() throws Exception {
        MainItemInt mockItem = new MainItemInt() {
            public String getId() { return "id"; }
            public String getName() { return "name"; }
            public String getItemname() { return "itemname"; }
            public String getItemid() { return "itemid"; }
        };
        when(statserv.getMainItems(anyList())).thenReturn(Arrays.asList(mockItem));
        mockMvc.perform(get("/mainitems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetItemsAndMainItemName() throws Exception {
        when(statserv.getItemsAndMainItemName(anyString())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/mainitemanditemname").param("itemid", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetItems() throws Exception {
        when(statserv.getItems(anyString(), anyList())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/items").param("mainitemid", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetItemsAndData() throws Exception {
        when(statserv.getItemsAndData(anyString())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/itemsanddata").param("mainitemid", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetItemsAndNamesForOneItem() throws Exception {
        when(statserv.getItemsAndNamesForOneItem(anyString(), anyList())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/oneitemsnames").param("mainitemid", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetItemsAndDataForOneItem() throws Exception {
        when(statserv.getItemsAndDataForOneItem(anyString(), anyString())).thenReturn(Collections.emptyList());
        mockMvc.perform(get("/oneitemsdata").param("mainitemid", "1").param("itemid", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetMainItemImp() throws Exception {
        mockMvc.perform(get("/mainitemsimp"))
                .andExpect(status().isOk());
    }
}
