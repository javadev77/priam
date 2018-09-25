package fr.sacem.priam.common.config;

import fr.sacem.fwk.config.Environment;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.util.Properties;

/**
 * @author <a href="mailto:xchen@palo-it.com">Xi CHEN</a>
 * @since 23/03/15.
 */
public class HomerPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();

    @Override
    protected String resolvePlaceholder(String placeholder, Properties props) {
        String propVal = Environment.getParameter(placeholder);
        if(propVal != null){
            if(propVal.matches("(\\d+(\\*?))+")){
                long value = spelExpressionParser.parseExpression(propVal).getValue(Long.class);
                return String.valueOf(value);
            }else{
                return propVal;
            }
        }else{
            return super.resolvePlaceholder(placeholder, props);
        }
    }

}
