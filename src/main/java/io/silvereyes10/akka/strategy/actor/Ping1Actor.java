package io.silvereyes10.akka.strategy.actor;

import org.apache.commons.lang.StringUtils;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.Function;
import scala.concurrent.duration.Duration;

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
		return receiveBuilder()
				.match(String.class, message -> {
					if (StringUtils.equals(message, "good") || StringUtils.equals(message, "bad")) {
						log.info("Ping1Actor received {}", message);
						child1.tell(message, getSender());
						child2.tell(message, getSender());
					}
				})
				.matchAny(message -> unhandled(message))
				.build();
	}

	private static SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create("1 minute"), new Function<Throwable, SupervisorStrategy.Directive>() {
		@Override
		public SupervisorStrategy.Directive apply(Throwable param) throws Exception {
			if (param instanceof ArithmeticException) {
				// Ping2Actor 는 "bad" 메시지를 받으면 ArithmeticException 발생.
				return SupervisorStrategy.resume();
			} else if (param instanceof NullPointerException) {
				// Ping3Actor 는 "bad" 메시지를 받으면 NullPointerException 발생.
				return SupervisorStrategy.restart();
			} else if (param instanceof IllegalArgumentException) {
				return SupervisorStrategy.stop();
			} else {
				return SupervisorStrategy.escalate();
			}
		}
	});

	@Override
	public SupervisorStrategy supervisorStrategy() {
		return strategy;
	}
}
