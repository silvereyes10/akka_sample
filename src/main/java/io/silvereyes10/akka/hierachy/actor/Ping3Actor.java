package io.silvereyes10.akka.hierachy.actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Ping3Actor extends AbstractActor {
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	@Override
	public AbstractActor.Receive createReceive() {
		return receiveBuilder()
				.matchEquals("work", message -> {
					log.info("Ping3 received {}", message);
					work();
					getSender().tell("done", getSelf());
				}).build();
	}

	private void work() throws InterruptedException {
		Thread.sleep(1000);
		log.info("Ping3 working...");
	}
}
