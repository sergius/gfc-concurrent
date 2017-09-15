package com.gilt.gfc.concurrent

import java.util.concurrent.{ExecutorService => JExecutorService, ScheduledExecutorService => JScheduledExecutorService}

import com.gilt.gfc.concurrent.{ScheduledExecutorService => GScheduledExecutorService}

import org.scalatest.mockito.{MockitoSugar => ScalaTestMockitoSugar}
import org.scalatest.{WordSpecLike, Matchers => ScalaTestMatchers}

class JavaConversionsSpec extends WordSpecLike
  with ScalaTestMatchers
  with ScalaTestMockitoSugar {

  "When converting java `ScheduledExecutorService`, and JavaConversions is imported" must {
    "compile" in {

      import JavaConversions._

      val mockJavaSchExecService = mock[JScheduledExecutorService]

      val serviceUnderTest: GScheduledExecutorService = mockJavaSchExecService
    }
  }

  "When converting java `ExecutorService`, and JavaConversions is imported" must {
    "compile" in {

      import JavaConversions._

      val mockJavaService = mock[JExecutorService]

      val serviceUnderTest: ExecutorService = mockJavaService
    }
  }
}
