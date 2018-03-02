package es.jander.security.svcs;

import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("public")
public class PublicRestController
{
    @GetMapping
    public Pair<String,String> returnServiceName()
    {
        return Pair.of("serviceName", PublicRestController.class.getSimpleName());
    }
}
