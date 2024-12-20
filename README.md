# Рецепты на каждый день

## Описание

**Рецепты на каждый день** — это интуитивно понятное и удобное приложение, созданное для всех любителей кулинарии,
которые хотят разнообразить свое меню и готовить вкусные блюда. Наша цель — сделать процесс приготовления пищи простым и
увлекательным, предоставляя пользователям доступ к множеству рецептов на любой вкус и случай.

## Основные функции

- **Широкий выбор рецептов:** Исследуйте тысячи рецептов, от классических блюд до современных кулинарных трендов. Каждый рецепт включает подробные инструкции и список необходимых ингредиентов.
- **Избранные рецепты:** Сохраняйте любимые рецепты в разделе "Избранные", чтобы иметь к ним быстрый доступ.
- **Выбор количества порций:** Легко выбирайте количество порций, и приложение автоматически пересчитает необходимые ингредиенты, чтобы вы могли готовить как для себя, так и для большой компании.


## Проект разработан с использованием следующих технологий:

- **Язык программирования**: Kotlin
- **Android SDK**: Основная платформа для разработки мобильного приложения.
- **RecyclerView**: Компонент для отображения списков и сеток с возможностью динамического обновления данных.
- **SeekBar**: Элемент пользовательского интерфейса для выбора значения из диапазона.
- **Single Activity**: Архитектурный подход, при котором приложение состоит из одной активности, а все экраны реализованы с помощью фрагментов.
- **Fragment**: Компонент пользовательского интерфейса, который можно повторно использовать в различных активностях.
- **Архитектура MVVM**: Приложение организовано по архитектурному шаблону Model-View-ViewModel, что способствует разделению логики и представления.
- **LiveData**: Используется для передачи и отслеживания состояния между фрагментами и ViewModel, обеспечивая реактивное обновление интерфейса.
- **AndroidViewModel**: Подкласс ViewModel, который предоставляет доступ к контексту приложения, что полезно для работы с SharedPreferences и другими компонентами, требующими контекст.
- **Jetpack Navigation**: Библиотека для упрощения навигации между фрагментами и управления стеком навигации.
- **OkHttp**: Библиотека для выполнения сетевых запросов и работы с HTTP.
- **Retrofit**: Библиотека для упрощения работы с RESTful API, использующая OkHttp для выполнения сетевых запросов.
- **Glide**: Библиотека для загрузки и кэширования изображений, упрощает работу с изображениями в приложении.
- **Kotlin Coroutines**: Механизм для работы с асинхронным кодом, позволяющий писать более чистый и понятный код для выполнения фоновых задач.
- **Jetpack Room**: Библиотека для работы с локальной базой данных, обеспечивающая удобный доступ к данным и управление ими с помощью объектов.
- **Dependency Injection (Hilt)**: Библиотека для внедрения зависимостей, упрощает управление зависимостями и улучшает тестируемость приложения.

## Установка
