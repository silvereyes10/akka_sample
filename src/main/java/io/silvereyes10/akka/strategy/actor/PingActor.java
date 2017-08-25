package io.silvereyes10.akka.strategy.actor;

import org.apache.commons.lang.StringUtils;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class PingActor extends AbstractActor {
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private ActorRef child;

	public PingActor() {
		child = context().actorOf(Props.create(Ping1Actor.class), "ping1Actor");
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(String.class, message -> {
					if (StringUtils.equals(message, "good") || StringUtils.equals(message, "bad")) {
						child.tell(message, getSelf());
					} else if (StringUtils.equals(message, "done")) {
						log.info("all works are successully complete");
					}
				}).matchAny(message -> unhandled(message))
				.build();
	}
}
