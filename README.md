Eco-Trace: Sürdürülebilirlik Analiz Sistemi
Bu proje, barkod tarama yoluyla ürünlerin çevresel etkilerini analiz eden ve daha yeşil alternatifler sunan Full-Stack bir backend uygulamasıdır.

Temel Mühendislik Özellikleri
Katmanlı Mimari: Proje; Controller, Service ve Repository katmanlarına ayrılarak, iş mantığı (business logic) ve veri erişimi birbirinden tamamen izole edilmiştir.

REST API Entegrasyonu: Open Food Facts API ile entegre çalışarak, yerel veritabanında bulunmayan ürünlerin verilerini anlık olarak çeker ve işler.

Skorlama Algoritması: Karbon salınımı, su ve enerji tüketimi gibi metrikleri baz alan, 0-100 arası çevresel puan üreten özgün bir matematiksel modelleme içerir.

Akıllı Öneri Motoru: Taranan ürünün puanına göre aynı kategorideki daha sürdürülebilir alternatifleri dinamik olarak listeler.

Oyunlaştırma: Kullanıcıların çevreci tercihlerini takip eden ve profil bazlı "Eco-Puan" kazandıran bir sistem sunar.

Teknoloji Yığını
Backend: Java 17, Spring Boot 3.x, Spring Data JPA

Veritabanı: H2 Database (In-Memory)

Frontend: HTML5, CSS3, JavaScript (Fetch API)

## Kurulum ve Çalıştırma

Projeyi yerel bilgisayarınızda çalıştırmak için iki yöntem bulunmaktadır:

### 1. Yöntem: Doğrudan Çalıştırma (Hızlı Test)
Java 17+ yüklü olan sistemlerde, projeyi derlemeden direkt çalıştırabilirsiniz:
1. `/target` klasöründeki `.jar` dosyasını indirin.
2. Terminalden şu komutu girin: `java -jar demo-0.0.1-SNAPSHOT.jar`
3. Tarayıcınızdan `http://localhost:8080/index.html` adresine gidin.

### 2. Yöntem: Geliştirici Modu (Kaynak Koddan)
1. Projeyi klonlayın: `git clone https://github.com/KULLANICI_ADIN/eco-trace.git`
2. Maven bağımlılıklarını yükleyin: `mvn clean install`
3. `DemoApplication` sınıfını çalıştırın.