package demo

fun main(){
    println("Hello World")

    //immutable / unchangable / read only = vals

    val name = "Omar"

    //changeable variables = vars
    var myAge = 28

    //kotlin uses type inference so whatever you put after your equal sign will be what your variable
    //is considered by kotlin
    //above the name is a string, age is an integer

    var bigInt:  Int = Int.MAX_VALUE
    var smallInt: Int = Int.MIN_VALUE

    println("Biggest Int: " + bigInt)  // printing the old way
    println("Smallest Int: $smallInt") //printing with string interpolation // $

    var bigLong:  Long = Long.MAX_VALUE
    var smallLong: Long = Long.MIN_VALUE

    println("Biggest Long: " + bigLong)  // printing the old way
    println("Smallest Long: $smallLong") //printing with string interpolation // $

    var bigDouble:  Double = Double.MAX_VALUE
    var smallDouble: Double = Double.MIN_VALUE

    println("Biggest Double: " + bigDouble)  // printing the old way
    println("Smallest Double: $smallDouble") //printing with string interpolation // $

    var bigFloat:  Float = Float.MAX_VALUE
    var smallFloat: Float = Float.MIN_VALUE

    println("Biggest Float: " + bigFloat)  // printing the old way
    println("Smallest Float: $smallFloat") //printing with string interpolation // $

    var bigByte:  Byte = Byte.MAX_VALUE
    var smallByte: Byte = Byte.MIN_VALUE

    println("Biggest Byte: " + bigByte)  // printing the old way
    println("Smallest Byte: $smallByte") //printing with string interpolation // $


    //when working with floating point numbers, there's a loss of precision after 15 digits

    var firstDouble: Double = 1.111111111111111
    var secondDouble: Double = 1.111111111111111

    println("Sum: "+ (firstDouble + secondDouble)) // this prints the correct sum b/c its up to 15 digits after the decimal


    var firstDouble2: Double = 1.11111111111111111
    var secondDouble2: Double = 1.11111111111111111

    println("Sum: "+ (firstDouble2 + secondDouble2)) //notice that the sum now ends in a 3....precision thrown off with 16th decimal number and above


    println("\n\n\n\n")


    //the keyword is is used to see if a variable is an integer, float, boolean or double
    //so the line below is checking to see if true is a boolean
    if (true is Boolean) {
        println("true is boolean")
    }


    var letterGrade: Char = 'A'
    println("Is A is a Char?: ${letterGrade is Char}") //another way of using string interpolation.




    // ***********Casting**************
    println("\n\t\t\t\t Casting \n")

    println("3.14 to Int : " + (3.14.toInt())) //this just converted 3.14 from a string to a double. wtf

    println("A to Int : " + letterGrade.toInt()) //letterGrade is A above and this line is converting it from a Char to an Int

    println("65 to Char : " + 65.toChar() + "\n\n") //converting the Int to a Char


    //*************Strings**************

    println("\t\t\t\t Strings and String Manipulation \n")

    val longString = """ This is a long String """

    var fName = "Jeremy"
    var lName = "Rayms"

    fName = "Sally"

    var fullName = fName + " " + lName
    println("Name: $fullName")

    println("1 + 2 = ${1 + 2}")

    println("String Length : ${longString.length}")

    //comparing strings

    var sentenceOne = "A random sentence"

    var sentenceTwo = "a random sentence"

    println("Strings Equal : ${sentenceOne.equals(sentenceTwo)}")//prints true or false

    println("Compare A to B : ${"A".compareTo("B")}") // prints a 0 if equal, negative will be a number < 0 and positive will be a number > 0

    println("2nd Index: ${sentenceOne.get(2)}")//getting a character at a specific index...2nd index in sentence one in this example
    println("2nd Index: ${sentenceOne[2]}")//this does the same thing as the line above. Use whichever you want

    println("Index 2 - 7: ${sentenceOne.subSequence(2, 8)}")//Gets a substring using the indexes you put in. In this example you get indexes 2 to 7 even though 8 is in the param because indexes start at 0

    println("Contains random?: ${sentenceOne.contains("random")}")//Checking to see if a string contains another string

    println("\n\n\n ")

    //*************Arrays**************

    println("\t\t\t\t Arrays \n")
}