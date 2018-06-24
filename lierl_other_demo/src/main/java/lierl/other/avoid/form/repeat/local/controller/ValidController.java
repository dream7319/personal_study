package lierl.other.avoid.form.repeat.local.controller;

import lierl.other.avoid.form.repeat.local.valid.CustomDateTime;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 参数验证
 * @author lierlei@xingyoucai.com
 * @create 2018-06-21 10:04
 **/
@RestController
@RequestMapping("/valid")
@Validated
public class ValidController {

	@GetMapping("/query")
	public String query(@NotBlank(message = "token 不能为空") @Length(message = "token 长度必须在 {min} - {max} 之间",max=10,min = 5) String token){
		return "success-"+token;
	}

	@GetMapping("/user")
	public String query(@Validated User user){
		System.out.println(user.toString());
		return "sucess";
	}

	@GetMapping("/date")
	public String validDate(@CustomDateTime(message = "您输入的格式错误，正确的格式为：{format}", format = "yyyy-MM-dd HH:mm") @NotBlank(message = "date 不能为空") String date){
		return date;
	}
}

@Data
@ToString
class User{
	private Integer id;
	@NotBlank(message = "name 不允许为空")
	@Length(min = 2, max = 10, message = "name 长度必须在 {min} - {max} 之间")
	private String name;
	@NotNull(message = "price 不允许为空")
	@DecimalMin(value = "0.1", message = "价格不能低于 {value}")
	private BigDecimal price;
}
