package brockresource.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

import brockresource.domain.Search;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/resource")

public class ResourceController {

    @Value("${snipe.access}")
    String token;
    private static final String url = "http://rtod.library.brocku.ca:3052/api/v1/hardware?limit=500&offset=0&sort=created_at&order=desc&search=%s";

    @GetMapping("/search")
    public String view(ModelMap model) {
        model.put("searchModel", new Search());
        // model.put("searchWithMap", searchTypeMap);

        return "resourceView";
    }

    @PostMapping("/search")
    public String search(@Valid @ModelAttribute("searchModel") Search searchModel, BindingResult result,
            ModelMap model, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            model.put("searchModel", searchModel);
            return "resourceView";
        }

        Map<String, Object> data = new HashMap<>();
        data.put("searched_with", searchModel.getSearchStr());
        data.put("rows", fetchData(searchModel));

        redirectAttributes.addFlashAttribute("data", data);

        return "redirect:/resource/search";
    }

    /*
     * This method fetches data through SnipeIT API.
     */
    private Object fetchData(Search searchModel) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setBearerAuth(token);
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> reqEntity = new HttpEntity<>(httpHeaders);

        RestTemplate restTemplate = new RestTemplate();
        String responseJson = restTemplate.exchange(
                String.format(url, searchModel.getSearchStr()),
                HttpMethod.GET, reqEntity, String.class).getBody();

        return new GsonJsonParser().parseMap(responseJson).get("rows");
    }

    @SuppressWarnings("unused")
    private List<Map<String, Object>> filter(Object listObject, Search searchModel) {
        /*
         * Additional filtering could be added here, but because of how strongly typed
         * Java is,
         * working with a JSONObject and casting over and over makes the code
         * unreadable. So leaving it for now.
         */

        return new ArrayList<>();
    }
}
