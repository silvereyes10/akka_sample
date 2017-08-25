package io.silvereyes10.akka.future;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import io.silvereyes10.akka.future.actor.BlockingActor;

public class BlockingMain {
	public static void main(String[] args) {
		ActorSystem actorSystem = ActorSystem.create("TestSystem");
		ActorRef blockingActor = actorSystem.actorOf(Props.create(BlockingActor.class), "blockingActor");
		blockingActor.tell(10, ActorRef.noSender());
		blockingActor.tell("hello", ActorRef.noSender());
	}
}
