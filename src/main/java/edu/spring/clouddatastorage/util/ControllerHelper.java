package edu.spring.clouddatastorage.util;

import edu.spring.clouddatastorage.dto.UserDtoResponse;
import edu.spring.clouddatastorage.security.UserDetailsDto;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.yaml.snakeyaml.util.UriEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@UtilityClass
public class ControllerHelper {

    public UserDtoResponse getUSerDtoFromAuthentication(Authentication authentication) {
        var userDetails = (UserDetailsDto) authentication.getPrincipal();
        return UserDtoResponse.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .build();
    }

    public List<String> parsePathForNavigation(String path) {
        var pathElements = path.split("/");
        if (pathElements.length == 1)
            return List.of(pathElements[0]);
        List<String> pathElementsList = new ArrayList<>();
        var sj = new StringJoiner("/");
        for (String pathElement : pathElements) {
            sj.add(pathElement);
            pathElementsList.add(sj.toString());
        }
        return pathElementsList;
    }

    public String generateRedirectUrl(String path) {
        if (path != null && !path.isEmpty()) {
            path = UriEncoder.encode(path);
            return "redirect:/?path=" + path;
        }
        return "redirect:/";
    }
}
