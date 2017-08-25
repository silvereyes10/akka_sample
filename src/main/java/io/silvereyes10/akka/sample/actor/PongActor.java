package io.silvereyes10.akka.sample.actor;

import akka.actor.AbstractLoggingActor;

public class PongActor extends AbstractLoggingActor {
	/**
	 * Dispatcher에서 쓰레드가 할당되면, 정의된 Receive 객체를 이용하여 Mailbox의 Message를 처리
	 * @return 전달된 Message를 처리할 Receive
	 */
	@Override
	public Receive createReceive() {
		return receiveBuilder()
				.match(String.class, message -> { // Message의 Instance를 비교하여, 처리하도록 구현
					log().info("Pong received {}", message);
					getSender().tell("pong", getSelf()); //  Actor에게 Message 던짐

					Thread.sleep(1000); // 예제 확인 용.
				})
				.build();
	}
}
