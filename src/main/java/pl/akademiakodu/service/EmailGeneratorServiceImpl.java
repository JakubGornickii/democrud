package pl.akademiakodu.service;

import org.springframework.stereotype.Component;

@Component
public class EmailGeneratorServiceImpl implements EmailGeneratorService {

    @Override
    public String getEmailRegistation(String token) {
        return "W celu dokończenia rejestracji kliknij poniższy link http://localhost:8084/registration/confirm/"+token;
    }

    @Override
    public String getEmailChangePass(String token) {
        return "W celu zmiany hasła kliknij poniższy link http://localhost:8084/passworld/change/"+token;
    }

    @Override
    public String getEmailChangeEmail(String token) {
        return "W celu zmiany emaila kliknij poniższy link http://localhost:8084/email/change/"+token;
    }
}
