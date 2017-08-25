package io.silvereyes10.akka.future.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.dispatch.OnComplete;
import akka.dispatch.OnFailure;
import akka.dispatch.OnSuccess;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.Function1;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.runtime.BoxedUnit;

public class NonBlockingActor extends AbstractActor {
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private ActorRef child;
	private Timeout timeout = new Timeout(Duration.create(5, "seconds"));
	private final ExecutionContext executionContext;

	public NonBlockingActor() {
		child = getContext().actorOf(Props.create(CalculationActor.class), "calculationActor");
		executionContext = getContext().system().dispatcher();
	}

	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(Integer.class, message -> {
					Future<Object> future = Patterns.ask(child, message, timeout);

					future.onSuccess(new OnSuccess<Object>() {
						@Override
						public void onSuccess(Object result) throws Throwable {
							log.info("Succeeded with " + result);
						}
					}, executionContext);

					future.onComplete(new OnComplete<Object>() {
						@Override
						public void onComplete(Throwable failure, Object success) throws Throwable {
							log.info("Completed.");
						}
					}, executionContext);

					future.onFailure(new OnFailure() {
						@Override
						public void onFailure(Throwable failure) throws Throwable {
							log.info("Failed with " + failure);
						}
					}, executionContext);
				})
				.match(String.class, message -> log.info("NonBlockingActor received a messag: " + message))
				.build();
	}
}
