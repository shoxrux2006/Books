package uz.gita.bookappShoxruh.utils


fun Long.trim(): String {
    var str = this.toString()
    when (this) {
        in 100..500 -> {
            str = "100+"
        }
        in 500..999 -> {
            str="1k"
        }
        in 1000..Int.MAX_VALUE -> {
            str = "${(this / 1000).toInt()}k"
        }

    }
    return str
}

fun main() {
    var num = 999L
    println(num.trim())
}
