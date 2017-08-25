package io.silvereyes10.akka.statemachine.actor;

import akka.actor.AbstractActorWithStash;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.FI;

public class PingActor extends AbstractActorWithStash {
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private ActorRef child;

	public PingActor() {
		child = context().actorOf(Props.create(Ping1Actor.class), "ping1Actor");
		getContext().become(initial);
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder().build();
	}

	private Receive initial = receiveBuilder()
			.matchEquals("work", new FI.UnitApply<String>() {
				@Override
				public void apply(String message) throws Exception {
					child.tell(message, getSelf());
					getContext().become(zeroDone);
				}
			})
			.matchAny(message -> stash())
			.build();

	private Receive zeroDone = receiveBuilder()
			.matchEquals("done", new FI.UnitApply<String>() {
				@Override
				public void apply(String message) throws Exception {
					log.info("Received the first done");
					getContext().become(oneDone);
				}
			})
			.matchAny(message -> stash())
			.build();

	private Receive oneDone = receiveBuilder()
			.matchEquals("done", new FI.UnitApply<String>() {
				@Override
				public void apply(String message) throws Exception {
					log.info("Received the second done");
					unstashAll();
					getContext().become(allDone);
				}
			})
			.matchAny(message -> stash())
			.build();

	private Receive allDone = receiveBuilder()
			.matchEquals("reset", new FI.UnitApply<String>() {
				@Override
				public void apply(String message) throws Exception {
					log.info("Received a reset");
					getContext().become(initial);
				}
			})
			.build();
}
