package io.silvereyes10.akka.statemachine.actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Ping3Actor extends AbstractActor {
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(String.class, message -> {
					log.info("Ping3 received {}", message);
					work();
					getSender().tell("done", getSelf());
				})
				.build();
	}

	private void work() throws InterruptedException {
		Thread.sleep(1000);
		log.info("Ping3 working...");
	}
}
