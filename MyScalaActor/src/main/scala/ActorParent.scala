import Main._
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}

object ActorParent {

  sealed trait Protocol
  final case class SendTok(tok: String, Node: ActorRef[ActorChild.Protocol]) extends Protocol
  case class GetMessageFromNode(message: String, targetNode: ActorRef[ActorChild.Protocol]) extends Protocol


  def apply(neighborsCount: Int): Behavior[Protocol] = {
    println("ParentNode was spawned")
    nodeSettings(0, neighborsCount)
  }

  private def nodeSettings(neighborResponses : Int, neighborsCount: Int): Behavior[Protocol] =
    Behaviors.receive { (context, message) =>
      message match {
        case SendTok(tok, node) =>
          println(s"Node ${context.self.path.name} sending message: to ${node.path.name} message is //${tok}//")
          node ! ActorChild.getMessageFromParentNode(tok, context.self)
          Behaviors.same
        case GetMessageFromNode(receive, from) =>
          val n = neighborResponses + 1
          println(f"Response ${from.path.name}")
          println(s"Node ${context.self.path.name} get message from ${from.path.name}: ${receive}")
          println (f"Now responses came ${n} from ${neighborsCount}")
          if (n == neighborsCount) {
            println(f"decision")
            Behaviors.stopped
          }
          else {
            nodeSettings(n,neighborsCount)
          }
      }

    }
  }
