package com.springboot.mybatis;

import com.springboot.mybatis.controller.Spring4AllController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(Spring4AllController.class)
public class MybatisApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void selectAll() throws Exception {
		MvcResult mvcResult = this.mockMvc.perform(get("/spring/test")).andReturn();
		String content = mvcResult.getResponse().getContentAsString();
		System.out.println(content);
	}

}
