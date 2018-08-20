package pl.akademiakodu.service;

public interface EmailGeneratorService {
    public String getEmailRegistation(String token);
    public String getEmailChangePass(String token);
    public String getEmailChangeEmail(String token);
    public String getNewEmailRegistration(String token);
}
