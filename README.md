# ArticleReader by palecda1

## Rychlozasvětsení do UI

### Hlavní obrazovka
V menu (zprava) je ikona obnovení, která po kliknutí způsobí, že se začnou stahovat články z feedů a v telefonu se uloží do databáze, pro pozdější čtení.  
Následuje ikonka ve tvaru rss, tou se uživatel dostane do nastavení feedů.  
Po kliknutí na náhled článku se uživatel dostane na detail článku.
### Nastavení feedů
V menu (zprava) je ikona "+", kterou se přidávají nové feedy.  
Tím větrníčkem, co je vedle, se seznam feedů vrátí do původního stavu, v jakém byl po instalaci.  
Kliknutím na feed ho můžete upravit a dlouhým stiskem ho můžete vymazat.  
Po změně feedů se články na hlavní obrazovce nezmění do doby, než uživatel stiskne ikonku pro obnovení.
### Detail článku
Na obrazovce s detailem článku ho můžete sdílet, nebo kliknout na ikonu zeměkoule a otevře se webová stránka článku.

## Rychlozasvětsení do balíčku Article

### Article
Reprezentuje jeden článek. Obsahuje tělo a adresu.

### ArticleSupplier
Je interface pro kontejner poskytující články. Obsahuje proto metody arrayOfArticles a articleById.

### DataStorage
Implementuje ArticleSupplier. Poskytne 10 hardcodovaných článků.

### MyStorage
Singleton ukazující na implementaci ArticleSupplieru.