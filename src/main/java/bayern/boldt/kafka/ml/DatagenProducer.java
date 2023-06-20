package bayern.boldt.kafka.ml;

import org.jboss.logging.Logger;
import io.quarkus.arc.log.LoggerName;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.reactive.messaging.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import bayern.boldt.kafka.ml.avro.*;

import static java.lang.Math.floor;
import static java.lang.Math.toIntExact;

@ApplicationScoped
public class DatagenProducer {


  @LoggerName("DatagenProducer")
  Logger log;

  @Inject
  @Channel("ts-out")
  @OnOverflow(value = OnOverflow.Strategy.NONE) // let Kafka Producer handle back pressure https://smallrye.io/smallrye-reactive-messaging/4.6.0/concepts/emitter/#overflow-management
  Emitter<TSDataPoint> emitter;

  long f(int x, int amplitude) {
    return (long) floor(amplitude  * Math.sin(x));
  }

  void onStart(@Observes StartupEvent ev) {
    int x = 0;
    while (true) {

      TSDataPoint m = new TSDataPoint();
      m.setValue( toIntExact(f(x, 20)));
      x++;

      sendTsDataPoint(m);
    }
  }

  public void sendTsDataPoint(TSDataPoint m) {
    try {
      emitter.send(m);
    } finally {
      log.info("Sent " + m.getValue());
    }
  }
}
