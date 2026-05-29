# Proje 4: E-Ticaret Uygulaması (Otomatik Ölçeklendirme ve Yönetim)

Bu belge, Java Spring Boot kullanarak geliştireceğimiz ve AWS üzerinde otomatik ölçeklenebilir bir yapıya kavuşturacağımız E-Ticaret projesi için bir yol haritası ve çözüm önerileri sunmaktadır.

## Kullanıcı İncelemesi ve Karar Gerektiren Konular

> [!IMPORTANT]
> **AWS Bakiye ve Maliyet Sorunu İçin Çözüm Önerileri**
> Hesabınızın bakiyesinin bittiğini belirttiniz. Bulut faturası ödemeden veya minimum maliyetle projeyi tamamlamak için aşağıdaki seçeneklerden birine karar vermemiz gerekiyor:
>
> 1. **AWS Yeni Hesap (Free Tier):** Tamamen yeni bir e-posta adresi ve kredi kartı ile yeni bir AWS hesabı açabilirsiniz. Bu size 12 aylık Ücretsiz Katman (Free Tier) sağlar. Bu katman kapsamında 750 saat/ay EC2 (t2.micro), 750 saat/ay RDS (Veritabanı) ve Elastic Load Balancer kullanım haklarınız olur. Projeyi ücretsiz tamamlarsınız.
> 2. **AWS Educate / GitHub Student Developer Pack:** Eğer üniversite öğrencisiyseniz (veya .edu uzantılı mailiniz varsa), GitHub Student Developer Pack üzerinden veya AWS Educate programına başvurarak kredi kartı girmeden ücretsiz AWS kredileri kazanabilirsiniz.
> 3. **Farklı Bir Bulut Sağlayıcısına Geçiş:** Proje kapsamında Azure veya Google Cloud da kullanılabiliyor. Google Cloud yeni üyelere 90 gün boyunca kullanılabilecek **$300 ücretsiz kredi** veriyor. Azure ise **$200 ücretsiz kredi** (veya öğrenciyseniz kredi kartsız $100) veriyor. AWS yerine GCP veya Azure'a geçerek projeyi tamamen ücretsiz kredilerle yapabiliriz.
> 4. **Lokal Simülasyon (Geliştirme Ortamı İçin):** Projeyi kendi bilgisayarımızda Docker, Nginx (Load Balancer olarak) kullanarak "sanki buluttaymış" ve ölçekleniyormuş gibi geliştirebilir, sadece en son sunum/teslim aşamasında buluta yükleyebiliriz.

## Açık Sorular

> [!WARNING]
> Lütfen aşağıdaki soruları yanıtlayın, böylece planı netleştirebilirim:
> 1. Yukarıdaki bulut platformu seçeneklerinden hangisi sizin için daha uygun? (Örn: "Google Cloud'un $300 kredisini kullanalım" veya "Yeni bir AWS hesabı açacağım")
> 2. Veritabanı olarak MySQL mi yoksa PostgreSQL mi tercih edersiniz? (İkisi de AWS RDS üzerinde destekleniyor, Spring Boot ile harika çalışıyor. Özel bir tercihiniz yoksa PostgreSQL önereceğim.)
> 3. E-Ticaret uygulamasında "hazır da kullanabilirsiniz" denmiş. Tamamen sıfırdan basit bir Spring Boot API + HTML/Thymeleaf veya React frontend mi yazalım, yoksa açık kaynak basit bir projeyi mi alıp bulut altyapısına uyarlayalım?

## Önerilen Mimari (AWS Üzerinde)

Proje mimarisi bulut üzerinde aşağıdaki bileşenlerden oluşacaktır:

1. **Veritabanı Katmanı (AWS RDS):** Verilerin güvenli ve yedekli tutulması için yönetilen bir ilişkisel veritabanı.
2. **Uygulama Katmanı (AWS EC2 + Auto Scaling Group):** Java Spring Boot uygulamamızın koştuğu sunucular. İşlemci (CPU) kullanımı %70'i geçtiğinde otomatik olarak yeni EC2 sunucuları ayağa kalkacaktır (Auto Scaling).
3. **Yük Dengeleyici (AWS Application Load Balancer):** Kullanıcılardan gelen istekleri karşılayıp, arkadaki birden fazla EC2 (Spring Boot) sunucusuna dağıtacak.
4. **Backend Teknolojisi:** Java 17+, Spring Boot, Spring Data JPA, Spring Security.

## Proje Geliştirme Aşamaları

### Aşama 1: Lokal Geliştirme (Spring Boot Uygulaması)
- Spring Boot projesinin oluşturulması (Product, Cart, Order, User modülleri).
- Veritabanı bağlantılarının ayarlanması.
- Uygulamanın çalışırlığının lokalde test edilmesi.

### Aşama 2: Docker ve Cloud Hazırlığı
- Uygulamanın bir `.jar` dosyası olarak paketlenmesi.
- AWS EC2 üzerinde çalışacak betiklerin (User Data script) hazırlanması (Java'nın kurulması ve uygulamanın başlatılması).

### Aşama 3: Bulut Altyapısının Kurulması (Cloud Platform)
- RDS (Veritabanı) örneğinin ayağa kaldırılması.
- EC2 için bir şablon (Launch Template) oluşturulması.
- Auto Scaling Group (Otomatik Ölçeklendirme Grubu) yapılandırması.
- Load Balancer yapılandırması ve Auto Scaling Group ile ilişkilendirilmesi.

### Aşama 4: Ölçeklendirme Testi ve Doğrulama
- Apache JMeter veya benzer bir araç ile Load Balancer'a sahte yük (trafik) gönderilmesi.
- Sistem CPU kullanımının artmasıyla AWS'nin otomatik olarak yeni sunucu (EC2) açtığının kanıtlanması (ekran görüntüleri ile).

## Doğrulama Planı

### Otomatik ve Manuel Doğrulama
- Projenin Load Balancer DNS adresi üzerinden sorunsuz erişilebilir olması.
- Yük testi sonucunda Auto Scaling'in tetiklenip yeni instancelar başlattığının bulut konsolundan izlenmesi.
- Veritabanının dışarıya kapalı, sadece EC2 sunucularına açık (Security Group) olduğunun teyit edilmesi.
