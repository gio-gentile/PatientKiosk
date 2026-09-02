package it.GiorgioGentile759951.patientkiosk.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import it.GiorgioGentile759951.patientkiosk.data.model.Answer
import it.GiorgioGentile759951.patientkiosk.data.model.GroupResult
import it.GiorgioGentile759951.patientkiosk.data.model.Questionnaire
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.max
import android.graphics.BitmapFactory
import android.graphics.RectF
import it.GiorgioGentile759951.patientkiosk.R

object PdfExporter {

    private const val PAGE_WIDTH = 595
    private const val PAGE_HEIGHT = 842
    private const val MARGIN = 40f
    private const val TABLE_QUESTION_WIDTH = 310f
    private const val TABLE_ANSWER_WIDTH = 165f
    private const val TABLE_SCORE_WIDTH = 40f

    fun exportResult(
        context: Context,
        uri: Uri,
        patientCode: String,
        questionnaire: Questionnaire,
        selectedAnswers: List<Answer?>,
        score: Int,
        maxScore: Int,
        interpretation: String,
        groupResults: List<GroupResult>
    ) {
        val logoBitmap = BitmapFactory.decodeResource(
            context.resources,
            R.drawable.patientkiosk_logo
        )

        val document = PdfDocument()

        val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 24f
            isFakeBoldText = true
        }

        val headingPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 17f
            isFakeBoldText = true
        }

        val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 12f
        }

        val tableHeaderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 11f
            isFakeBoldText = true
        }

        val tableTextPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = 10f
        }

        val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 1f
        }

        var pageNumber = 0
        var page: PdfDocument.Page
        var canvas: Canvas
        var y = MARGIN

        fun startPage(): PdfDocument.Page {

            pageNumber++

            val pageInfo = PdfDocument.PageInfo.Builder(
                PAGE_WIDTH,
                PAGE_HEIGHT,
                pageNumber
            ).create()

            return document.startPage(pageInfo)
        }

        page = startPage()
        canvas = page.canvas

        fun finishAndStartNewPage() {

            document.finishPage(page)

            page = startPage()
            canvas = page.canvas
            y = MARGIN
        }

        fun wrapText(
            text: String,
            paint: Paint,
            maxWidth: Float
        ): List<String> {

            if (text.isBlank()) {
                return listOf("")
            }

            val words = text.split(" ")
            val lines = mutableListOf<String>()

            var currentLine = ""

            words.forEach { word ->

                val candidate =
                    if (currentLine.isEmpty()) {
                        word
                    } else {
                        "$currentLine $word"
                    }

                if (paint.measureText(candidate) <= maxWidth) {

                    currentLine = candidate

                } else {

                    if (currentLine.isNotEmpty()) {
                        lines.add(currentLine)
                    }

                    currentLine = word
                }
            }

            if (currentLine.isNotEmpty()) {
                lines.add(currentLine)
            }

            return lines
        }

        fun drawWrappedText(
            text: String,
            x: Float,
            startY: Float,
            paint: Paint,
            maxWidth: Float,
            lineHeight: Float = 18f
        ): Float {

            var currentY = startY

            wrapText(
                text = text,
                paint = paint,
                maxWidth = maxWidth
            ).forEach { line ->

                canvas.drawText(
                    line,
                    x,
                    currentY,
                    paint
                )

                currentY += lineHeight
            }

            return currentY
        }

        fun drawTableHeader() {

            val top = y
            val height = 30f
            val bottom = top + height

            val xQuestion = MARGIN
            val xAnswer = xQuestion + TABLE_QUESTION_WIDTH
            val xScore = xAnswer + TABLE_ANSWER_WIDTH
            val xEnd = xScore + TABLE_SCORE_WIDTH

            canvas.drawRect(
                xQuestion,
                top,
                xEnd,
                bottom,
                borderPaint
            )

            canvas.drawLine(
                xAnswer,
                top,
                xAnswer,
                bottom,
                borderPaint
            )

            canvas.drawLine(
                xScore,
                top,
                xScore,
                bottom,
                borderPaint
            )

            canvas.drawText(
                "Domanda",
                xQuestion + 5f,
                top + 19f,
                tableHeaderPaint
            )

            canvas.drawText(
                "Risposta",
                xAnswer + 5f,
                top + 19f,
                tableHeaderPaint
            )

            canvas.drawText(
                "Pt.",
                xScore + 5f,
                top + 19f,
                tableHeaderPaint
            )

            y = bottom
        }

        fun drawTableRow(
            questionNumber: Int,
            questionText: String,
            answer: Answer?
        ) {

            val questionLines = wrapText(
                "$questionNumber. $questionText",
                tableTextPaint,
                TABLE_QUESTION_WIDTH - 10f
            )

            val answerText =
                answer?.text ?: "-"

            val answerLines = wrapText(
                answerText,
                tableTextPaint,
                TABLE_ANSWER_WIDTH - 10f
            )

            val lineHeight = 14f

            val contentLines = max(
                questionLines.size,
                answerLines.size
            )

            val rowHeight =
                max(
                    32f,
                    contentLines * lineHeight + 14f
                )

            // Non entra nella pagina:
            // nuova pagina + nuova intestazione.
            if (y + rowHeight > PAGE_HEIGHT - MARGIN) {

                finishAndStartNewPage()

                canvas.drawText(
                    "${questionnaire.name} — Risposte",
                    MARGIN,
                    y + 15f,
                    headingPaint
                )

                y += 35f

                drawTableHeader()
            }

            val top = y
            val bottom = y + rowHeight

            val xQuestion = MARGIN
            val xAnswer =
                xQuestion + TABLE_QUESTION_WIDTH

            val xScore =
                xAnswer + TABLE_ANSWER_WIDTH

            val xEnd =
                xScore + TABLE_SCORE_WIDTH

            canvas.drawRect(
                xQuestion,
                top,
                xEnd,
                bottom,
                borderPaint
            )

            canvas.drawLine(
                xAnswer,
                top,
                xAnswer,
                bottom,
                borderPaint
            )

            canvas.drawLine(
                xScore,
                top,
                xScore,
                bottom,
                borderPaint
            )

            var questionY = top + 17f

            questionLines.forEach { line ->
                canvas.drawText(
                    line,
                    xQuestion + 5f,
                    questionY,
                    tableTextPaint
                )

                questionY += lineHeight
            }

            var answerY = top + 17f

            answerLines.forEach { line ->
                canvas.drawText(
                    line,
                    xAnswer + 5f,
                    answerY,
                    tableTextPaint
                )

                answerY += lineHeight
            }

            canvas.drawText(
                answer?.score?.toString() ?: "-",
                xScore + 10f,
                top + 17f,
                tableTextPaint
            )

            y = bottom
        }

        // Intestazione
        val logoWidth = 70f

        val aspectRatio =
            logoBitmap.height.toFloat() /
                    logoBitmap.width.toFloat()

        val logoHeight =
            logoWidth * aspectRatio

        canvas.drawBitmap(
            logoBitmap,
            null,
            RectF(
                MARGIN,
                y,
                MARGIN + logoWidth,
                y + logoHeight
            ),
            null
        )

        canvas.drawText(
            "PatientKiosk",
            MARGIN + logoWidth + 15f,
            y + (logoHeight / 2f) + 10f,
            titlePaint
        )

        y += logoHeight + 35f

        canvas.drawText(
            "Risultato questionario",
            MARGIN,
            y,
            headingPaint
        )

        y += 30

        canvas.drawText(
            "Paziente: $patientCode",
            MARGIN,
            y,
            textPaint
        )

        y += 20f

        canvas.drawText(
            "Questionario: ${questionnaire.name}",
            MARGIN,
            y,
            textPaint
        )

        y += 20f

        y = drawWrappedText(
            text = "Descrizione: ${questionnaire.description}",
            x = MARGIN,
            startY = y,
            paint = textPaint,
            maxWidth = PAGE_WIDTH - MARGIN * 2
        )

        y += 20f

        val date = LocalDateTime.now()
            .format(
                DateTimeFormatter.ofPattern(
                    "dd/MM/yyyy HH:mm"
                )
            )

        canvas.drawText(
            "Compilato il: $date",
            MARGIN,
            y,
            textPaint
        )

        y += 40f

        // Risultato

        canvas.drawText(
            "Risultato",
            MARGIN,
            y,
            headingPaint
        )

        y += 28f

        if (groupResults.isEmpty()) {

            canvas.drawText(
                "Punteggio: $score / $maxScore",
                MARGIN,
                y,
                textPaint
            )

            y += 24f

            if (interpretation.isNotBlank()) {

                y = drawWrappedText(
                    text = "Interpretazione: $interpretation",
                    x = MARGIN,
                    startY = y,
                    paint = textPaint,
                    maxWidth = PAGE_WIDTH - MARGIN * 2
                )
            }

        } else {

            groupResults.forEach { result ->

                canvas.drawText(
                    result.name,
                    MARGIN,
                    y,
                    headingPaint
                )

                y += 22f

                canvas.drawText(
                    "${result.score} / ${result.maxScore}",
                    MARGIN,
                    y,
                    textPaint
                )

                y += 20f

                if (result.interpretation.isNotBlank()) {

                    y = drawWrappedText(
                        text = result.interpretation,
                        x = MARGIN,
                        startY = y,
                        paint = textPaint,
                        maxWidth = PAGE_WIDTH - MARGIN * 2
                    )
                }

                y += 15f
            }
        }

        y += 30f

        // Se non c'è abbastanza spazio nemmeno
        // per iniziare la tabella, pagina nuova.
        if (y > PAGE_HEIGHT - 150f) {

            finishAndStartNewPage()
        }

        // Tabella risposte
        canvas.drawText(
            "Dettaglio delle risposte",
            MARGIN,
            y,
            headingPaint
        )

        y += 25f

        drawTableHeader()

        questionnaire.questions.forEachIndexed { index,
                                                 question ->

            drawTableRow(
                questionNumber = index + 1,
                questionText = question.text,
                answer = selectedAnswers.getOrNull(index)
            )
        }

        // Footer
        if (y + 45f > PAGE_HEIGHT - MARGIN) {
            finishAndStartNewPage()
        }

        y += 30f

        canvas.drawText(
            "Documento generato da PatientKiosk.",
            MARGIN,
            y,
            textPaint
        )

        document.finishPage(page)

        context.contentResolver
            .openOutputStream(uri)
            ?.use { outputStream ->

                document.writeTo(outputStream)
            }

        document.close()
    }
}