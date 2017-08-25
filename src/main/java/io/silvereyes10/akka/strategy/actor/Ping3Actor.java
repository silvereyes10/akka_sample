package io.silvereyes10.akka.strategy.actor;

import akka.actor.AbstractActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import scala.Option;

public class Ping3Actor extends AbstractActor {
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	public Ping3Actor() {
		log.info("Ping3Actor constructor..");
	}

	@Override
	public void postStop() throws Exception {
		log.info("Ping3Actor postStop..");
	}

	@Override
	public void preRestart(Throwable reason, Option<Object> message) throws Exception {
		log.info("Ping3Actor preRestart..");
	}

	@Override
	public void postRestart(Throwable reason) throws Exception {
		log.info("Ping3Actor postRestart..");
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
		log.info("Ping3Actor is good");
	}

	/** 일부러 NullPointerException을 발생시킨다 */
	private void badWork() {
		throw new NullPointerException();
	}
}
