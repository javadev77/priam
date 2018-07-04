//package fr.sacem.priam.services.batch;
//
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.item.*;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.Resource;
//
//import javax.transaction.Transaction;
//
///**
// * Created by benmerzoukah on 12/06/2018.
// */
//public class SpringBatchConfig {
//    @Autowired
//    private JobBuilderFactory jobs;
//
//    @Autowired
//    private StepBuilderFactory steps;
//
//    @Value("input/record.csv")
//    private Resource inputCsv;
//
//    @Value("file:xml/output.xml")
//    private Resource outputXml;
//
//    @Bean
//    public ItemReader<Transaction> itemReader()
//            throws UnexpectedInputException, ParseException {
//        FlatFileItemReader<Transaction> reader = new FlatFileItemReader<>();
//        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
//        String[] tokens = { "username", "userid", "transactiondate", "amount" };
//        tokenizer.setNames(tokens);
//        reader.setResource(inputCsv);
//        DefaultLineMapper<Transaction> lineMapper =
//                new DefaultLineMapper<Transaction>();
//        lineMapper.setLineTokenizer(tokenizer);
//        lineMapper.setFieldSetMapper(new RecordFieldSetMapper());
//        reader.setLineMapper(lineMapper);
//        return reader;
//    }
//
//    @Bean
//    public ItemProcessor<Transaction, Transaction> itemProcessor() {
//        return new CustomItemProcessor();
//    }
//
//    @Bean
//    public ItemWriter<Transaction> itemWriter(Marshaller marshaller)
//            throws MalformedURLException {
//        StaxEventItemWriter<Transaction> itemWriter =
//                new StaxEventItemWriter<Transaction>();
//        itemWriter.setMarshaller(marshaller);
//        itemWriter.setRootTagName("transactionRecord");
//        itemWriter.setResource(outputXml);
//        return itemWriter;
//    }
//
//    @Bean
//    public Marshaller marshaller() {
//        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//        marshaller.setClassesToBeBound(new Class[] { Transaction.class });
//        return marshaller;
//    }
//
//    @Bean
//    protected Step step1(ItemReader<Transaction> reader,
//                         ItemProcessor<Transaction, Transaction> processor,
//                         ItemWriter<Transaction> writer) {
//        return steps.get("step1").<Transaction, Transaction> chunk(100)
//                .reader(reader).processor(processor).writer(writer).build();
//    }
//
//    @Bean(name = "firstBatchJob")
//    public Job job(@Qualifier("step1") Step step1) {
//        return jobs.get("firstBatchJob").start(step1).build();
//    }
//
//}
