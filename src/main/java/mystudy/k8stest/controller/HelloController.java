package mystudy.k8stest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletRequest;

@PropertySource("classpath:my.properties")
@RestController
public class HelloController {

    @Autowired
    private ServletRequest request;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${remote.api.host}")
    private String host;

    @Value("${remote.api.port}")
    private String port;

    @GetMapping("/hello")
    public String hello() {
        String msg = "hello, " + request.getLocalAddr();
        System.out.println(msg);
        return "hello";
    }

    @GetMapping("/send")
    public String send(String msg) {
        if (msg == null || "".equals(msg)) msg = "some text " + Math.random();
        String uri = host + ":" + port + "/recv?msg=" + msg;
        String result = restTemplate.getForObject(uri, String.class);
//        System.out.println(result);
        return "send";
    }

    @GetMapping("/recv")
    public String receive(String msg) {
        System.out.println("received: " + msg + ", " + request.getLocalAddr());
        return "receive";
    }
}
