package ru.skillbranch.devintensive.models

class Bender(
    var status: Status = Status.NORMAL,
    var question: Question = Question.NAME
) {
    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING (Triple(255, 120, 0)),
        DANGER (Triple(255, 60, 60)),
        CRITICAL (Triple(255, 255, 0));

        fun nextStatus():Status{
            return if(this.ordinal < values().lastIndex){
                values()[this.ordinal + 1]
            }
            else{
                values()[0]
            }
        }
    }

    enum class Question(
        val question:String,
        val answers:List<String>){
        NAME("Как меня зовут?", listOf("Бендер", "Bender")){
            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")){
            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL ("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")){
            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")){
            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")){
            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()){
            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
    }

    fun askQuestion(): String{
        return when(question){
            Question.NAME -> Question.NAME.question
            Question.PROFESSION-> Question.PROFESSION.question
            Question.MATERIAL-> Question.MATERIAL.question
            Question.BDAY-> Question.BDAY.question
            Question.SERIAL-> Question.SERIAL.question
            Question.IDLE-> Question.IDLE.question
        }
    }

    fun checkForDigits(str:String): Boolean{
        for (i in 0..str.length-1){
            if(str.get(i).isDigit()) return true
        }
        return false
    }

    fun validateAnswer(answer: String): String?{
        return when(question){
            Question.NAME -> if(answer.getOrNull(0)!!.isLowerCase())"Имя должно начинаться с заглавной буквы" else null
            Question.PROFESSION -> if(answer.getOrNull(0)!!.isUpperCase())"Профессия должна начинаться со строчной буквы" else null
            Question.MATERIAL -> if(checkForDigits(answer)) "Материал не должен содержать цифр" else null
            Question.BDAY -> if(!answer.contains("[^0123456789]"))"Год моего рождения должен содержать только цифры" else null
            Question.SERIAL -> if(!answer.contains("[^01234567899]") && answer.length != 7)"Серийный номер содержит только цифры, и их 7" else null
            Question.IDLE -> null
        }
    }

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>>{
        val validatedAnswer: String? = validateAnswer(answer)
        return if(question.answers.contains(answer)){
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color
        }
        else if(question == Question.IDLE){
            question.question to status.color
        }
        else{
            when {
                validatedAnswer!= null -> validatedAnswer + "\n" + question.question to status.color
                status == Status.CRITICAL -> {
                    question = Question.NAME
                    status = Status.NORMAL
                    "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
                }
                else -> {
                    status = status.nextStatus()
                    "Это неправильный ответ\n${question.question}" to status.color
                }
            }
        }
    }
}