package io.silvereyes10.akka.hierachy.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Ping1Actor extends AbstractActor {
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private ActorRef child1;
	private ActorRef child2;

	public Ping1Actor() {
		child1 = context().actorOf(Props.create(Ping2Actor.class), "ping2Actor");
		child2 = context().actorOf(Props.create(Ping3Actor.class), "ping3Actor");
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().matchEquals("work", message -> {
			log.info("Ping1 received {}", message);
			child1.tell("work", getSender());
			child2.tell("work", getSender());
		}).build();
	}
}
