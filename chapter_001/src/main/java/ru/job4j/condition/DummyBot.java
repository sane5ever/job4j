package ru.job4j.condition;

/**
 * Глупый бот. Принимает слово и возвращает ответ.
 * @author Alexander Savchenko (sane5ever@gmail.com)
 * @version 1.0
 * @since 13.09.2018
 */
public class DummyBot {
    /**
     * Отвечает на вопросы.
     * @param question вопрос от клиента
     * @return ответ
     */
    public String answer(String question) {
        String result = "Это ставит меня в тупик. Спросите другой вопрос.";
        if ("Привет, Бот.".equals(question)) {
            result = "Привет, умник.";
        } else if ("Пока.".equals(question)) {
            result = "До скорой встречи.";
        }
        return result;
    }
}
