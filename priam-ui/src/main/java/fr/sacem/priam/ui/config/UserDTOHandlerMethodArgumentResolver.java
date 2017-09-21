package fr.sacem.priam.ui.config;

import fr.sacem.priam.ui.security.SsoAuthenticationToken;
import fr.sacem.priam.ui.rest.dto.UserDTO;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by benmerzoukah on 08/09/2017.
 */
public class UserDTOHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return UserDTO.class.equals(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
      UserDTO userDTO = null;
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      if(authentication instanceof SsoAuthenticationToken){
        userDTO = ((SsoAuthenticationToken) authentication).getUser();
      }
      return userDTO == null ? UserDTO.GUEST : userDTO;
    }
}
