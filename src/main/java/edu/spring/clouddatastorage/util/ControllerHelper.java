package edu.spring.clouddatastorage.util;

import edu.spring.clouddatastorage.dto.UserDtoResponse;
import edu.spring.clouddatastorage.security.UserDetailsDto;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ControllerHelper {

    public UserDtoResponse takeUserDtoFromSecurity() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
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
        var sb = new StringBuilder();
        for (int i = 0; i < pathElements.length; i++) {
            sb.append(pathElements[i]);
            pathElementsList.add(sb.toString());
            if (i != pathElements.length - 1)
                sb.append("/");
        }
        return pathElementsList;
    }

    public String generateRedirectUrl(String path) {
        if (path != null && !path.isEmpty())
            return "redirect:/?path=" + path;
        return "redirect:/";
    }
}
