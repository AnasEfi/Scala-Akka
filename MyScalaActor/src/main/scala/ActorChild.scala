import ActorParent.GetMessageFromNode
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, Behavior}


object ActorChild {

  sealed trait Protocol

  final case class SendMessage(tok: String) extends Protocol

  final case class FillNeighbour(neighbour: ActorRef[ActorChild.Protocol]) extends Protocol
  final case class getMessageAndReply(tok: String, replyTo: ActorRef[ActorChild.Protocol]) extends Protocol
  final case class getMessageFromParentNode(tok: String, replyTo: ActorRef[ActorParent.Protocol]) extends Protocol
  final case class getMessageFromChild(receiveTok: String, replyTo: ActorRef[ActorChild.Protocol]) extends Protocol

  def apply(neighborsCount: Int): Behavior[Protocol] = Behaviors.setup { context =>
    var actorsList: List[ActorRef[ActorChild.Protocol]] = List.empty
    var actorsResponse: Long = 0

    Behaviors.receive { (context, message) =>
      message match {
        case FillNeighbour(actor) =>
          actorsList = actorsList :+ actor
          println(f"To ${context.self.path.name} added a new neighbour ${actor.path.name}")
          Behaviors.same
        case getMessageFromParentNode(tok, replyTo) =>
          println(f"Get message:[${tok}] from:[${replyTo.path.name}] to Node:[${context.self.path.name}]")
          var parent: ActorRef[ActorParent.Protocol] = replyTo
          actorsList.foreach(neighbor =>
            neighbor ! getMessageAndReply(tok, context.self)
          )
          Behaviors.receive { (context, message) => {
            message match {
              case getMessageFromChild(tok, replyTo) =>
                println(s"Message [${tok}] come from [${replyTo.path.name}] ")
                actorsResponse = actorsResponse + 1
                println(f"Now responces ${actorsResponse} from ${neighborsCount}")
                if (actorsResponse == neighborsCount) {
                  parent ! GetMessageFromNode(tok, context.self)
                  Behaviors.stopped
                }
                else Behaviors.same
            }
          }
          }
        case getMessageAndReply(tok, replyTo) =>
          replyTo ! getMessageFromChild("Got Message", replyTo)
          println(f"[${context.self.path.name}] got a message [${tok}] from [${replyTo.path.name}] and send back")
          Behaviors.stopped
      }
    }
  }
}

