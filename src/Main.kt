package signature

import java.io.File
import java.util.Scanner

fun readFont(fontName: String): Triple<String, Array<Array<String>>, Int> {
    val fileScanner = Scanner(File(fontName))
    val (symbolHeight, symbolCount) = fileScanner.nextLine().split(' ').map { it.toInt() }
    var alphabet = ""
    val font = Array(symbolCount) { Array(symbolHeight) { "" } }
    var idx = 0
    while (fileScanner.hasNext()) {
        val (symbol, symbolLen) = fileScanner.nextLine().split(' ')
        alphabet += symbol
        for (lineNum in 0 until symbolHeight) {
            font[idx][lineNum] = fileScanner.nextLine().padEnd(symbolLen.toInt())
        }
        idx += 1
    }
    return Triple(alphabet, font, symbolHeight)
}

fun main() {
    fun strToFont(str: String, alphabet: String, font: Array<Array<String>>, height: Int, spaceLen: Int): Array<String> {
        val arr = Array(height) { "" }
        for (line in 0 until height) {
            for (char in str) {
                arr[line] += if (char == ' ') {
                    " ".repeat(spaceLen)
                } else {
                    font[alphabet.indexOf(char)][line]
                }
            }
        }
        return arr
    }
    val (mediumAlphabet, mediumFont, mediumHeight) = readFont("N:/Repos/ideas/ASCII Text Signature/ASCII Text Signature/task/src/signature/medium.txt")
    val (romanAlphabet, romanFont, romanHeight) = readFont("N:/Repos/ideas/ASCII Text Signature/ASCII Text Signature/task/src/signature/roman.txt")
    val scanner = Scanner(System.`in`)
    println("Enter name and surname: ")
    val name = scanner.nextLine()
    val nameArr = strToFont(name, romanAlphabet, romanFont, romanHeight, 10)
    println("Enter person's status: ")
    val status = scanner.nextLine()
    val statusArr = strToFont(status, mediumAlphabet, mediumFont, mediumHeight, 5)
    val maxLen = Math.max(statusArr[0].length, nameArr[0].length)
    val nameSpacesLen = maxLen - nameArr[0].length
    val nameSpacesLeft = if (nameSpacesLen % 2 == 0) {
        nameSpacesLen / 2
    } else {
        (nameSpacesLen - 1) / 2
    }
    val nameSpacesRight = nameSpacesLen - nameSpacesLeft
    val statusSpacesLen = maxLen - statusArr[0].length
    val statusSpacesLeft = if (statusSpacesLen % 2 == 0) {
        statusSpacesLen / 2
    } else {
        (statusSpacesLen - 1) / 2
    }
    val statusSpacesRight = statusSpacesLen - statusSpacesLeft
    val totalWidth = maxLen + 8
    val border = "8".repeat(totalWidth)
    println(border)
    for (line in nameArr) {
        println("88  ${" ".repeat(nameSpacesLeft)}$line${" ".repeat(nameSpacesRight)}  88")
    }
    for (line in statusArr) {
        println("88  ${" ".repeat(statusSpacesLeft)}$line${" ".repeat(statusSpacesRight)}  88")
    }
    println(border)
}
