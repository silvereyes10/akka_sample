package io.silvereyes10.akka.strategy;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import io.silvereyes10.akka.strategy.actor.PingActor;

public class GoodMain {
	public static void main(String[] args) {
		ActorSystem actorSystem = ActorSystem.create("TestSystem");
		ActorRef ping = actorSystem.actorOf(Props.create(PingActor.class), "pingActor");
		//ping.tell("good", ActorRef.noSender());
		ping.tell("bad", ActorRef.noSender());
	}
}
