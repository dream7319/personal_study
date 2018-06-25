package com.lierl.other.mock;

import com.alibaba.fastjson.JSON;
import lierl.other.controller.DemoController;
import lombok.Data;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-06-25 14:55
 **/
@RunWith(SpringRunner.class)
@WebMvcTest(DemoController.class)
public class MockTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void test() throws Exception {
		this.mockMvc.perform(get("/demo/get?name=test")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Hello")));
	}

	@Test
	public void test1() throws Exception {
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("/demo/get?name=test").accept(MediaType
				.APPLICATION_JSON))
				.andReturn();
		int status = result.getResponse().getStatus();
		Assert.assertEquals(status,200);
		String content = result.getResponse().getContentAsString();
		System.out.println(content);
	}

	@Test
	public void test2() throws Exception {
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.get("").param("page", "0")
				.param("num", "1").accept(MediaType.APPLICATION_JSON))
				.andReturn();
		int status = result.getResponse().getStatus();
		Assert.assertEquals(status,200);
		String content = result.getResponse().getContentAsString();
		System.out.println(content);
	}

	/**
	 * 此方式需要controller中方法参数前没有@RequestBody
	 */
	@Test
	public void test3() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/github/post")
				.param("codeSnippet", "package")
				.param("codeUrl", "http://localhost:8080/code")
				.param("projectUrl", "http://localhost:8080")
				.param("userName", "chhliu")
				.param("sensitiveMessage", "love")
				.param("spriderSource", "CSDN")
				.contentType(MediaType.APPLICATION_JSON_UTF8);
		MvcResult result = this.mockMvc.perform(request)
				.andReturn();
		int statusCode = result.getResponse().getStatus();
		Assert.assertEquals(statusCode, 200);
		String body = result.getResponse().getContentAsString();
		System.out.println("body:"+body);
	}

	/**
	 * attention:
	 * Details：此方式需要controller中方法参数前加@RequestBody
	 * @throws Exception
	 * void
	 */
	@Test
	public void test4() throws Exception{
		User user = new User();
		user.setId(1);
		user.setName("111");
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/github/post")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(JSON.toJSONString(user)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		int statusCode = result.getResponse().getStatus();
		Assert.assertEquals(statusCode, 200);
		String body = result.getResponse().getContentAsString();
		System.out.println("body:"+body);
	}

	@Test
	public void test5() throws Exception{
		RequestBuilder request = MockMvcRequestBuilders.put("/github/put")
				.param("id", "719")
				.param("codeSnippet", "import java.lang.exception")
				.param("codeUrl", "http://localhost:8080/code")
				.param("projectUrl", "http://localhost:8080")
				.param("userName", "xyh")
				.param("sensitiveMessage", "love")
				.param("spriderSource", "CSDN");
		MvcResult result = this.mockMvc.perform(request)
				.andReturn();
		int statusCode = result.getResponse().getStatus();
		Assert.assertEquals(statusCode, 200);
		String body = result.getResponse().getContentAsString();
		System.out.println("body:"+body);
	}

}

@Data
class User{
	private Integer id;
	private String name;
}