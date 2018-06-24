package lierl.other.controller;

import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-06-04 13:46
 **/
@Controller
@RequestMapping("/demo")
public class DemoController {

	@GetMapping("/get")
	public String getMapping(String name){
		Assert.notNull(name,"姓名不能为空");
		throw new RuntimeException("throw exception");
	}

	@GetMapping("/index")
	public ModelAndView index() {
		ModelAndView view = new ModelAndView();
		// 设置跳转的视图 默认映射到 src/main/resources/templates/{viewName}.html
		view.setViewName("index");
		// 设置属性
		view.addObject("title", "我的第一个WEB页面");
		view.addObject("desc", "欢迎进入battcn-web 系统");
		Author author = new Author();
		author.setAge(22);
		author.setEmail("1837307557@qq.com");
		author.setName("唐亚峰");
		view.addObject("author", author);
		return view;
	}

	@GetMapping("/index1")
	public String index1(HttpServletRequest request) {
		// TODO 与上面的写法不同，但是结果一致。
		// 设置属性
		request.setAttribute("title", "我的第一个WEB页面");
		request.setAttribute("desc", "欢迎进入battcn-web 系统");
		Author author = new Author();
		author.setAge(22);
		author.setEmail("1837307557@qq.com");
		author.setName("唐亚峰");
		request.setAttribute("author", author);
		// 返回的 index 默认映射到 src/main/resources/templates/xxxx.html
		return "index";
	}

	@GetMapping("/index2")
	public String index2(Model model){
		model.addAttribute("title","hello world");
		return "index";
	}

	@Data
	class Author {
		private int age;
		private String name;
		private String email;
		// 省略 get set
	}
}
