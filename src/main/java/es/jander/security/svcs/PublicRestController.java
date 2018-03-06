package es.jander.security.svcs;

import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PublicRestController
{
    @GetMapping("public")
    public Pair<String,String> publicServiceRequest()
    {
        return Pair.of("serviceName", "public service");
    }

    @GetMapping("private")
    public Pair<String,String> privateServiceRequest()
    {
        return Pair.of("serviceName", "private service");
    }
}
