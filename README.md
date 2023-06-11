# "Online Shop": multi-module Android application

- [ ] Dagger 2
- [ ] Multi api-impl Gradle modules 
- [ ] Clean Architecture
- [ ] Jetpack Navigation with deep-links
- [ ] MVVM for presentation layer
- [ ] Work Manager and Kotlin Coroutines for async work
- [ ] Retrofit to get products list with API
- [ ] Room to store products and 

Functionality

- [ ] List of products
- [ ] Details of the product
- [ ] Add new product
- [ ] Add to cart

Architecture

- [ ] Two types of modules are used: module-api - for interfaces, module-impl - for implementations
- [ ] Features are divided into layers in accordance with the principles of Clean Architecture: data, domain, presentation
- [ ] Dagger 2 used for dependencies injection
- [ ] MVVM pattern used for presentation layer 
- [ ] Navigation with deep links

Scheme of modules

- [ ] App
- [ ] Core
    - [ ] Navigation (api + impl)
    - [ ] Database (api + impl)
    - [ ] Network (api + impl)
    - [ ] Utils
    - [ ] Resources
- [ ] Feature (no-UI modules)
    - [ ] 1. Product (api + impl) (product description)
    - [ ] 2. Product-In-List (api + impl) (list item)
- [ ] UI-Feature (depends on Feature-modules )
    - [ ] Products list (api + impl) (depends on 2 feature)
    - [ ] PDP (api + impl) (depends on 1 feature)
    - [ ] Add product (api + impl) (depends on 1 and 2 features)


