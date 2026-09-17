package np.com.birajstha.oauthdemo.service;

import lombok.AllArgsConstructor;
import np.com.birajstha.oauthdemo.entity.User;
import np.com.birajstha.oauthdemo.repository.UserRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;

    public User registerOrUpdate(String provider, OidcUser oidcUser) {
        String providerSubject = oidcUser.getSubject();
        String name = oidcUser.getClaimAsString("name");
        String email = oidcUser.getClaimAsString("email");

        Optional<User> existingUser = userRepository.findByProviderAndProviderSubject(
                provider,
                providerSubject
        );

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setName(name);
            user.setEmail(email);
            return  user;
        }

        User user = new User(name, email, provider, providerSubject);
        return userRepository.save(user);
    }

    public Optional<User> findByProviderAndProviderSubject(String provider, String providerSubject) {
        return userRepository.findByProviderAndProviderSubject(provider, providerSubject);
    }
}
