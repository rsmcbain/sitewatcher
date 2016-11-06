package com.mcbain.sitewatcher.rest;
import static org.hamcrest.CoreMatchers.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.MockitoAnnotations.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mcbain.sitewatcher.domain.Probe;
import com.mcbain.sitewatcher.service.ProbeService;

@RunWith(SpringRunner.class)
public class ProbeControllerTest {
	@InjectMocks
	private ProbeController controller;
	
	@SuppressWarnings("deprecation")
	@Mock
	private ProbeService service;
	
	private MockMvc mockMvc;
	Probe probe;
	ObjectMapper objectMapper = new ObjectMapper();
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
	}
	@Test
	public void testExistingProbe() throws Exception {
		probe = new Probe("1","Test Name","www.my.com");
		
		when(service.findOne("1")).thenReturn(probe);
		
		mockMvc.perform(get("/v1/probes/1"))
				.andExpect(jsonPath("$.name",is(probe.getName())))
				.andExpect(jsonPath("$.url",is(probe.getUrl()) ))
				.andExpect(status().isOk())
				;
	}
	@Test
	public void testNonExistingProbe() throws Exception {
		when(service.findOne("0")).thenReturn(null);
		
		mockMvc.perform(get("/v1/probes/0"))
				.andExpect(status().isNotFound())
				;
	}
	@Test
	public void testCreateDuplicateReturnsConflictError() throws Exception {
		probe = new Probe("1","Test Name","www.my.com");
		System.out.println(objectMapper.writeValueAsString(probe));
		when(service.findOne("1")).thenReturn(probe);
		
		mockMvc.perform(post("/v1/probes")
					.content(objectMapper.writeValueAsString(probe))
					.contentType(contentType)
				)
				.andExpect(status().isConflict())
				;
	}
}
