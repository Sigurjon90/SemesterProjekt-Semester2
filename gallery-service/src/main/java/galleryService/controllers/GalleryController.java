package galleryService.controllers;

import commonService.security.JwtUtils;
import galleryService.entities.Gallery;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Api(description = "Gallery API")
@RestController
@RequestMapping("/")
public class GalleryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GalleryController.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    @Autowired
    private JwtUtils jwtUtils;

    @RequestMapping("/")
    public String home() {
        return "Galery Service running at port: " + env.getProperty("local.server.port");
    }

    @RequestMapping("/{id}")
    public Gallery getGallery(@RequestHeader HttpHeaders headers, @PathVariable final int id) {
        String token = headers.get("authorization").get(0);
        UUID userId = jwtUtils.getUserId(token);
        List<UUID> myCitizens = jwtUtils.getMyCitizens(token);
        Gallery gallery = new Gallery();
        gallery.setId(id);

        List<Object> images = restTemplate.getForObject("http://image-service/images/", List.class);
        gallery.setImages(images);

        return gallery;
    }

    // ----- Admin Area ----
    // Can only be accessed by users with role of 'admin'
    @RequestMapping("/admin")
    public String homeAdmin() {
        return "Secret admin running at port: " + env.getProperty("local.server.port");
    }
}
