package fr.sacem.priam.rest.api.common;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by benmerzoukah on 13/07/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PriamRestApiCommonWebAppTest.class)
@WebAppConfiguration
@ActiveProfiles("test")
public abstract class RestResourceTest {
  protected MockMvc mockMvc;
  private HttpMessageConverter mappingJackson2HttpMessageConverter;
  protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
                                                 MediaType.APPLICATION_JSON.getSubtype(),
                                                 Charset.forName("utf8"));
  @Autowired
  private WebApplicationContext webApplicationContext;

  @Autowired
  public void setConverters(HttpMessageConverter<?>[] converters) {

    this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                                                 .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                                                 .findAny()
                                                 .orElse(null);

    assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
  }

  @Before
  public void setup() throws Exception {
      this.mockMvc = webAppContextSetup(webApplicationContext).build();
  }

  protected String json(Object o) throws IOException {
    MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
    this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);

    return mockHttpOutputMessage.getBodyAsString();
  }


}
