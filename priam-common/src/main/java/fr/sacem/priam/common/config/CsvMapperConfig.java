package fr.sacem.priam.common.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import fr.sacem.priam.common.util.csv.NumberSerializer;
import fr.sacem.priam.common.util.csv.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CsvMapperConfig {

    @Bean
    public CsvMapper csvMapper(){
        CsvMapper mapper = new CsvMapper();
        mapper.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
        
        SimpleModule module = new SimpleModule();
        module.addSerializer(Double.class, new NumberSerializer());
        module.addSerializer(Float.class, new NumberSerializer());
        module.addSerializer(String.class, new StringSerializer());
        mapper.registerModule(module);
        
        return mapper;
    }
    

}
