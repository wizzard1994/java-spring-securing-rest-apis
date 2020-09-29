package io.jzheaux.springsecurity.resolutions;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

@Component
public class ResolutionInitializer implements SmartInitializingSingleton {
    private final ResolutionRepository resolutions;

    private final UserRepository userRepository;

    public ResolutionInitializer(ResolutionRepository resolutions, UserRepository userRepository) {
        this.resolutions = resolutions;
        this.userRepository = userRepository;
    }

    @Override
    public void afterSingletonsInstantiated() {
        this.resolutions.save(new Resolution("Read War and Peace", "user"));
        this.resolutions.save(new Resolution("Free Solo the Eiffel Tower", "user"));
        this.resolutions.save(new Resolution("Hang Christmas Lights", "user"));

        // Create user
        User admin = new User("user", "{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVGS5tHQ08W");
        admin.grantAuthority("resolution:read");
        admin.grantAuthority("resolution:write");
        admin.setEnabled(true);

        User readUser = new User("hasRead", "{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVGS5tHQ08W");
        readUser.grantAuthority("resolution:read");
        readUser.setEnabled(true);

        User writeUser = new User("hasWrite", "{bcrypt}$2a$10$MywQEqdZFNIYnx.Ro/VQ0ulanQAl34B5xVjK2I/SDZNVGS5tHQ08W");
        writeUser.grantAuthority("resolution:write");
        writeUser.setEnabled(true);

        this.userRepository.save(admin);
        this.userRepository.save(readUser);
        this.userRepository.save(writeUser);
    }
}
