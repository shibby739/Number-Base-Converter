package converter

import kotlin.math.pow

fun main() {
    println("Enter two numbers in format: {source base} {target base} (To quit type /exit)")
    var input = readln()
    if (input == "/exit") {
        return
    } else if (input != "/exit") {
        val (sourceBase, targetBase) = input.split(" ").map { it.toInt() }
        println("Enter number in base $sourceBase to convert to base $targetBase (To go back type /back)")
        input = readln()
        if (input == "/back") {
            println()
            main()
        } else {
            if ("." in input) {
                convertFraction(input, sourceBase, targetBase)
            } else {
                convertNum(input, sourceBase, targetBase)
            }
        }
    }
}

fun convertNum(num: String, sourceBase: Int, targetBase: Int) {
    val decimal = num.toBigInteger(sourceBase)
    val result = decimal.toString(targetBase)
    println("Conversion result: $result")
    println()
    println("Enter number in base $sourceBase to convert to base $targetBase (To go back type /back)")
    val input = readln()
    if (input == "/back") {
        println()
        main()
    } else if ("." in input) {
        convertFraction(input, sourceBase, targetBase)
    } else {
        convertNum(input, sourceBase, targetBase)
    }
}

fun convertFraction(num: String, sourceBase: Int, targetBase: Int) {
    var (int, fraction) = num.split(".")
    val intResult = int.toBigInteger(sourceBase).toString(targetBase)
    var n = 1
    val fracToDecList = mutableListOf<Double>()
    for (i in fraction) {
        val digit = i.digitToInt(sourceBase) / sourceBase.toDouble().pow(n)
        fracToDecList.add(digit)
        n++
    }
    var decimalFraction = fracToDecList.sum()
    val remainderList = mutableListOf<String>()
    repeat(5) {
        decimalFraction *= targetBase
        int = decimalFraction.toString().substringBefore(".")
        decimalFraction = (decimalFraction.toString().substring(decimalFraction.toString().indexOf("."))).toDouble()
        remainderList.add(int)
    }
    val lettersList = mutableListOf<String>()
    if (targetBase > 10) {
        for (i in remainderList) {
            val a = i.toBigInteger().toString(targetBase)
            lettersList.add(a)
        }
        val fractionResult = lettersList.joinToString("").substring(0, 5)
        println("Conversion result: $intResult.$fractionResult")
    } else {
        val fractionResult = remainderList.joinToString("").substring(0, 5)
        println("Conversion result: $intResult.$fractionResult")
    }
    println()
    println("Enter number in base $sourceBase to convert to base $targetBase (To go back type /back)")
    val input = readln()
    if (input == "/back") {
        println()
        main()
    } else if ("." in input) {
        convertFraction(input, sourceBase, targetBase)
    } else {
        convertNum(input, sourceBase, targetBase)
    }
}

