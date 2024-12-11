package id.hoptima.util

import java.text.NumberFormat
import java.util.Locale

object NumberUtil {
    private val rupiahFormatter = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

    fun formatRupiah(input: Long): String {
        return rupiahFormatter.format(input)
    }
}