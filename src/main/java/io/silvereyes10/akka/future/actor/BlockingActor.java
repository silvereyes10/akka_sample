package io.silvereyes10.akka.future.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class BlockingActor extends AbstractActor {
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private ActorRef child;
	private Timeout timeout = new Timeout(Duration.create(5, "seconds"));

	public BlockingActor() {
		child = getContext().actorOf(Props.create(CalculationActor.class), "calculationActor");
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Integer.class, message -> {
					Future<Object> future = Patterns.ask(child, message, timeout);
					Integer result = (Integer) Await.result(future, timeout.duration());
					log.info("Final result is " + (result + 1));
				})
				.match(String.class, message -> {
					log.info("BlockingActor received a message: " + message);
				})
				.build();
	}
}
