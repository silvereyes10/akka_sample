package io.silvereyes10.akka.strategy.actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Ping2Actor extends AbstractActor {
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	public Ping2Actor() {
		log.info("Ping2Actor constructor..");
	}

	@Override
	public void preRestart(Throwable reason, scala.Option<Object> message) {
		log.info("Ping2Actor preRestart..");
	}

	@Override
	public void postStop() throws Exception {
		log.info("Ping2Actor postStop..");
	}

	@Override
	public void postRestart(Throwable reason) throws Exception {
		log.info("Ping2Actor postRestart..");
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.matchEquals("good", message -> {
					goodWork();
					getSender().tell("done", getSelf());
				})
				.matchEquals("bad", message -> badWork())
				.matchAny(message -> unhandled(message))
				.build();
	}

	private void goodWork() {
		log.info("Ping2Actor is good.");
	}

	/** 일부러 ArithmeticException을 발생시킨다	*/
	private void badWork() {
		int a = 1 / 0;
	}

}
