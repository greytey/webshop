CREATE TABLE IF NOT EXISTS item(
    id SERIAL,
    productname VARCHAR(255),
    description BLOB,
    price FLOAT,
    code VARCHAR(255),
    fileType VARCHAR(100),
    codeText BLOB,
    length VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS user(
    id SERIAL,
    firstname VARCHAR(100),
    lastname VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100),
    address VARCHAR(255),
    zip VARCHAR(100),
    city VARCHAR(100),
    verificationCode INT(8),
    isVerified BIT
);

CREATE TABLE IF NOT EXISTS comment(
    id SERIAL,
    displayname VARCHAR(100),
    createdate VARCHAR(100),
    commenttext BLOB,
    productid INT
);

INSERT INTO item(productname, description, price, code, fileType, codeText, length) VALUES 
("CollectionView mit SwipeElement", 
"Eine CollectionView für das .NET MAUI Framework auf C#, vobei man in einer Liste (CollectionView) nach links swipen kann, um mehr Aktionen zu sehen wie als Favorite markieren
oder das Item aus der Liste zu löschen. In unserem Code nicht included ist eine Beispielliste und die Methoden löschen und favorisieren", 8.99, ".MAUI View", "xaml", 
"<CollectionView x:Name='collectionView'
        ItemsSource='{Binding collectionList}'>
    <CollectionView.ItemTemplate>
        <DataTemplate>
            <SwipeView>
                <SwipeView.LeftItems>
                    <SwipeItems>
                        <SwipeItem Text='Favorite'
                                   IconImageSource='favorite.png'
                                   BackgroundColor='LightGreen'
                                   Invoke='favorisieren()'/>
                        <SwipeItem Text='Delete'
                                   IconImageSource='delete.png'
                                   BackgroundColor='LightPink'/>
                    </SwipeItems>
                </SwipeView.LeftItems>
                <Grid BackgroundColor='White'
                      Padding='10'>
                    <!-- Define item appearance -->
                </Grid>
            </SwipeView>
        </DataTemplate>
    </CollectionView.ItemTemplate>
</CollectionView>", "10 Boxen"),
    ("Random Number Generator", "Random Number Generator in C#, die Klasse Random muss dabei importiert werden", 5.5, "C#", "cs", "Random rnd = new Random();
    int random  = random.Next(intFrom, intTo + 1);", "2 lines"),
    ("Foreach Listiterator", "Ein Listiterator für C#, der durch jedes Item einer Liste durchget und das Item mit der ToString-Methode ausgibt",
    4.8, "C#", "cs", "foreach (datatype name in listToIterateThrough)
    {
        Console.Write(name.ToString());
    }", "2 lines");

INSERT INTO comment VALUES
("Quickltey", "15.12.2022", "Diese Code Teile sind einfach spitze. Sie haben mir seh geholfen. Ich werde langsam richtig professionell!", 1),
("GreyForrest", "15.12.2022", "Diesen Code hier zu kaufen macht doch keinen sinn. Im Internet finde ich den gelichen Code gratis!", 2),
("Dels", "15.12.2022", "Mir gefällt der Kommentarbereich. Man müsste nur noch auf Kommentare antworten könne, dann wäre er perfekt!", 3);