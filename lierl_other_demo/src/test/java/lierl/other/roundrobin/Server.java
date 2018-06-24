package lierl.other.roundrobin;

import lombok.Data;
import lombok.ToString;

/**
 * @author lierlei@xingyoucai.com
 * @create 2018-06-07 11:16
 **/
@Data
@ToString
public class Server {
	private String ip;
	private int weight;

	public Server(String ip) {
		this.ip = ip;
	}
}
