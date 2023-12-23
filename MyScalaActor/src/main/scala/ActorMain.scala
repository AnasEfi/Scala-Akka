/*import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object ActorMain {

  sealed trait Protocol

  final case class Start(actorChildName: String) extends Protocol

  final case class StartChild_1(actorChildName: String, count: String) extends Protocol

  final case class StartChild_2(actorChildName: String, count: String) extends Protocol

  def apply(neighborsCount: Int): Behavior[Start] =
    Behaviors.setup { context =>
      println("Creating a parent Actor")
      context.spawn(ActorParent(neighborsCount), "parent")
      Behaviors.receiveMessage {message =>
        message match {
          case StartChild_1(actorChildName, count) =>
            println("Creating a child Actor wit one node")
            context.spawn(ActorParent(neighborsCount), "parent")

        }
        }
      }
    }

  def StartChild(actorChildName: String, count: Int): Behavior[StartChild_1] = {
    Behaviors.setup { context =>
      println("Creating a parent Actor")
      Behaviors.receiveMessage { message =>
        context.spawn(ActorChild(count), message.actorChildName)
        Behaviors.same
      }
    }
  }
}
*/