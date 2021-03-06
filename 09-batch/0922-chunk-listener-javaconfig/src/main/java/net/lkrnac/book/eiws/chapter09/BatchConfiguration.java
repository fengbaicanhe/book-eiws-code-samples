package net.lkrnac.book.eiws.chapter09;

import net.lkrnac.book.eiws.chapter09.process.SimpleRecordProcessor;
import net.lkrnac.book.eiws.chapter09.read.SimpleRecordReader;
import net.lkrnac.book.eiws.chapter09.write.SimpleRecordWriter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

  @Bean
  public Step simpleRecordsStep(StepBuilderFactory stepBuilderFactory,
      SimpleRecordReader simpleRecordReader,
      SimpleRecordProcessor simpleRecordProcessor,
      SimpleRecordWriter simpleRecordWriter,
      SimpleItemReaderListener simpleItemReaderListener,
      SimpleItemProcessListener simpleItemProcessListener,
      SimpleItemWriterListener simpleItemWriterListener,
      SimpleChunkListener simpleChunkListener) {
    return stepBuilderFactory.get("simpleRecordsStep")
        .<String, String> chunk(4)
        .reader(simpleRecordReader)
        .listener(simpleItemReaderListener)
        .processor(simpleRecordProcessor)
        .listener(simpleItemProcessListener)
        .writer(simpleRecordWriter)
        .listener(simpleItemWriterListener)
        .listener(simpleChunkListener)
        .build();
  }

  @Bean
  public Job simpleRecordsJob(JobBuilderFactory jobBuilderFactory,
      Step simpleRecordsStep) {
    return jobBuilderFactory.get("simpleRecordsJob")
        .start(simpleRecordsStep)
        .build();
  }
}
