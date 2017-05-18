package fr.sacem.priam.ui;


import fr.sacem.priam.model.config.JpaConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;

/**
 * Created by benmerzoukah on 18/04/2017.
 */
@Import(JpaConfiguration.class)
@SpringBootApplication(scanBasePackages = {"fr.sacem.priam"})
@EnableCaching
public class PriamWebApp extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PriamWebApp.class);
    }

    /*
    public static void main(String[] args) throws Exception {
        SpringApplication.run(PriamWebApp.class, args);
    }
    */
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
