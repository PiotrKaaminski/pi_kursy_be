# Główny opis
## Użytkownicy
### Zakładanie użytkownika z rolą ADMIN/TEACHER
Użytkownik z taką rolą może założyć jedynie admin<br>
podajemy dane użytkownika oraz hasło, użytkownik jest na statusie TEMPORARY_PASSWORD_SET<br>
po zalogowaniu na nowego użytkownika używając tymczasowego hasła mamy jedynie uprawnienia do modifyPassword<br>
należy zmienić hasło i ustawić jakieś własne<br>
od teraz można korzystać z nowego użytkownika

### Zakładanie użytkownika z rolą STUDENT
Każdy zakłada konto samemu sobie<br>
podajemy dane użytkownika wraz z hasłem, po założeniu konta można się na nie zalogować

### Uprawnienia
#### ADMIN
* zakładania kont innym użytkownikom z rolą ADMIN lub TEACHER
* zarządzanie użytkownikami (usuwanie, przeglądanie, modyfikacja)
* dodawanie nowych kategorii kursów
* dodawanie nowych kursów **(treść kursów dodaje tylko TEACHER)**
* podgląd wszystkich kursów
* podgląd statystyk wszystkich kursów
* przypisanie kursu do użytkownika TEACHER
#### TEACHER
* zarządzanie treścią kursów, do których jest przypisany
* podgląd statystyk kursów, do których jest przypisany
* podgląd wszystkich kursów
#### STUDENT
* przegląd dostępnych kursów
* wykup kursu
* dostęp do zawartości wykupionych kursów
# TODO
* poprawa obsługi błędów, zwracanie prawidłowych struktur
* uprzywilejowany użytkownik na statusie PENDING ma uprawnienia jedynie do zmiany hasła

* admin może zmieniać tytuł, cenę, kategorie oraz nauczyciela kursu (patch, aktualizowane są tylko wartości nie nullowe)
* dodanie modyfikacji kursu, teacher nadaje opis 
* dodanie sekcji do kursu (tytuł, opis)
* dodanie filmu do kursu