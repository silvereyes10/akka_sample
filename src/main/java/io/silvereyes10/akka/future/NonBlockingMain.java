package io.silvereyes10.akka.future;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import io.silvereyes10.akka.future.actor.NonBlockingActor;

public class NonBlockingMain {
	public static void main(String[] args) {
		ActorSystem actorSystem = ActorSystem.create("TestSystem");
		ActorRef nonBlockingActor = actorSystem.actorOf(Props.create(NonBlockingActor.class), "nonblockingActor");
		nonBlockingActor.tell(10, ActorRef.noSender());
		nonBlockingActor.tell("hello", ActorRef.noSender());
	}
}
