import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx
import com.orientechnologies.orient.core.db.record.ORecordOperation
import com.orientechnologies.orient.core.record.impl.ODocument
import com.orientechnologies.orient.core.sql.query.{OResultSet, OLiveQuery, OLiveResultListener}

import scala.concurrent.Future
import scala.io.StdIn
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

/**
  * Created by Rodrigo Lima on 24/08/2016.
  */

object Main {

  private val uri: String = "remote:localhost/DataBaseName"

  def main(args: Array[String]) {

    def livePerson(name: String): Unit = Future {

      val db: ODatabaseDocumentTx = new ODatabaseDocumentTx(uri)
      db.open("admin", "admin")

      val listener: MyLiveQueryListener = new MyLiveQueryListener(db)
      val result: OResultSet[ODocument] = db.query(new OLiveQuery[ODocument]("live select from Person", listener))

      ResultListener(name, result.get(0).field("token"))

    } onComplete {
      case Success(s) => println(s"Toke ${s.name}: ${s.token}")
      case Failure(e) => e.printStackTrace()
    }

    livePerson("1")
    livePerson("2")

    StdIn.readLine()
  }
}

final case class ResultListener(name: String, token: Int)

final class MyLiveQueryListener(db: ODatabaseDocumentTx) extends OLiveResultListener {

  override def onLiveResult(iLiveToken: Int, iOp: ORecordOperation): Unit = {
    if (!db.isActiveOnCurrentThread) db.activateOnCurrentThread

    var msg: String = "\nNew result from server for live query " + iLiveToken + "\n"
    msg += "operation: " + iOp.`type` + "\n"
    msg += "content: " + iOp.record

    println(msg)
  }

  def onError(iLiveToken: Int): Unit = {
    println("Live query terminate due to error")
  }

  def onUnsubscribe(iLiveToken: Int): Unit = {
    println("Live query terminate with unsubscribe")
  }
}

