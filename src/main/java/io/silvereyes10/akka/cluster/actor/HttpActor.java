package io.silvereyes10.akka.cluster.actor;

import akka.actor.ActorRef;
import akka.camel.CamelMessage;
import akka.camel.javaapi.UntypedConsumerActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class HttpActor extends UntypedConsumerActor {
	private LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	private String uri;
	private ActorRef service;
	private ActorRef sender;

	public HttpActor(ActorRef service) {
		this.service = service;
		this.uri = "jetty:http://localhost:8877/akkaStudy";
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof CamelMessage) {
			this.sender = getSender();
			service.tell("Hello", getSelf());
		} else if (message instanceof String) {
			sender.tell(message, getSelf());
		} else {
			unhandled(message);
		}
	}

	@Override
	public String getEndpointUri() {
		return uri;
	}
}
