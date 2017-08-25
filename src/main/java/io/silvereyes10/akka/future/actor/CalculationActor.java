package io.silvereyes10.akka.future.actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class CalculationActor extends AbstractActor {
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Integer.class, message -> {
					log.info("CalculationActor received {}", message);
					work(message);
					getSender().tell(message * 2, getSelf());
				})
				.build();
	}

	private void work(Integer message) throws InterruptedException {
		log.info("CalculationActor working on " + message);
		Thread.sleep(1000);
		log.info("CalculationActor completed " + message);
	}
}
