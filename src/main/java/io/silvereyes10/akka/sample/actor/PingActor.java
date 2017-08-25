package io.silvereyes10.akka.sample.actor;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;

public class PingActor extends AbstractLoggingActor {
	private ActorRef pong;

	/**
	 * Actor가 생성되면서 수행되는 메소드
	 */
	@Override
	public void preStart() {
		this.pong = context().actorOf(Props.create(PongActor.class, PongActor::new), "pongActor");  // Message를 던질 Actor를 정의
	}

	/**
	 * Dispatcher에서 쓰레드가 할당되면, 정의된 Receive 객체를 이용하여 Mailbox의 Message를 처리
	 * @return 전달된 Message를 처리할 Receive
	 */
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(String.class, message -> {   // Message의 Instance를 비교하여, 처리하도록 구현
					log().info("Ping received {}", message);
					pong.tell("ping", getSelf());   // 다른 Actor에게 Message 던짐
				})
				.build();
	}
}
