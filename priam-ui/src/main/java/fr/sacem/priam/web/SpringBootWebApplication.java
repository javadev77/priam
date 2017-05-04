package fr.sacem.priam.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Created by benmerzoukah on 18/04/2017.
 */
@SpringBootApplication
public class SpringBootWebApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootWebApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootWebApplication.class, args);
    }
/*
  @Bean
  public SessionFactory<ChannelSftp.LsEntry> sftpSessionFactory() {
    DefaultSftpSessionFactory factory = new DefaultSftpSessionFactory(true);
    factory.setHost("dettome");
    factory.setPort(22);
    factory.setUser("priam");
    factory.setPassword("priam001");
    factory.setAllowUnknownKeys(true);

    return new CachingSessionFactory<>(factory);
  }

  @Bean
  public SftpInboundFileSynchronizer sftpInboundFileSynchronizer() {
    SftpInboundFileSynchronizer fileSynchronizer = new SftpInboundFileSynchronizer(sftpSessionFactory());
    fileSynchronizer.setDeleteRemoteFiles(true);
    fileSynchronizer.setRemoteDirectory("/work/DomainApp/priam/HTDOCS/ftp");
    fileSynchronizer.setFilter(new SftpSimplePatternFileListFilter("*.zip"));

    return fileSynchronizer;
  }

  @Bean
  @InboundChannelAdapter(channel = "sftpChannel", poller = @Poller(fixedDelay = "60000"))
  public MessageSource<File> sftpMessageSource() {
    SftpInboundFileSynchronizingMessageSource source = new SftpInboundFileSynchronizingMessageSource(
      sftpInboundFileSynchronizer());
    source.setLocalDirectory(new File("/export/priam/ftp-inbound"));
    source.setAutoCreateLocalDirectory(true);
    source.setLocalFilter(new AcceptOnceFileListFilter<>());

    return source;
  }

  @Bean
  @ServiceActivator(inputChannel = "sftpChannel")
  public MessageHandler handler() {
    return message -> System.out.println(message.getPayload());
  }
*/
}
