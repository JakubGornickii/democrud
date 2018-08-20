package pl.akademiakodu.service;

import org.springframework.stereotype.Component;

@Component
public class EmailGeneratorServiceImpl implements EmailGeneratorService {

    @Override
    public String getEmailRegistation(String token) {
        return "W celu dokończenia rejestracji kliknij poniższy link https://young-bastion-29840.herokuapp.com/registration/confirm/"+token;
    }

    @Override
    public String getEmailChangePass(String token) {
        return "W celu zatwierdzenia nowego hasła https://young-bastion-29840.herokuapp.com/password/change/"+token;
    }

    @Override
    public String getEmailChangeEmail(String token) {
        return "W celu zmiany emaila kliknij poniższy link https://young-bastion-29840.herokuapp.com/email/change/"+token;
    }

    @Override
    public String getNewEmailRegistration(String token) {
        return "Oto nowy link do aktywacji konta https://young-bastion-29840.herokuapp.com/registration/confirm/"+token;
    }

    @Override
    public String getEmailPassworldRecover(String token) {
        return "Link do nadania nowego hasła  https://young-bastion-29840.herokuapp.com/password/recover/"+token;
    }
}
