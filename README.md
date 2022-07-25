# Market

Стек

- [ ] Dagger 2
- [ ] Multi-modules
- [ ] Clean Architecture
- [ ] Jetpack Navigation
- [ ] MVVM для presentation-слоя
- [ ] Kotlin Coroutines для реализации асинхронности

Функционал приложения

- [ ] Просмотр списка товаров
- [ ] Просмотр карточки товара
- [ ] Создание нового товара
- [ ] Кеширование товаров в базу данных Room
- [ ] Счетчик просмотров карточки товара

Архитектура

- [ ] Для разделения кода используются модули двух типов: module-api - для интерфейсов, module-impl - для реализаций
- [ ] Фичи внутри разбиваются на слои в соответствии с принципами Clean Architecture: data, domain, presentation
- [ ] Для предоставления зависимостей используется Dagger 2
- [ ] В слое presentation используется MVVM
- [ ] Deep links для навигации

Схема модулей

- [ ] App
- [ ] Core
    - [ ] Navigation (api + impl)
    - [ ] Database (api + impl)
    - [ ] Network (api + impl)
    - [ ] Utils
    - [ ] Resources
- [ ] Feature (no-UI modules)
    - [ ] 1. Product (api + impl) (подробное описание товара)
    - [ ] 2. Product-In-List (api + impl) (товар из списка)
- [ ] UI-Feature (зависят от Feature )
    - [ ] Products list (api + impl) (depends on 2 feature)
    - [ ] PDP (api + impl) (depends on 1 feature)
    - [ ] Add product (api + impl) (depends on 1 and 2 features)


