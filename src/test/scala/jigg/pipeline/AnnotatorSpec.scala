package jigg.pipeline

import java.util.Properties
import scala.xml.Node
import org.scalatest._

import jigg.util.Prop

class NothingAnnotator(override val name: String, override val props: Properties) extends Annotator {

  @Prop(gloss = "gloss of variable1") var variable1 = ""

  def annotate(node: Node) = node
}

class AnnotatorSpec extends FlatSpec with Matchers {

  "Opt variable" should "be customizable with property file" in {
    val props = new Properties
    props.setProperty("nothing.variable1", "hoge")

    val annotator = new NothingAnnotator("nothing", props)
    annotator.initProps

    annotator.variable1 should be("hoge")
  }
}