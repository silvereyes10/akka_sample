package io.silvereyes10.akka.sample;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import io.silvereyes10.akka.sample.actor.PingActor;

public class Main {
	public static void main(String[] args) {
		ActorSystem actorSystem = ActorSystem.create("TestSystem");                                               // ActorSystem의 생성과 함께 시스템의 이름을 명명
		ActorRef ping = actorSystem.actorOf(Props.create(PingActor.class, PingActor::new), "pingActor");   // Message를 던질 최초의 Actor를 등록
		ping.tell("start", ActorRef.noSender());                                                            // Message를 던짐
	}
}
