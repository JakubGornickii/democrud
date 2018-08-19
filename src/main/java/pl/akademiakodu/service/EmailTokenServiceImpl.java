package pl.akademiakodu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.akademiakodu.model.EmailToken;
import pl.akademiakodu.repository.EmailTokenRepository;

import java.util.Random;
@Component
public class EmailTokenServiceImpl implements EmailTokenService{

    @Autowired
    EmailTokenRepository emailTokenRepository;

    @Override
    public EmailToken generateToken(Integer userId, String useFor) {

        int leftLimit = 97;
        int rightLimit = 122;
        int targetStringLength = 100;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedToken = buffer.toString();
EmailToken emailToken = new EmailToken(generatedToken,userId,true,useFor);
emailTokenRepository.save(emailToken);
        System.out.println(generatedToken);

        return emailToken;
    }

    @Override
    public EmailToken findByToken(String token) {
        return emailTokenRepository.findByToken(token);
    }

    @Override
    public void tokenDelete(EmailToken emailToken) {
        emailTokenRepository.delete(emailToken);
    }

    @Override
    public EmailToken findByUserId(Integer userId) {

        return emailTokenRepository.findByUserId(userId);
    }


}

