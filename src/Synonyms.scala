import io.StdIn._
import io.Source
import java.io.File         //technically not needed
import java.io.FileWriter   //makes it easy to append
import java.io.PrintWriter  //makes "print" and "println" available

object Synonyms extends App {
  class Identifier {
    var key = ""
    var synList = ""
  }

  //val map: Map[String, Set[String]] = Map()
  //map.keys
  //map("recal") -> Set{}
  val lines: List[String] = Source.fromFile("./resources/Fallows1898.txt").getLines.toList
  val selectedLines = lines.drop(99)
  var myMap: Map[String, List[String]] = Map()

  //val filep = new PrintWriter(new FileWriter("output.txt"));  //appends
  //filep.println(selectedLines)
  //filep.close()

  // Make regex for KEY, SYN, ANT
  val KEY_pattern = "KEY: (.*)".r
  val SYN_pattern = "SYN: (.*)".r
  val ANT_pattern = "ANT: (.*)".r
  val character_regex = "([a-zA-Z]*).*".r
  var SYN_flag = false
  var key: String = ""
  for (line <- selectedLines) {
    line match {
      case KEY_pattern(c) => {
        println("Test")
        val key: String = c.substring(0, c.length() - 1)
        if (!(myMap.contains(key))) {
          myMap += (key, List())
        }
      }
      case SYN_pattern(c) => {
        SYN_flag = true
        val split_words: List[String] = (c.split(", ").map(x => {
          x match {
            case character_regex(c2) => c2
          }
        })).toList
        myMap += (key -> List.concat(myMap(key), split_words))
      }
      case ANT_pattern(c) => {
        SYN_flag = false
      }
      case "=" => {
        SYN_flag = false
      }
      case _ => {
        if (SYN_flag) {
          //val split_words: List[String] = (_.split(", ").map(x => {x match {case character_regex(c2) => c2}})).toList
          //myMap += (key -> List.concat(myMap(key), split_words)
        }
      }
    }
  }
}



/*  object MyClass {
    def main(args: Array[String]) {
      var A: Map[String, List[String]] = Map()
      A += ("Hello" -> List("World"))
      val ls: List[String] = A("Hello")
      A += ("Hello" -> List.concat(ls, List("Foo")))
      println(A)
    }
  }*/


/*
import io.StdIn._
import io.Source
import java.io.File         //technically not needed
import java.io.FileWriter   //makes it easy to append
import java.io.PrintWriter  //makes "print" and "println" available

object Synonyms extends App {
  class Identifier {
    var key = ""
    var synList = ""
  }
  val lines: List[String] = Source.fromFile("./resources/Fallows1898.txt").getLines.toList

  val selectedLines = lines.drop(99)

  // check the value of lines(100)
  val line1 = selectedLines(0)
  println(line1)

  // lines.slice(100, lines.length)

}*/
