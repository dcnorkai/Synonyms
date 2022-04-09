import io.StdIn._
import io.Source
import java.io.File         //technically not needed
import java.io.FileWriter   //makes it easy to append
import java.io.PrintWriter  //makes "print" and "println" available

//BUG: Not reading multiple lines in for synonyms
//Need to convert all synonyms to lowercase

object Synonyms extends App {

  val lines: List[String] = Source.fromFile("./resources/Fallows1898.txt").getLines.toList
  val dictionary = lines.slice(99, 33712)
  var myMap: Map[String, List[String]] = Map()

  val KEY_pattern = "KEY: (.*)".r
  val SYN_pattern = "SYN: (.*)".r
  val ANT_pattern = "ANT: (.*)".r
  val character_regex = "([a-zA-Z]*).*".r
  var SYN_flag = false
  var key: String = ""

  //Looping through dictionary
  for (line <- dictionary) {
    line match {
      case KEY_pattern(c) => {
        key = (c.substring(0, c.length() - 1)).toLowerCase //Removes period at the end of the key string
        //println("KEY: " + key) //Print test for key
        if (!(myMap.contains(key))) {
          myMap += (key -> List())
        }
      }
      case SYN_pattern(c) => {
        SYN_flag = true
        val split_words: List[String] = (c.toLowerCase.split(", ").map(x => {
          x match {
            case character_regex(c2) => c2
          }
        })).toList
        //println("SYN: " + split_words) //Print test for split_words
        //println(myMap(key))
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
          //println("TEST: " + line)
          val split_words: List[String] = (line.toLowerCase.split(", ").map(x => {x match {case character_regex(c2) => c2}})).toList
          //println(split_words)
          myMap += (key -> List.concat(myMap(key), split_words))
        }
      }
    }
  }

  //Prints myMap
  //for ((k,s) <- myMap) printf("KEY: %s, SYN: %s\n", k, s)

  //Looping through myMap
  for ((k,synList) <- myMap) {
    //Check if first letter of key is q
    if (k(0) == 'q') {
      //For each value associated with the current key
      for (s <- synList) {
        if (myMap.contains(s)) {
          if (myMap(s).length > 0) {
            if (myMap(s).contains(k)) {
              println(k + " and " + s + " are reciprocal synonyms")
            }
            else {
              println(k + " lists "+ s + " but " + s + " has a list of words without " + k)
            }
          }
        }
      }
    }
  }
}
