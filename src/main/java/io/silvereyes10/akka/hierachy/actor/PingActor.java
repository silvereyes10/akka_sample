package io.silvereyes10.akka.hierachy.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class PingActor extends AbstractActor {
	private LoggingAdapter log = Logging.getLogger(getContext().getSystem(), this);
	private ActorRef child;
	private int count = 0;

	public PingActor() {
		this.child = context().actorOf(Props.create(Ping1Actor.class), "ping1Actor");
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.matchEquals("work", message -> child.tell(message, getSelf()))
				.matchEquals("done", message -> {
					if (count == 0) {
						count++;
					} else {
						log.info("all works are completed.");
						count = 0;
					}
				}).build();
	}
}
