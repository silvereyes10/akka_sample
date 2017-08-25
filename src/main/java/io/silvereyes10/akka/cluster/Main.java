package io.silvereyes10.akka.cluster;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.FromConfig;
import io.silvereyes10.akka.cluster.actor.HttpActor;
import io.silvereyes10.akka.cluster.actor.PingServiceActor;

public class Main {
	public static void main(String[] args) {
		ActorSystem actorSystem = ActorSystem.create("ClusterSystem");
		ActorRef router = actorSystem.actorOf(Props.create(PingServiceActor.class).withRouter(new FromConfig()), "serviceRouter");
		ActorRef httpActor = actorSystem.actorOf(Props.create(HttpActor.class, router), "httpActor");
	}
}
