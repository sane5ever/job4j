[![Codacy Badge](https://api.codacy.com/project/badge/Grade/f614f83d531c4c6eae387fa9392b8240)](https://www.codacy.com/app/inflatone/job4j?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=inflatone/job4j&amp;utm_campaign=Badge_Grade)
[![Build Status](https://travis-ci.org/inflatone/job4j.svg?branch=master)](https://travis-ci.org/inflatone/job4j)
[![codecov](https://codecov.io/gh/inflatone/job4j/branch/master/graph/badge.svg)](https://codecov.io/gh/inflatone/job4j)

# Проекты курса "С нуля до трудоустройства".
#### Обучение проходит на сайте http://job4j.ru/ для овладения навыками, необходимыми для начала карьеры java-разработчика.

#### Ссылки на избранные задания:
* Web-проекты _Middle_-заданий вынесены в отдельный <a href="https://github.com/inflatone/job4j.ee">репозиторий</a>.
* <a href="https://github.com/inflatone/job4j/tree/master/chapter_007">Задания</a> блока _JDBC_:
    * Консольный граббер вакансий по расписанию
    * XML XSLT JDBC Оптимизация
* <a href="https://github.com/inflatone/job4j/tree/master/chapter_006">Задания</a> блока _Multithreading_
    * Игра Бомбермен
    * Неблокирующий кеш
* <a href="https://github.com/inflatone/job4j/tree/master/chapter_008">Задания</a> блока _IO_:
    * Socket's Time Bot
    * Поиск файлов по критерию
    * Утилита для архивации папки
* <a href="https://github.com/inflatone/job4j/tree/master/chapter_005">Задания</a> блока _Структуры данных и алгоритмы_:
    * Сбор статистики изменений коллекций
    * Проверка символов слов на соответствие

#### Освоенные технологии в рамках данного курса:
* компоненты Java, структура элементарной программы, компиляция и запуск приложения в консоли, настройка среды разработки
* система управления версиями, сборка и тестирование проекта, дебаг и рефакторинг
* базовый синтаксис, управляющие конструкции, операторы и типы данных, аннотации и Javadoc
* классы и объекты
  * принципы ООП
  * виды отношений между классами
  * интерфейсы, лямбда-выражения и внутренние классы
  * правила реализации и контракты между equals() и hashCode()
* исключения: типы, их обработка и использование в программе, обеспечение fail-fast/fail-safe поведения объектов
* построение в Java классических структур данных (динамический массив, связный список, стеки и очереди, ассоциативный массив, деревья, упорядоченное и неупорядоченное множества), их итераторов и алгоритмов сортировки, анализ алгоритмической сложности
* обобщённое программирование, реализация дженериков в Java, стирание типов при компиляции, ковариантность и контравариантность
* многопоточность
  * виды памяти в Java и устройство JMM
  * запуск и завершение работы нитей, их взаимодействие
  * нарушение видимости общих объектов (shared objects) и синхронизация доступа к ним (thread-safety)
  * проблемы многопоточности (race condition, deadlock, livelock, thread starvation)
* использование высокоуровневых утилит параллельности
  * реализация потокобезопасных структур данных (динамический массив, блокирующая и неблокирующая очереди) и особенности работы их итераторов (concurrent collections)
  * механизм работы исполнителей потоков и основы планирования выполнения асинхронных задач (executor framework)
  * гибкие средства синхронизации работы нитей (locks + synchronizers)
  * оптимизация неатомарных операций с помощью compare-and-swap (atomics)
* потоки ввода-вывода и сокеты
* работа с SQL в Java
  * настройка PostgreSQL
  * ключи (primary, foreign), запросы, фильтры, join'ы
  * нормализация БД
  * JDBC, Connection, Transactions, rollback
  * in-memory БД (SQLite, HSQLDB)
* структура клиент-серверных приложений
  * схема прав пользователя и роли
  * Servlet API: контейнер сервлетов, JSP, JSTL
  * реализация web-приложения с разбиением на слои и применением паттерна MVC
  * основы JS, AJAX, JSON, jQuery, Bootstrap

##### Шаблоны проектирования:
  * порождающие (Builder, Factory method и Abstract factory, Singleton, Lazy initialization и Holder)
  * структурные (Adapter, Decorator, Proxy)
  * поведенческие (Command, Iterator, Null Object, Strategy, Template method)
  * параллельного программирования (Producer–consumer и Thread pool)

##### Инструменты разработки:
  * Maven
  * Git
  * IntelliJ IDEA
  * TrackStudio

##### Плагины и сторонние библиотеки:
  * jUnit
  * Log4j 2
  * Checkstyle
  * JaCoCo
  * Travis CI
  * Mockito
  * JCIP annotations
  * Maven Shade