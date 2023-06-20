package bayern.boldt.kafka.ml;

import io.quarkus.test.junit.QuarkusTest;

import org.junit.jupiter.api.Test;

import jakarta.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class DatagenProducerTest {

  @Inject
  DatagenProducer application;

  @Test
  void test() {

    assertEquals(1,1);

  }
}
