package pl.akademiakodu.service;

import pl.akademiakodu.model.EmailToken;

public interface EmailTokenService {
    public EmailToken generateToken(Integer userId, String useFor);
    public EmailToken findByToken(String token);
    public void tokenDelete(EmailToken emailToken);
    public EmailToken findByUserId(Integer userId);

}
