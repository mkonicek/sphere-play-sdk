package de.commercetools.sphere.client
package shop
package model

import java.math.BigDecimal
import scala.collection.mutable.ListBuffer
import scala.collection.JavaConversions._

import TestUtil.eur
import JsonTestObjects._
import de.commercetools.sphere.client.model.{Reference, Money, EmptyReference}

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers

class VariantSpec extends WordSpec with MustMatchers {

  "getPrice()" must {
    val emptyGroup: Reference[CustomerGroup] = EmptyReference.create("customerGroup")
    val eur100 = new Price(eur(100), "", emptyGroup)
    val eur200DE = new Price(eur(200), "DE", emptyGroup)
    val usd300 = new Price(new Money(new BigDecimal(300), "USD"), "", emptyGroup)
    val eur400Group = new Price(eur(400), "", customerGroup)
    val eur500DEGroup = new Price(eur(500), "DE", customerGroup)
    val eur600FRGroup = new Price(eur(600), "FR", customerGroup)
    val usd700DEGroup = new Price(new Money(new BigDecimal(700), "USD"), "DE", customerGroup)
    val usd800FRGroup = new Price(new Money(new BigDecimal(800), "USD"), "FR", customerGroup)
    val prices: java.util.List[Price] = ListBuffer(List(eur100, eur200DE, usd300, eur400Group, eur500DEGroup,
      eur600FRGroup, usd700DEGroup, usd800FRGroup): _*)
    val variant = new Variant(1, "sku", prices, Nil, Nil)

    "find a price by currency" in {
      variant.getPrice("EUR").getValue.getAmount.intValue must be (100)
      variant.getPrice("USD").getValue.getAmount.intValue must be (300)
      variant.getPrice("GBP") must be (null)
    }
    "find a price by currency and country" in {
     variant.getPrice("EUR", "DE").getValue.getAmount.intValue must be (200)
     variant.getPrice("EUR", "GB").getValue.getAmount.intValue must be (100)
     variant.getPrice("USD", "GB").getValue.getAmount.intValue must be (300)
    }
    "find a price by currency, country and customer group" in {
      variant.getPrice("EUR", "DE", customerGroup).getValue.getAmount.intValue must be (500)
      variant.getPrice("EUR", "DE", customerGroup2).getValue.getAmount.intValue must be (200)
      variant.getPrice("EUR", "GB", customerGroup).getValue.getAmount.intValue must be (400)
      variant.getPrice("USD", "GB", customerGroup).getValue.getAmount.intValue must be (300)
      variant.getPrice("USD", "GB", customerGroup2).getValue.getAmount.intValue must be (300)
    }
  }
}