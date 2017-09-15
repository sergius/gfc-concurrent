package com.gilt.gfc.concurrent

import java.util.concurrent.{ExecutorService => JExecutorService, ScheduledExecutorService => JScheduledExecutorService}

import org.scalatest.mockito.{MockitoSugar => ScalaTestMockitoSugar}
import org.scalatest.{WordSpecLike, Matchers => ScalaTestMatchers}

class JavaConvertersSpec extends WordSpecLike
  with ScalaTestMatchers
  with ScalaTestMockitoSugar {

  "When converting java `ScheduledExecutorService`, and JavaConverters is imported" must {
    "compile" in {

      import JavaConverters._

      val mockJavaSchExecService = mock[JScheduledExecutorService]

      val serviceUnderTest: AsyncScheduledExecutorService = mockJavaSchExecService.asScala
    }
  }

  "When converting java `ExecutorService`, and JavaConverters is imported" must {
    "compile" in {

      import JavaConverters._

      val mockJavaService = mock[JExecutorService]

      val serviceUnderTest: ExecutorService = mockJavaService.asScala
    }
  }
}
