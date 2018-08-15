package pl.akademiakodu.other;

import org.springframework.web.servlet.ModelAndView;

public interface UserData {
   public ModelAndView getUserData();
   public Integer getUserId();
   public String getUserRole();

}

