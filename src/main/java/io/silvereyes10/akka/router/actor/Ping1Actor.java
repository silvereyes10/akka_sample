package io.silvereyes10.akka.router.actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Ping1Actor extends AbstractActor {
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Integer.class, message -> {
					log.info("Ping1Actor({}) received {}", hashCode(), message);
					work(message);
				})
				.build();
	}

	private void work(Integer message) throws InterruptedException {
		log.info("Ping1Actor({}) working on {}", hashCode(), message);
		Thread.sleep(1000);
		log.info("Ping1Actor({}) completed", hashCode());
	}
}
