package com.example.stats.controller;

import com.example.stats.entity.Items;
import com.example.stats.entity.ItemsData;
import com.example.stats.entity.MainItem;
import com.example.stats.repository.ItemsDataRepository;
import com.example.stats.repository.ItemsRepository;
import com.example.stats.repository.MainItemRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.UUID;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StatsControllerIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MainItemRepository mainItemRepository;
    @Autowired
    private ItemsRepository itemsRepository;
    @Autowired
    private ItemsDataRepository itemsDataRepository;

    private MainItem dbMainItem;
    private Items dbItem;
    private ItemsData dbItemsData;
    private Long dbMainItemId;
    private Long dbItemId;
    private Long dbItemsDataId;
    private String dbMainItemName;
    private String dbItemName;
    private String dbItemsDataValue;
    private final String uniqueSuffix = "integration-" + UUID.randomUUID();

    @BeforeAll
    void setup() {
        dbMainItem = mainItemRepository.findAll().iterator().hasNext() ? mainItemRepository.findAll().iterator().next() : null;
        if (dbMainItem == null) throw new IllegalStateException("No MainItem found in DB. Please insert at least one MainItem record.");
        dbMainItemId = dbMainItem.getid();
        dbMainItemName = dbMainItem.getName();
        dbItem = itemsRepository.findAll().iterator().hasNext() ? itemsRepository.findAll().iterator().next() : null;
        if (dbItem == null) throw new IllegalStateException("No Items found in DB. Please insert at least one Items record.");
        dbItemId = dbItem.getid();
        dbItemName = dbItem.getName();
        dbItemsData = itemsDataRepository.findAll().iterator().hasNext() ? itemsDataRepository.findAll().iterator().next() : null;
        if (dbItemsData == null) throw new IllegalStateException("No ItemsData found in DB. Please insert at least one ItemsData record.");
        dbItemsDataId = dbItemsData.getId();
        dbItemsDataValue = dbItemsData.getData();
    }

    @Test
    void testGetMainItemsIntegration() throws Exception {
        mockMvc.perform(get("/mainitems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[?(@.name == '" + dbMainItemName + "')]").exists());
    }

    @Test
    void testGetItemsIntegration() throws Exception {
        mockMvc.perform(get("/items").param("mainitemid", String.valueOf(dbMainItemId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[?(@.name == '" + dbItemName + "')]").exists());
    }

    @Test
    void testGetItemsAndDataIntegration() throws Exception {
        mockMvc.perform(get("/itemsanddata").param("mainitemid", String.valueOf(dbMainItemId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetMainItemAndItemNameIntegration() throws Exception {
        mockMvc.perform(get("/mainitemanditemname").param("itemid", String.valueOf(dbItemId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetItemsAndNamesForOneItemIntegration() throws Exception {
        mockMvc.perform(get("/oneitemsnames").param("mainitemid", String.valueOf(dbMainItemId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGetItemsAndDataForOneItemIntegration() throws Exception {
        mockMvc.perform(get("/oneitemsdata")
                .param("mainitemid", String.valueOf(dbMainItemId))
                .param("itemid", String.valueOf(dbItemId)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}