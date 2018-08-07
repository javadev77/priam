package fr.sacem.priam.rest.common.config;

import fr.sacem.priam.security.config.SsoAuthenticationToken;
import fr.sacem.priam.security.model.UserDTO;
import org.springframework.core.MethodParameter;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Created by benmerzoukah on 13/07/2018.
 */
public class UserDTOHandlerMethodArgumentResolverTest implements org.springframework.web.method.support.HandlerMethodArgumentResolver {

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
        if(authentication instanceof TestingAuthenticationToken){
            userDTO = (UserDTO) authentication.getPrincipal();
        }
        return userDTO == null ? UserDTO.GUEST : userDTO;
    }
}
