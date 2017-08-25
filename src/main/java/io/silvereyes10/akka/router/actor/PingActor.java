package io.silvereyes10.akka.router.actor;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.routing.RoundRobinPool;
import scala.concurrent.duration.Duration;

public class PingActor extends AbstractActor {
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);

	private ActorRef childRouter;

	public PingActor() {
		SupervisorStrategy strategy = new OneForOneStrategy(5
						, Duration.create(1, TimeUnit.MINUTES)
						, Collections.singletonList(Exception.class));

		childRouter = getContext().actorOf(
				new RoundRobinPool(5)
						.withSupervisorStrategy(strategy)
						.props(Props.create(Ping1Actor.class)
				), "ping1Actor");
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(String.class, message -> {
					for (int i = 0; i < 10; i++) {
						childRouter.tell(i, getSelf());
					}
					log.info("PingActor sent 10 messages to the router.");
				})
				.matchAny(message -> unhandled(message))
				.build();
	}
}
