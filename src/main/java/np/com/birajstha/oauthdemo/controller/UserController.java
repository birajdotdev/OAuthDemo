package np.com.birajstha.oauthdemo.controller;

import lombok.AllArgsConstructor;
import np.com.birajstha.oauthdemo.entity.User;
import np.com.birajstha.oauthdemo.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
public class UserController {
    private UserService userService;

    @GetMapping("/")
    public String home(){
        return """
                Public Home
                
                Login using: http://localhost:8080/oauth2/authorization/google"
                """;
    }

    @GetMapping("/profile")
    public Map<String, Object> profile(@AuthenticationPrincipal OidcUser oidcUser){
        User user = userService.findByProviderAndProviderSubject("google", oidcUser.getSubject())
                .orElseThrow();

        Map<String,Object> response = new HashMap<>();

        response.put("internalUserId",user.getId());
        response.put("provider",user.getProvider());
        response.put("subject",user.getProviderSubject());
        response.put("name",user.getName());
        response.put("email",user.getEmail());

        return response;
    }
}
