# Projektdokumentation codelineway Sequel

By Delia Reho, Melanie Völlmin & Joel Jütte


![Logo](codelinewayLogoOnlyClear.png)

| **Datum** | **Version** | **Änderung** | **Autor** |
| --- | --- | --- | --- |
| 03.11.2022 | 1.1 | Initial I P E | Joel Jütte |
| 10.11.2022 | 1.2 | Start R | Melanie Völlmin |
| 17.11.2022 | 1.3 | Fokus Kommentarbereich | Delia Reho |
| 24.11.2022 | 1.4 | Fokus Sicherheit und Aufräumen | Joel Jütte |
| 01.12.2022 | 1.5 | Alles funktioniert und sieht gut aus | Melanie Völlmin |
| 08.12.2022	| 1.6 |	Schlussspurt / Hosting	| Delia Reho |
| 15.12.2022 |	2.0 |	Portfolio / Testen |	Joel Jütte |


## 1 Informieren

### 1.1 Projekt

Wir wollen unseren Webshop erweitern. Dabei möchten wir gerne die Registrierung und Anmeldung vom letzten Mal fertig realisieren. Die Benutzer sollen Kommentare zu den Produkten hinterlassen können. Ebenfalls soll die Adresse beim ersten Check-Out in der Datenbank gespeichert werden und ab dort automatisch ausgefüllt werden. Der Webshop soll gehostet werden, wobei auf die Sicherheit der Daten geachtet werden muss. Eventuelle weitere Features/Erweiterungen wären:

- Profil-Seite, wo man Dinge ändern kann
- Bewertungen mit Sternen
- ~~Echte Zahlungsanbindung (Twint, Debit- und Kreditkarte, PayPal, etc.)~~
- Webshop responsive gestalten (für Handy, Tablet)
- Webshop in eine App verwandeln

Im Bezug auf die Sicherheit möchten wir gerne die Leistungsziele 19, 22 und 24 des Moduls 183 «Applikationssicherheit» beachten.

**Leistungsziel 19** fordert, dass Passwörter, die in Datenbanken gespeichert werden, korrekt gehasht werden müssen. Da wir unsere Benutzer und deren Daten in einer Datenbank speichern, wollen wir die Passwörter der Benutzer hashen und salten.

Im **Leistungsziel 22** geht es darum, Eingaben zu validieren und Codemanipulation zu verhindern. Die sogenannte SQL-Injektion zum Beispiel wird genutzt, um ohne Passwort in einen beliebigen Useraccount zu kommen. Solche Gefahren wollen wir verhindern, in dem wir einerseits im Code vorsichtig mit den uns gegebenen Daten umgehen und andererseits die Daten validieren, bevor wir sie weiterverarbeiten. So wird ein Passwort zuerst auf Abstände und problematische Zeichen geprüft, bevor es in der Datenbank weitergenutzt wird.

Beim **Leistungsziel** 24 handelt es sich um das Sessionhandling. Wenn ein Benutzer die Website aufruft, bekommt er eine Session. Dazu gehört auch eine Session-ID. Wenn diese öffentlich zugänglich ist, kann ein Mensch mit bösen Absichten einem Benutzer einen Link mit der eigenen Session-ID schicken. Wenn sich dieser einloggt sich, kann der Mensch mit bösen Absichten durch den Link mit der Session-ID auf die Website angemeldet als der Benutzer zugreifen. In unserem Falle macht das eigentlich kein grossen Unterschied, da man damit nicht wirklich Daten stehlen kann. Trotzdem möchten wir unsere Benutzer schützen, in dem die Session-ID nicht öffentlich angezeigt wird.


### 1.2 Quellen

- [CSS costum properties (variables)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)
- [Spring Applikation ausführen](https://www.jetbrains.com/help/idea/your-first-spring-application.html#run-spring-application)
- [Full Course – Spring Boot Tutorial](https://www.youtube.com/watch?v=9SGDpanrc8U)
- [Relationale Daten mit Spring](https://spring.io/guides/gs/relational-data-access)
- [Spring Boot JDBC](https://www.javatpoint.com/spring-boot-jdbc)
- [Spring JDBC Template Querying Beispiele](https://mkyong.com/spring/spring-jdbctemplate-querying-examples/)
- [Tutorial für automatisch generierte E-Mail](https://netcorecloud.com/tutorials/send-email-in-java-using-gmail-smtp/)
- [Textarea verändert Grösse nach Inhalt](https://youtu.be/s-sYzWPDTP8)
- [Bootstrap Form](https://getbootstrap.com/docs/4.0/components/forms/?)
- jQuery zu [Springboot](https://www.codejava.net/frameworks/spring-boot/add-bootstrap-and-jquery-in-a-spring-boot-project) hinzufügen
- [Test ob String eine Nummer enthält](https://www.tutorialspoint.com/Check-if-a-string-contains-a-number-using-Java#:~:text=To%20find%20whether%20a%20given,method%20of%20the%20Character%20class.)
- [Dateien erstellen und hinein schreiben](https://www.w3schools.com/java/java_files_create.asp)
- [Datei im Mail Anhang hinzufügen](https://www.baeldung.com/java-send-emails-attachments)

### 1.3 Anforderungen

| **Nummer** | **Muss / Kann?** | **Funktional? Qualität? Rand?** | **Beschreibung** |
| --- | --- | --- | --- |
| 1 | Muss | Funktional | Produkte im Warenkorb können entfernt werden |
| 2 | Muss | Funktional | Um ins Check-out zu gelangen muss man eingeloggt sein. |
| 3 | Muss | Funktional | Ein Benutzer kann sich mit Namen, E-Mail und Passwort registrieren. |
| 4 | Muss | Funktional | Ein registrierter Benutzer kann sich mit E-Mail und Passwort anmelden. |
| 5 | Muss | Funktional | Alle Eingaben werden auf Gefahren überprüft. |
| 6 | Muss | Funktional | Es gibt eine Password Policy. |
| 7 | Muss | Funktional | Die E-Mail wird validiert. |
| 8 | Muss | Funktional | Es gibt eine E-Mail-Verifikation. |
| 9 | Muss | Funktional | Um etwas zu kaufen, muss man verifiziert sein. |
| 10 | Muss | Funktional | Die Adresseingaben im ersten Check-Out eines neu registrierten Benutzers werden in der Datenbank gespeichert. |
| 11 | Muss | Funktional | Die Adresseingaben im Check-Out werden automatisch befüllt, wenn die Daten in der Datenbank vorhanden sind. |
| 12 | Kann | Funktional | Der Benutzer kann online bezahlen. |
| 13 | Muss | Funktional | Die versendete E-Mail enthält den Code als Datei im Anhang. |
| 14 | Muss | Funktional | Es gibt ein Kommentarbereich auf der Produktseite. |
| 15 | Muss | Funktional | Allen Benutzern werden die Kommentare von anderen Benutzern angezeigt. |
| 16 | Muss | Funktional | Nur eingeloggte Benutzer können Kommentare schreiben. |
| 17 | Muss | Rand | OneDrive wird als Speicherort verwendet. |
| 18 | Muss | Rand | Jeden Halbtag wird das Projekt und die Dokumentation Auf GitHub gepusht. |
| 19 | Muss | Rand | Die Webapplikation wird gehostet. |
| 20 | Muss | Rand | Die Datenbank für die Webapplikation wird online gehostet, so dass alle Mitentwickler damit arbeiten können. |
| 21 | Muss | Rand | Das Projekt muss bis am 15.12.2022 fertig sein. |

### 1.4 Diagramme

## 2 Planen

### 2.1Arbeitspakete/To-Do

| **Nr.** | **Frist** | **Beschreibung** | **Person** | **Zeit (geplant)** | **Zeit (effektiv)** |
| --- | --- | --- | --- | --- | --- |
| 1.1 | 03.11.2022 | Dokumentation erstellen und IPE ausfüllen | Alle | 90 | 120 |
| 1.2 | 03.11.2022 | Mögliche Verbindungen zu Online-Datenbank recherchieren | Melanie | 45 | 50+ |
| 1.3 | 03.11.2022 | Online-Datenbank einrichten (Tables) und mit bestehendem Webshop verbinden | Melanie | 45 | 35 |
| 1.4 | 03.11.2022 | Überarbeitung der Login Seite und erstellen der Verifikation Seite | Joel | 45 | 55 |
| 1.5 | 03.11.2022 | Informieren wie man JavaScript (jQuery) mit Springboot verwendet. jQuery für die Login Seite schreiben. | Joel | 45 | 25 |
| 1.6 | 03.11.2022 | GUI des Kommentarbereichs entwerfen | Delia | 45 | 30 |
| 1.7 | 03.11.2022 | GUI des Kommentarbereichs implementieren | Delia | 45 | 60 |
| 1.8 | ~~03.11.2022~~ 10.11.2022 | Dokumentation fertigstellen und gemeinsam mit aktuellen Projektstand auf dem bereits vorhandenen GitHub-Repository hochladen | Alle | 45 | 20 |
| 2 ||||||
| 2.1 | 10.11.2022 | Wöchentliches HDKOM\* | Alle | 22 | 30 |
| 2.2 | 10.11.2022 | Login mit Datenbank verbinden | Joel | 45 | 55 |
| 2.3 | 10.11.2022 | Validierung von Eingabe und Password Policy Implementieren | Joel | 45 | 55 |
| 2.4 | ~~10.11.2022~~ 17.11.2022 | Login auf der Webpage implementieren | Joel | 90 | 45 + 85 |
| 2.5 | 10.11.2022 | GUI des Kommentarbereichs fertigstellen | Delia | 90 | 30 |
| 2.6 | ~~10.11.2022~~ 17.11.2022 | Funktionalität des Kommentarbereichs umsetzen
 | Delia | 90 | 120 |
| 2.7 | 10.11.2022 | Warenkorb-Feature «entfernen» hinzufügen | Melanie | 45 | 60 |
| 2.8 | 10.11.2022 | Check-Out Formular anpassen/ Pflichtcheckbox hinzufügen | Melanie | 45 | 25 |
| 2.9 | ~~10.11.2022~~ 17.11.2022 | Autofill Check-Out Formular vorbereiten | Melanie | 45 | 20+75 |
| 2.10 | 10.11.2022 | Reserve | Melanie | 45 | 75\*\* |
| 2.11 | 10.11.2022 | Dokumentation nachführen und mit dem Projekt auf GitHub pushen | Alle | 22 | 20 |
| 3 ||||||
| 3.1 | 17.11.2022 | Wöchentliches HDKOM\* | Alle | 22 | 30 |
| 3.2 | 17.11.2022 | Besprechung und Hilfestellung Kommentarbereich | Alle | 45 | 45 |
| 3.3 | 17.11.2022 | Einrichtung des MySQL Workbench Servers, um das Projekt auf meinem Gerät starten zu können | Joel | 45 | 10 |
| 3.4 | 17.11.2022 | «Nicht gefunden» Anzeige erstellen | Joel | 30 | 30 |
| 3.5 | 17.11.2022 | XSS in der Suchfunktion verhindern | Joel | 60 | 40 |
| 3.6 | 17.11.2022 | Fehlertesting: Produkt mehr als einmal in den Warenkorb hinzufügen | Melanie | 25 | 10 |
| 3.7 | ~~17.11.2022~~ 24.11.2022 | Alle Links im Header funktionierend machen | Melanie | 20 | 20 |
| 3.8 | 17.11.2022| Hilfestellung Comments | Melanie | 45 | 45 |
| 3.9 | ~~17.11.2022~~ 24.11.2022 | CSS für Warenkorb und CheckOut anpassen | ~~Melanie~~ Joel | 20 | 25 |
| 3.10 | ~~17.11.2022~~ 24.11.2022 | Funktionalität Kommentare senden | Delia | 60 | 50+ |
| 3.11 | 17.11.2022 | Funktionalität Kommentare anzeigen | Delia | 45 | 60 |
| 3.12 | 17.11.2022 | Funktionalität Textarea-Erweiterung bei Input | Delia | 30 | 40 |
| 3.13 | 17.11.2022 | CSS für Comment Section anpassen | Delia | 15 | 10 |
| 3.14 | 17.11.2022 | Dokumentation nachführen und mit dem Projekt auf GitHub pushen | Alle | 22 | 15 |
| 4 ||||||
| 4.1 | 24.11.2022 | Wöchentliches HDKOM\* | Alle | 22 | 10 |
| 4.2 | 24.11.2022 | XSS-Testen und allenfalls verhindern | ~~Joel~~ Melanie | 22 | 15 |
| 4.3 | 24.11.2022 | SQL-Injektion verhindern Zusatz: Exceptions werfen für Klarheit | Joel / Melanie | 45 | 30 |
| 4.4 | 24.11.2022 | CSS aufräumen | Joel | 22 | 15 |
| 4.5 | 24.11.2022 | Formulare optisch überarbeiten | Joel | 45 | 15 |
| 4.6 | 24.11.2022 | Code Datei (Produkt) in die DB einbinden | Joel | 45 | 45 |
| 4.7 | 24.11.2022 | Error Seite erstellen | Melanie | 45 | 30 |
| 4.8 | 24.11.2022 | Error Seite implementieren | Melanie | 22 | 5 |
| 4.9 | ~~24.11.2022~~ 1.12.2022 | Bestellbestätigungsemail anpassen + Fileattachment hinzufügen | Melanie / Joel | 45+60+45 | 60+45 |
| 4.10 | ~~24.11.2022~~ 1.12.2022 | Aktuelles Datum anzeigen bei Kommentaren | Delia | 45 | 40 |
| 4.11 | ~~24.11.2022~~ 1.12.2022 | Funktionalität Kommentare senden | Delia | 60 | 155 |
| 4.12 | ~~24.11.2022~~ 1.12.2022 | Funktionalität mehrere Kommentare anzeigen | Delia | 45 | 15 |
| 4.13 | ~~24.11.2022~~ 1.12.2022 | Profil login/logout | Melanie | 60 | 30+75 |
| 4.13 | 24.11.2022 | Dokumentation nachführen und mit dem Projekt auf GitHub pushen | Alle | 22 | 25 |
| 5 ||||||
|5.1|1.12.2022|Wöchentliches HDKOM\*|Alle|22 |20|
|5.2|1.12.2022|Verifikation implementieren -> Datenbank / Java Code|Joel|60|30|
|5.3|1.12.2022|Verifikation implementieren -> Zugriffsverweigerungen |Joel|60|30+90|
|5.4|1.12.2022|CSS verbessern |Joel|45|30+5|
|5.5|1.12.2022|Informierung über Online-Shop-Bezahlung (API?)|Melanie|30|15|
|5.6|1.12.2022|Informieren ~~und Umsetzung~~ Layout-Templates Springboot|Melanie|45|45+30|
|5.7|~~1.12.2022~~ 08.12.2022|Hilfestellung Kommentare Delia|Melanie|45+30|40+45|
|5.8|1.12.2022|Funktionalität Kommentare senden|Delia|90|90+|
|5.9|1.12.2022|Kommentare von anderen Benutzern werden angezeigt|Delia|90|90+|
|5.10|1.12.2022|Datum, wann der Kommentar gepostet wurde, wird beim Kommentar angezeigt|Delia|45|20|
|5.11|1.12.2022|Preisanzeige anpassen (5.50 statt 5.5)|Melanie|25|35|
|5.12|1.12.2022|Dokumentation nachführen und mit dem Projekt auf GitHub pushen|Alle |22|20|
|6.1|08.12.2022|Wöchentliches HDKOM*|Alle|22 |22|
|6.2| ~~08.12.2022~~ 15.12.2022|Überarbeitung Dokumentation|Melanie|25|45|
|6.3|08.12.2022|Informieren und Umsetzung Benutzerdaten hashen und salten|Melanie|60|30|
|6.4| ~~08.12.2022~~ 15.12.2022|Informieren ~~und Umsetzung~~ von Website hosten|Melanie|60|60+45+45|
|6.5|08.12.2022|Verifikation implementieren  Mail versenden|Joel|22|22|
|6.6|08.12.2022|Diverses (Hier und da etwas machen / helfen)|Joel|/|/|
|6.7|08.12.2022|Überarbeitung Dokumentation|Delia|60|60|
|6.8|08.12.2022|Fertigstellung Kommentarbereich|Delia|75|75|
|6.9|08.12.2022|Dokumentation nachführen und mit dem Projekt auf GitHub pushen|Alle |22|22|
|7.1|15.12.2022|Wöchentliches HDKOM*|Alle|22 |22|
|7.2|15.12.2022|Testen|Melanie|45|30|
|7.3|15.12.2022|Portfolio|Melanie|45|60|
|7.4|15.12.2022|Ergänzung Dokumentation|Joel|45|40|
|7.5|15.12.2022|Portfolio|Joel|135|140|
|7.6|15.12.2022|Ergänzungen Dokumentation|Delia|45|35|
|7.7|15.12.2022|Portfolio|Delia|135|145|
|7.8|15.12.2022|Dokumentation nachführen und mit dem Projekt auf GitHub pushen|Alle |22|22|


**\*HDKOM** = **H** ead **D** eveloper **K** ick- **O** ff **M** eeting (Zweck: Besprechung des Projektstands und den Plänen für diese Woche)
_alternativ__:_ _HDMI = Head Developer_ _Management_ _Interchange_

\*\*verbraucht wegen Problemen mit OneDrive, Probleme mit Code-Sharing und da Projekt nicht gestartet werden konnte

### 2.2 Milestones

| Datum | Milestone |
| --- | --- |
| 03.11.2022 | IPE abschliessen |
| ~~10.11.2022 | Projekt läuft auf allen Notebooks der Gruppenmitglieder~~ |
| ~~17.11.2022 | Webpage ist gehostet~~ |
| 24.11.2022 | Check-Out funktioniert |
| 08.12.2022 | Kommentierbereich funktioniert |

## 3 Entscheiden

Wie wird die Password Policy gestaltet?
Die Passwörter sollen mindestens 8 Zeichen beinhalten, darunter mindestens eine Zahl und mindestens einen Buchstaben. Mindestens ein Buchstabe muss grossgeschrieben werden. Leerzeichen im Passwort sind nicht erlaubt.

Wie stellen wir den Code auf der Item Page dar?
Da wir den Code nicht als Bild darstellen können, werden wir einen Screenshot eines Codes überall als Filler auf die Items-Pages klatschen und in der Shopübersicht sind die Bilder einfach das Logo unseres Projekts.

Welche Farben wollen wir benutzen?
Wir wollen unsere Website ähnlich wie Programmiercode in einer IDE gestalten. Der Hintergrund soll grau/schwarz sein, die Schrift weiss. Es gibt einige farbige Akzente, die in Blautönen gehalten sind, wie unser Logo. Folgendes sind die gewählten Farben mit Hex Code:

| **Farbe** | **Verwendung** | **Hex Code** |
| --- | --- | --- |
| Schwarzgrau | Hintergrund | #3b3b3b |
| Helleres Schwarzgrau | Akzente über Hintergrund | #525252 |
| Weissgrau | Schrift | #F9F6EE |
| Blauer Akzent 1 | Akzente für Titel | #7DF9FF |
| Blauer Akzent 2 | Akzente für wichtige Abschnitte | #6495ED |
| Blauer Akzent 3 | Akzente für Navbar und Suche | #0047AB |

## 4 Realisieren

Sehr stockend velaufen

## 5 Kontrollieren

Wir testen nur die Anforderungen, die erfüllt wurden und nicht zur Kategorie «_Rahmenbedingungen_» zählen:

| **Nummer** | **Beschreibung** | **Status** |
| --- | --- | --- |
| 1 | Produkte im Warenkorb können entfernt werden | Erfüllt |
| 2 | Um ins Check-out zu gelangen muss man eingeloggt sein. | Erfüllt |
| 3 | Ein Benutzer kann sich mit Namen, E-Mail und Passwort registrieren. | Erfüllt |
| 4 | Ein registrierter Benutzer kann sich mit E-Mail und Passwort anmelden. | Erfüllt |
| 5 | Alle Eingaben werden auf Gefahren überprüft. | Erfüllt |
| 6 | Es gibt eine Password Policy. | Erfüllt |
| 7 | Die E-Mail wird validiert. | Erfüllt |
| 8 | Es gibt eine E-Mail-Verifikation. | Erfüllt |
| 9 | Um etwas zu kaufen, muss man verifiziert sein. | Erfüllt |
| 10 | Die Adresseingaben im ersten Check-Out eines neu registrierten Benutzers werden in der Datenbank gespeichert. | Erfüllt |
| 11 | Die Adresseingaben im Check-Out werden automatisch befüllt, wenn die Daten in der Datenbank vorhanden sind. | Erfüllt |
| 12 | Der Benutzer kann online bezahlen. | Nicht erfüllt |
| 13 | Die versendete E-Mail enthält den Code als Datei im Anhang. | Teilweise erfüllt |
| 14 | Es gibt ein Kommentarbereich auf der Produktseite. | Erfüllt |
| 15 | Allen Benutzern werden die Kommentare von anderen Benutzern angezeigt. | Erfüllt |
| 16 | Nur eingeloggte Benutzer können Kommentare schreiben. | Erfüllt |
| 17 | OneDrive wird als Speicherort verwendet. | Erfüllt |
| 18 | Jeden Halbtag wird das Projekt und die Dokumentation Auf GitHub gepusht. | Teilweise erfüllt |
| 19 | Die Webapplikation wird gehostet. | Nicht erfüllt |
| 20 | Die Datenbank für die Webapplikation wird online gehostet, so dass alle Mitentwickler damit arbeiten können. | Nicht erfüllt |
| 21 | Das Projekt muss bis am 15.12.2022 fertig sein. | Teilweise erfüllt |

### 5.1 Testfälle

|Testfall Nr.|1.1|
|----|----|
|Getestete Anforderung |1|
|Testziel|Produkte können aus dem Warenkorb entfernt werden.|
|Voraussetzung |Webseite ist geöffnet und Produkte sind im Warenkorb|
|Eingabe|Benutzer klickt auf das X|
|Ausgabe |Produkt wird aus dem Warenkorb entfernt

|Testfall Nr.|2.1|
|----|----|
|Getestete Anforderung |2|
|Testziel|Um ins Check-out zu gelangen, muss man eingeloggt sein.|
|Voraussetzung |Webseite ist geöffnet |
| Eingabe | Benutzer ist nicht eingeloggt und will ins Check-out gehen. | Ausgabe | Benutzer kommt nicht auf die Check-out Seite.|

|Testfall Nr.|2.2|
|----|----|
|Getestete Anforderung |2|
|Testziel|Um ins Check-out zu gelangen muss man eingeloggt sein.|
|Voraussetzung |Webseite ist geöffnet|
|Eingabe |Benutzer ist eingeloggt und will ins Check-out gehen.|
|Ausgabe |Benutzer kommt auf die Check-out Seite.|

|Testfall Nr.|3.1|
|----|----|
|Getestete Anforderung |3|
|Testziel|Registrierung durch E-Mail und Passwort|
|Voraussetzung |Webseite ist geöffnet, noch kein User mit derselben E-Mail in der Datenbank vorhanden|
|Eingabe |1.Benutzer gibt E-Mail-Adresse ein. 2.Benutzer gibt Vor- und Nachname ein. 3.Benutzer gibt Passwort ein. 4. Benutzer gibt das Passwort erneut ein. 5. Benutzer klickt auf Registrieren.|
|Ausgabe |6.Benutzer wird registriert und eingeloggt.|

|Testfall Nr.|4.1|
|----|----|
|Getestete Anforderung |4|
|Testziel|Registrierte Benutzer können sich anmelden.|
|Voraussetzung |Webseite ist geöffnet, Benutzer ist registriert|
|Eingabe   |1.Benutzer gibt seine E-Mail-Adresse ein. 2.Benutzer gibt das Passwort ein.|
|Ausgabe| 3.Benutzer wird eingeloggt.|

|Testfall Nr.|5.1|
|----|----|
|Getestete Anforderung |5|
|Testziel|Eingaben werden auf Gefahren überprüft.|
|Voraussetzung |Webseite ist geöffnet|
|Eingabe   | Benutzer versucht eine XSS-Attacke im Suchfeld.|
|Ausgabe | Der Code wird nicht als Code behandelt, sondern als Eingabe dargestellt.| 

|Testfall Nr.|5.2|
|----|----|
|Getestete Anforderung |5|
|Testziel|Eingaben werden auf Gefahren überprüft.|
|Voraussetzung |Webseite ist geöffnet|
|Eingabe   |Benutzer versucht eine SQL-Injection in den Eingabefeldern.|
|Ausgabe |Die SQL-Injection funktioniert nicht.|

|Testfall Nr.|6.1|
|----|----|
|Getestete Anforderung |6|
|Testziel|Es gibt eine Password Policy.|
|Voraussetzung |Webseite ist geöffnet, Benutzer registriert sich|
|Eingabe   |1.Benutzer tätigt eine Eingabe, welche nicht der Password Policy entspricht. 2.Benutzer klickt auf Registrieren.
|Ausgabe |3.Benutzer wird nicht registriert.|

|Testfall Nr.|6.2|
|----|----|
|Getestete Anforderung |6|
|Testziel|Es gibt eine Password Policy|
|Voraussetzung |Webseite ist geöffnet, Benutzer registriert sich|
|Eingabe   |1. Benutzer tätigt eine Eingabe, welche der Password Policy entspricht. 2. Benutzer klickt auf Registrieren.|
|Ausgabe |3. Benutzer wird registriert.|

|Testfall Nr.|7.1|
|----|----|
|Getestete Anforderung |7|
|Testziel|Die E-Mail wird validiert.|
|Voraussetzung |Webseite ist geöffnet, Benutzer registriert sich.|
|Eingabe   |1. Benutzer gibt eine ungültige E-Mail ein. 2. Benutzer klickt auf Registrieren.|
|Ausgabe |3. Benutzer wird nicht registriert.|

|Testfall Nr.|7.2|
|----|----|
|Getestete Anforderung |7|
|Testziel|Die E-Mail wird validiert.|
|Voraussetzung |Webseite ist geöffnet, Benutzer registriert sich|
|Eingabe   |1. Benutzer gibt eine gültige E-Mail ein. 2. Benutzer klickt auf Registrieren.|
|Ausgabe |3. Benutzer wird registriert. |

|Testfall Nr.|8.1|
|----|----|
|Getestete Anforderung |8|
|Testziel|Es gibt eine E-Mail-Verifikation.|
|Voraussetzung |Webseite ist geöffnet|
|Eingabe   |Benutzer schliesst Registrierung erfolgreich ab.|
|Ausgabe | Benutzer erhält eine E-Mail um sich zu verifizieren.|

|Testfall Nr.|9.1|
|----|----|
|Getestete Anforderung |9|
|Testziel|Um etwas zu kaufen, muss man registriert sein.|
|Voraussetzung |Webseite ist geöffnet, Benutzer eingeloggt und nicht verifiziert|
|Eingabe   | Benutzer navigiert zum Check-out.|
|Ausgabe | Benutzer wird nicht weitergeleitet.|

|Testfall Nr.|9.2|
|----|----|
|Getestete Anforderung |9|
|Testziel|Um etwas zu kaufen, muss man registriert sein.|
|Voraussetzung |Webseite ist geöffnet, Benutzer eingeloggt und verifiziert|
|Eingabe   | Benutzer navigiert zum Check-out.|
|Ausgabe |Benutzer wird weitergeleitet.|

|Testfall Nr.|10.1|
|----|----|
|Getestete Anforderung |10|
|Testziel|Adresseingaben werden gespeichert.|
|Voraussetzung |Webseite ist geöffnet, Benutzer ist zum ersten Mal im Check-out|
|Eingabe   |1. Benutzer gibt die Benötigten Daten ein. 2. Benutzer klickt auf Bestellen.|
|Ausgabe |3. Die Benutzerdaten werden in der Datenbank gespeichert.|

|Testfall Nr.|11.1|
|----|----|
|Getestete Anforderung |11|
|Testziel|Falls vorhanden werden Adresseingaben automatisch ausgefüllt.|
|Voraussetzung |Webseite ist geöffnet, Benutzer hat bereits Adressdaten in der Datenbank.|
|Eingabe   |Benutzer navigiert ins Check-out.|
|Ausgabe |Adresseingaben werden automatisch befüllt.|

|Testfall Nr.|12.1|
|----|----|
|Getestete Anforderung |12|
|Testziel|Kauf-E-Mail enthält Dateien im Anhang.|
|Voraussetzung |Benutzer hat mindestens ein Produkt gekauft.|
|Eingabe   |Benutzer öffnet die erhaltende E-Mail.|
|Ausgabe |Die E-Mail enthält die bestellten Produkte als Anhang.|

|Testfall Nr.|13.1|
|----|----|
|Getestete Anforderung |13|
|Testziel|Es gibt einen Kommentarbereich auf der Produktseite.|
|Voraussetzung |Webseite ist geöffnet|
|Eingabe   |1. Benutzer klickt auf ein Produkt. 3. Benutzer scrollt nach unten.|
|Ausgabe | 2. Produktseite wird angezeigt. 4. Kommentarbereich wird angezeigt.|

|Testfall Nr.|14.1|
|----|----|
|Getestete Anforderung |14|
|Testziel|Alle Kommentare zum jeweiligen Produkt werden allen angezeigt.|
|Voraussetzung |Webseite ist geöffnet und eine Produktseite ist geöffnet|
|Eingabe   |Benutzer scrollt zum Kommentarbereich.|
|Ausgabe |Im Kommentarbereich werden Kommentare anderer Benutzer angezeigt.|

|Testfall Nr.|15.1|
|----|----|
|Getestete Anforderung |15|
|Testziel|Nur eingeloggte Benutzer können Kommentare schreiben.|
|Voraussetzung |Webseite ist geöffnet, Benutzer nicht eingeloggt.|
|Eingabe   |1. Benutzer schreibt ein Kommentar. 2. Benutzer sendet den Kommentar ab.|
|Ausgabe |3. Kommentar wird nicht abgesendet. 4. Kommentar wird nicht angezeigt.|

|Testfall Nr.|15.2|
|----|----|
|Getestete Anforderung |15|
|Testziel|Nur eingeloggte Benutzer können Kommentare schreiben.|
|Voraussetzung |Webseite ist geöffnet, Benutzer eingeloggt|
|Eingabe   |1. Benutzer schreibt ein Kommentar. 2. Benutzer sendet den Kommentar ab.|
|Ausgabe |3. Kommentar wird abgesendet. 4. Kommentar wird angezeigt.|


### 5.2 Testprotokoll

|Anlass des Testes |Evaluierung der Testresultate vom 15.12.2022, für die Abgabe des Projektes|
|----|----|
|System Hardware |Acer TravelMate P2|
|System Software |Windows 10 Pro Version 22h2|


|Test-Nr.|Testfall Nr.|Datum |Tester|Resultat|Bemerkung|
|----|----|----|----|----|----|
|1.1.1|1.1|15.12.2022|Melanie Völlmin|OK| |
|2.1.1|2.1|15.12.2022|Melanie Völlmin|OK| |
|2.2.1|2.2|15.12.2022|Melanie Völlmin|OK| |
|3.1.1|3.1|15.12.2022|Melanie Völlmin|OK| |
|4.1.1|4.1|15.12.2022|Melanie Völlmin|OK| |
|5.1.1|5.1|15.12.2022|Melanie Völlmin|OK| |
|5.2.1|5.2|15.12.2022|Melanie Völlmin|OK| |
|6.1.1|6.1|15.12.2022|Melanie Völlmin|OK| |
|6.2.1|6.2|15.12.2022|Melanie Völlmin|OK| |
|7.1.1|7.1|15.12.2022|Melanie Völlmin|OK| |
|7.2.1|7.2|15.12.2022|Melanie Völlmin|OK|Emails konnten teilweise mehr als einmal verwendet werden, teilweise nicht.|
|8.1.1|8.1|15.12.2022|Melanie Völlmin|NOK|Website ist nicht gehosted, also gibt es keinen zugänglichen Verifizierungslink|
|9.1.1|9.1|15.12.2022|Melanie Völlmin|OK| |
|9.2.1|9.2|15.12.2022|Melanie Völlmin|OK| |
|10.1.1|10.1|15.12.2022|Melanie Völlmin|OK| |
|11.1.1|11.1|15.12.2022|Melanie Völlmin|OK| |
|12.1.1|12.1|15.12.2022|Melanie Völlmin|OK| |
|13.1.1|13.1|15.12.2022|Melanie Völlmin|OK| |
|14.1.1|14.1|15.12.2022|Melanie Völlmin|OK| |
|15.1.1|15.1|15.12.2022|Melanie Völlmin|OK| |
|15.2.1|15.2|15.12.2022|Melanie Völlmin|OK| |

### 5.3 Testfazit

Das Projekt läuft, konnte aber leider nicht gehosted werden, was auch das Verifizieren «unmöglich» macht.

## 6 Auswerten

- _Siehe_ _Portfolios_
