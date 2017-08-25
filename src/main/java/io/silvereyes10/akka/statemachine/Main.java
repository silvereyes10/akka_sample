package io.silvereyes10.akka.statemachine;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import io.silvereyes10.akka.statemachine.actor.PingActor;

public class Main {
	public static void main(String[] args) {
		ActorSystem actorSystem = ActorSystem.create("TestSystem");
		ActorRef ping = actorSystem.actorOf(Props.create(PingActor.class), "pingActor");
		ping.tell("work", ActorRef.noSender());
		ping.tell("reset", ActorRef.noSender());
	}
}
