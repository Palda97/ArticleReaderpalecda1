# ArticleReader by palecda1

## Rychlozasvětsení do balíčku Article

### Article
Reprezentuje jeden článek. Obsahuje tělo a adresu.

### ArticleSupplier
Je interface pro kontejner poskytující články. Obsahuje proto metody arrayOfArticles a articleById.

### DataStorage
Implementuje ArticleSupplier. Poskytne 10 hardcodovaných článků.

### MyStorage
Singleton ukazující na implementaci ArticleSupplieru.