package com.gilt.gfc.concurrent

import java.util.concurrent.atomic.DoubleAdder

import org.scalatest.FunSuite

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps


class BatcherTest
  extends FunSuite {

  test("batcher works single-threadedly") {
    for ( maxOutstanding <- (1 to 10) ;
          numRecords <- (1 to 100) ) {

      val records = (1 to numRecords)
      val (adder, batcher) = mkTestBatcher(maxOutstanding)
      records.foreach(i => batcher.add(i))

      batcher.flush()
      assert(adder.intValue == records.sum)

      batcher.shutdown()
    }
  }


  test("batcher flushes after a period of time") {
    val records = (1 to 10)
    val (adder, batcher) = mkTestBatcher(100)
    records.foreach(i => batcher.add(i))

    Thread.sleep(1001L) // should flush after 1sec
    assert(adder.intValue == records.sum)

    batcher.shutdown()
  }


  test("batcher flushes after max outstanding count") {
    val records = (1 to 9)
    val (adder, batcher) = mkTestBatcher(5)
    records.foreach(i => batcher.add(i))
    assert(adder.intValue == (1 to 5).sum) // should see 5 out of 9

    batcher.shutdown()
  }


  test("batcher works concurrently") {

    val (adder, batcher) = mkTestBatcher(10)
    val records = (1 to 10000)

    val futures = records.map(i => Future{ batcher.add(i) } )
    Await.result(Future.sequence(futures), 2 seconds) // should flush after 1sec

    assert(adder.intValue == records.sum)
    batcher.shutdown()
  }


  private[this]
  def mkTestBatcher( maxOutstandingCount: Int
                   ): (DoubleAdder, Batcher[Int]) = {
    val adder = new DoubleAdder()

    val batcher = Batcher[Int](
      "test"
    , maxOutstandingCount
    , 1 second
    ) { records =>
      records.foreach(i => adder.add(i))
    }

    (adder, batcher)
  }
}
