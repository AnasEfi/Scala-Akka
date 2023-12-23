import akka.NotUsed
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior, Terminated}

object Main {
  sealed trait Protocol

  def apply(): Behavior[NotUsed] = {
    Behaviors.setup { context =>

      val Node_1 = context.spawn(ActorParent(1), "parent")
      val Node_2 = context.spawn(ActorChild(2),"Node2")
      val Node_3 = context.spawn(ActorChild(0),"Node3")
      val Node_4 = context.spawn(ActorChild(0),"Node4")

      Node_2 ! ActorChild.FillNeighbour(Node_3)
      Node_2 ! ActorChild.FillNeighbour(Node_4)

      Node_1 ! ActorParent.SendTok("TOKEN", Node_2)

      context.watch(Node_1)
      
      Behaviors.receiveSignal {
        case (_, Terminated(_)) =>
          Behaviors.stopped
      }
    }
  }
    def main(args:Array[String]): Unit = {
      ActorSystem(Main(),"EchoRoom")

  }
}

