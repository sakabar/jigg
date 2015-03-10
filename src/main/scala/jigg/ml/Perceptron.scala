package jigg.ml

import scala.collection.mutable.ArrayBuffer

trait Perceptron[L] extends LinearClassifier[L] with OnlineTrainer[L] {

  def averageWeights: WeightVector[Float]

  var c = 1.0F

  override def update(examples: Seq[Example[L]], gold: L): Unit = {
    val pred = predict(examples)._1
    if (pred != gold) {
      var i = 0
      while (i < examples.size) {
        val label = examples(i).label
        if (label == pred) updateFeatureWeighs(examples(i).featVec, -1.0F)
        else if (label == gold) updateFeatureWeighs(examples(i).featVec, 1.0F)
        i += 1
      }
    }
    c += 1.0F
  }
  def updateFeatureWeighs(featVec: Array[Int], scale: Float): Unit = featVec.foreach { f =>
    weights(f) += scale
    averageWeights(f) += scale * c
  }
  def update(predFeatVec:Array[Int], goldFeatVec:Array[Int]): Unit = {
    updateFeatureWeighs(predFeatVec, -1.0F)
    updateFeatureWeighs(goldFeatVec, 1.0F)
    c += 1.0F
  }
  def takeAverage: Unit = (0 until weights.size) foreach { i =>
    weights(i) -= averageWeights(i) / c
  }
}

class FixedPerceptron[L](val weightArray: Array[Float]) extends Perceptron[L] {

  override val weights = new FixedWeightVector(weightArray)
  override val averageWeights = new FixedWeightVector(new Array[Float](weights.size))
}

class GrowablePerceptron[L](val weightArray: ArrayBuffer[Float]) extends Perceptron[L] {

  override val weights = new GrowableWeightVector(weightArray)
  override val averageWeights = WeightVector.growable[Float](weights.size)
}