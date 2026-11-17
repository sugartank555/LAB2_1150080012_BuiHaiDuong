# LAB 2 – MÃ HÓA ĐỐI XỨNG HIỆN ĐẠI

## 1. Thông tin sinh viên

- **Họ và tên:** Bùi Hải Đường
- **Mã số sinh viên:** 1150080012
- **Lớp:** 11CNPM1
- **Học phần:** An toàn và Bảo mật thông tin
- **Bài thực hành:** LAB 2 – Mã hóa đối xứng hiện đại
- **Môi trường phát triển:** Eclipse IDE, Java 17, Maven, SQL Server 2022 Express, Python

---

## 2. Giới thiệu đề tài

LAB 2 tập trung tìm hiểu và xây dựng các chương trình mã hóa đối xứng hiện đại bằng Java và Python.

Mục tiêu của bài thực hành:

- Tìm hiểu nguyên lý hoạt động của các thuật toán DES, TripleDES, AES, RC4 và Twofish.
- Thực hiện mã hóa và giải mã dữ liệu văn bản.
- Sử dụng các chế độ mã hóa CBC, GCM và CTR.
- Xây dựng giao diện Java Swing để tương tác với người dùng.
- Kết nối Microsoft SQL Server để quản lý tài khoản và lịch sử thao tác.
- Thực hiện đăng ký, đăng nhập và xác thực người dùng bằng AES-GCM kết hợp SQL Server.
- Phát triển chương trình AES-CBC bằng Python với giao diện Tkinter.

**Lưu ý:** DES, TripleDES và RC4 được sử dụng để học tập, không khuyến nghị dùng trong hệ thống thực tế.

---

## 3. Công nghệ sử dụng

| Công nghệ | Mục đích |
|---|---|
| Java JDK 17 | Ngôn ngữ lập trình chính |
| Eclipse IDE | Phát triển và chạy ứng dụng Java |
| Apache Maven | Quản lý thư viện và biên dịch project |
| Java Swing | Xây dựng giao diện desktop |
| Java Cryptography Architecture | Thực hiện các thuật toán mã hóa |
| BouncyCastle | Hỗ trợ thuật toán Twofish |
| Microsoft SQL Server 2022 Express | Lưu trữ tài khoản và lịch sử |
| Microsoft JDBC Driver | Kết nối Java với SQL Server |
| Python 3 | Xây dựng chương trình AES-CBC |
| Tkinter | Thiết kế giao diện Python |
| cryptography | Thư viện mã hóa AES-CBC |

---

## 4. Cấu trúc thư mục project

```text
LAB2_MaHoaHienDai/
│
├── pom.xml
├── README.md
│
├── src/
│   └── main/
│       └── java/
│           └── week_02/
│               ├── CipherUtil.java
│               ├── FileTextIO.java
│               ├── DbConfig.java
│               ├── HistoryDao.java
│               ├── UserDao.java
│               │
│               ├── DESCipher.java
│               ├── TripleDESCipher.java
│               ├── AESCipher.java
│               ├── RC4Cipher.java
│               ├── TwofishCipher.java
│               ├── AESCTRCipher.java
│               │
│               ├── frm_DES.java
│               ├── frm_TripleDES.java
│               ├── frm_AES.java
│               ├── frm_RC4.java
│               ├── frm_Twofish.java
│               ├── frm_AES_CTR.java
│               ├── frm_KetNoiSQL.java
│               ├── frm_LichSu.java
│               └── frm_Menu.java
│
├── sql/
│   ├── 01_Tao_CSDL.sql
│   ├── 02_Tao_Tai_Khoan_SQL.sql
│   └── 03_Kiem_Tra_Du_Lieu.sql
│
├── python/
│   └── aes_cbc_gui.py
│
└── screenshots/
    ├── 00_Menu.png
    ├── 01_DES.png
    ├── 02_TripleDES.png
    ├── 03_AES_Login.png
    ├── 04_RC4.png
    ├── 05_Twofish.png
    ├── 06_AES_CTR.png
    ├── 07_AES_CBC_Python.png
    └── 08_SQL_Server.png
```

---

## 5. Quá trình thực hiện

### Bước 1: Chuẩn bị môi trường

Cài đặt và kiểm tra Java JDK 17, Eclipse IDE, Maven và SQL Server 2022 Express.

Kiểm tra phiên bản Java:

```bash
java -version
javac -version
```

Tạo Maven Project:

- Group ID: `vn.edu.lab`
- Artifact ID: `LAB2_MaHoaHienDai`
- Version: `1.0-SNAPSHOT`
- Packaging: `jar`
- Package: `week_02`

Thêm thư viện Microsoft JDBC Driver và BouncyCastle trong `pom.xml`.

### Bước 2: Xây dựng các lớp dùng chung

**CipherUtil.java**

Cung cấp các chức năng:

- Chuyển đổi văn bản UTF-8 sang byte và ngược lại.
- Mã hóa và giải mã Base64.
- Chuyển đổi dữ liệu HEX.
- Sinh khóa, IV, nonce và salt ngẫu nhiên.
- Tạo khóa bằng PBKDF2-HMAC-SHA256.

**FileTextIO.java**

Hỗ trợ đọc và ghi tệp văn bản UTF-8 bằng JFileChooser.

### Bước 3: Xây dựng các thuật toán mã hóa

Mỗi thuật toán có một lớp xử lý mã hóa và một giao diện Java Swing riêng.

### Bước 4: Xây dựng cơ sở dữ liệu

Sử dụng Microsoft SQL Server 2022 Express, instance `SUGAR`.

Database:

```sql
LAB2_MaHoaHienDai
```

Hai bảng chính:

- `dbo.app_users`: Lưu tài khoản, salt và password hash.
- `dbo.crypto_history`: Lưu tên thuật toán, thao tác, người dùng, thời gian và độ dài dữ liệu.

Mật khẩu tài khoản được xử lý bằng PBKDF2 kết hợp salt, không lưu mật khẩu gốc vào database.

### Bước 5: Xây dựng chức năng xác thực AES

Chương trình hỗ trợ:

1. Register tài khoản mới.
2. Login bằng username và password.
3. Sinh Ticket sau khi đăng nhập thành công.
4. Chỉ cho phép Encrypt/Decrypt AES sau khi Login.
5. Ghi lịch sử thao tác vào SQL Server.

### Bước 6: Tạo menu chính

Xây dựng `frm_Menu.java` để mở các form thuật toán và chức năng SQL Server.

---

## 6. Nội dung và kết quả các bài thực hành

### Bài 2.1 – Mật mã DES

**Thuật toán:** DES/CBC/PKCS5Padding.

DES mã hóa dữ liệu theo khối 64 bit, sử dụng khóa 8 byte và IV ngẫu nhiên 8 byte.

**Dữ liệu kiểm thử:**

```text
Plaintext: Xin chào Lab 2 - Mã hóa hiện đại!
Key: 12345678
```

**Kết quả mong đợi:**

- Encrypt tạo Ciphertext dưới dạng Base64.
- Encrypt hai lần cho hai bản mã khác nhau do IV ngẫu nhiên.
- Decrypt khôi phục đúng Plaintext ban đầu.
- Khóa không đủ 8 byte được thông báo lỗi.
- Có thể lưu và mở lại Ciphertext bằng tệp `.txt`.

**Ảnh minh chứng:**

![Kết quả DES](screenshots/01_DES.png)

### Bài 2.2 – Mật mã TripleDES

**Thuật toán:** DESede/CBC/PKCS5Padding.

TripleDES thực hiện mã hóa DES ba lần với khóa dài 24 byte.

**Dữ liệu kiểm thử:**

```text
Plaintext: Xin chào Lab 2 - Mã hóa hiện đại!
Key: 123456789012345678901234
```

**Kết quả mong đợi:**

- Encrypt tạo Ciphertext Base64.
- Decrypt khôi phục đúng bản rõ.
- Khóa 23 byte phải bị từ chối.
- Có thể lưu và đọc lại Ciphertext từ tệp.

**Ảnh minh chứng:**

![Kết quả TripleDES](screenshots/02_TripleDES.png)

### Bài 2.3 – AES-GCM kết hợp đăng ký, đăng nhập

**Thuật toán:** AES-256/GCM/NoPadding.

Khóa AES được tạo từ mật khẩu bằng PBKDF2-HMAC-SHA256, sử dụng salt và nonce ngẫu nhiên.

**Quy trình kiểm thử:**

1. Kết nối SQL Server.
2. Register tài khoản mới.
3. Login bằng tài khoản vừa đăng ký.
4. Nhận Ticket đăng nhập.
5. Thực hiện mã hóa AES-GCM.
6. Giải mã và kiểm tra dữ liệu.
7. Thay đổi Ciphertext để kiểm tra khả năng phát hiện chỉnh sửa.

**Dữ liệu kiểm thử:**

```text
Username: sinhvien01
Password: [Mật khẩu tự đặt, tối thiểu 8 ký tự]

Plaintext: Xin chào Lab 2 - Mã hóa hiện đại!
Key AES: MatKhauAES@2026
```

**Kết quả mong đợi:**

- Đăng ký và đăng nhập thành công.
- Ticket gồm 32 ký tự HEX.
- Encrypt/Decrypt khôi phục chính xác dữ liệu.
- Ciphertext bị chỉnh sửa gây lỗi xác thực GCM.
- Database ghi nhận REGISTER, LOGIN, ENCRYPT và DECRYPT.

**Ảnh minh chứng:**

![AES Login](screenshots/03_AES_Login.png)

### Bài 2.4 – Mật mã dòng RC4

RC4 sử dụng thuật toán KSA và PRGA để sinh keystream, sau đó XOR với dữ liệu đầu vào.

**Dữ liệu kiểm thử:**

```text
Plaintext: Plaintext
Key: Key
```

**Ciphertext chuẩn:**

```text
BBF316E8D940AF0AD3
```

**Kết quả mong đợi:**

- Encrypt tạo đúng chuỗi HEX trên.
- Decrypt khôi phục `Plaintext`.
- Bản mã được biểu diễn dưới dạng HEX.
- Có thể lưu và mở tệp bản mã.

**Ảnh minh chứng:**

![Kết quả RC4](screenshots/04_RC4.png)

### Bài 2.5 – Mật mã Twofish

Twofish sử dụng khối 128 bit, khóa 256 bit, chế độ CBC và IV 16 byte.

**Dữ liệu kiểm thử:**

```text
Plaintext: Xin chào Lab 2 - Mã hóa hiện đại!
Key: Twofish@Lab2
IV: Sinh bằng Generate IV
```

**Kết quả mong đợi:**

- Generate IV tạo chuỗi HEX dài 32 ký tự.
- Encrypt tạo Ciphertext Base64.
- Decrypt khôi phục đúng Plaintext.
- Sử dụng IV khác làm giải mã thất bại hoặc cho dữ liệu không đúng.
- Save File lưu IV và Ciphertext.
- Open File khôi phục lại IV và Ciphertext.

**Ảnh minh chứng:**

![Kết quả Twofish](screenshots/05_Twofish.png)

### Bài 2.6 – Nâng cao 01: AES-CTR

AES-CTR sử dụng bộ đếm để tạo keystream và mã hóa dữ liệu mà không cần padding.

**Quy trình kiểm thử:**

1. Generate Key 256 bit.
2. Generate Nonce 96 bit.
3. Nhập Plaintext.
4. Encrypt.
5. Xóa Plaintext.
6. Decrypt với đúng Key và Nonce.
7. Kiểm tra dữ liệu khôi phục.
8. Thử mã hóa đoạn văn trên 1000 ký tự.

**Kết quả mong đợi:**

- Encrypt/Decrypt thành công.
- Độ dài dữ liệu mã hóa tính theo byte bằng độ dài bản rõ tính theo byte.
- Ciphertext được hiển thị Base64.
- File lưu Nonce và Ciphertext, không lưu Key.
- Không tái sử dụng cùng cặp Key/Nonce cho nhiều thông điệp khác nhau.

**Ảnh minh chứng:**

![Kết quả AES-CTR](screenshots/06_AES_CTR.png)

### Bài nâng cao 02 – AES-CBC bằng Python

Xây dựng chương trình AES-256-CBC với giao diện Tkinter và thư viện `cryptography`.

**Các chức năng:**

- Generate Key.
- Generate IV.
- Encrypt.
- Decrypt.
- Save File.
- Open File.
- Self-test.

Chạy kiểm thử:

```bash
py aes_cbc_gui.py --self-test
```

**Kết quả mong đợi:**

```text
SELF-TEST OK: 4 thông điệp mã hóa/giải mã đúng,
sai khóa bị phát hiện.
```

**Ảnh minh chứng:**

![AES-CBC Python](screenshots/07_AES_CBC_Python.png)

---

## 7. Kết nối và kiểm thử SQL Server

### Cấu hình kết nối

| Thuộc tính | Giá trị |
|---|---|
| SQL Server | SQL Server 2022 Express |
| Instance | SUGAR |
| Server JDBC | 127.0.0.1 |
| Port | 14333 |
| Database | LAB2_MaHoaHienDai |
| SQL Login | lab2_user |
| Authentication | SQL Server Authentication |

Mật khẩu SQL được nhập khi chạy chương trình, không lưu trực tiếp trong mã nguồn Java.

### Kiểm tra dữ liệu

Chạy câu lệnh sau trong SQL Server Management Studio:

```sql
USE LAB2_MaHoaHienDai;
GO

SELECT TOP (20)
    user_id,
    username,
    created_at
FROM dbo.app_users
ORDER BY user_id DESC;

SELECT TOP (50)
    history_id,
    created_at,
    algorithm,
    operation,
    app_username,
    input_length,
    output_length
FROM dbo.crypto_history
ORDER BY history_id DESC;
```

**Kết quả mong đợi:**

- Bảng `app_users` chứa tài khoản đã đăng ký.
- Bảng `crypto_history` chứa lịch sử các thao tác.
- Không lưu Plaintext, Ciphertext hoặc khóa mã hóa vào bảng lịch sử.
- Có thể sử dụng chức năng Xem lịch sử trong ứng dụng Java.

**Ảnh minh chứng:**

![SQL Server](screenshots/08_SQL_Server.png)

---

## 8. Hướng dẫn chạy project

### 8.1. Chạy ứng dụng Java bằng Eclipse

**Bước 1:** Mở Eclipse IDE.

**Bước 2:** Chọn:

```text
File → Import → Maven → Existing Maven Projects
```

**Bước 3:** Chọn thư mục `LAB2_MaHoaHienDai`.

**Bước 4:** Đợi Maven tải các thư viện cần thiết.

**Bước 5:** Mở:

```text
src/main/java/week_02/frm_Menu.java
```

**Bước 6:** Nhấp chuột phải và chọn:

```text
Run As → Java Application
```

Chương trình sẽ mở menu chính.

### 8.2. Chuẩn bị database

Mở SQL Server Management Studio và chạy lần lượt:

```text
sql/01_Tao_CSDL.sql
sql/02_Tao_Tai_Khoan_SQL.sql
sql/03_Kiem_Tra_Du_Lieu.sql
```

Script kiểm tra dữ liệu nên chạy lại sau khi thực hiện Register/Login và mã hóa để xem lịch sử mới.

Cần bảo đảm SQL Server đã bật TCP/IP và cổng được cấu hình đúng.

### 8.3. Chạy chương trình Python

Mở terminal tại thư mục `python` rồi cài thư viện:

```bash
py -m pip install cryptography
```

Chạy self-test:

```bash
py aes_cbc_gui.py --self-test
```

Chạy giao diện:

```bash
py aes_cbc_gui.py
```

---

## 9. Tổng hợp kiểm thử

| STT | Bài thực hành | Tiêu chí kiểm thử | Trạng thái |
|---|---|---|---|
| 1 | DES | Mã hóa, giải mã, IV ngẫu nhiên | Cần xác nhận trên máy |
| 2 | TripleDES | Khóa 24 byte, mã hóa và giải mã | Cần xác nhận trên máy |
| 3 | AES-GCM | Register, Login, Ticket, AES | Cần xác nhận SQL |
| 4 | RC4 | HEX chuẩn BBF316E8D940AF0AD3 | Cần xác nhận trên máy |
| 5 | Twofish | IV, Encrypt, Decrypt, Open/Save | Cần xác nhận trên máy |
| 6 | AES-CTR | Generate Key/Nonce, Encrypt/Decrypt | Cần xác nhận trên máy |
| 7 | AES-CBC Python | Self-test và giao diện Tkinter | Cần xác nhận trên máy |
| 8 | SQL Server | Kết nối, tài khoản và lịch sử | Cần xác nhận đăng nhập SQL |

Trạng thái trong bảng sẽ được cập nhật sau khi thực hiện và chụp ảnh kiểm thử đầy đủ.

---

## 10. Nhận xét và đánh giá

Qua quá trình xây dựng LAB 2, bài thực hành giúp tìm hiểu sự khác nhau giữa các thuật toán mã hóa đối xứng và cách sử dụng chúng trong ứng dụng.

**Những kiến thức chính:**

- DES và TripleDES có hạn chế về độ an toàn, chủ yếu phù hợp mục đích học tập.
- AES-GCM cung cấp cả tính bảo mật và xác thực dữ liệu.
- RC4 minh họa nguyên lý mã hóa dòng và phép XOR.
- Twofish cho thấy cách sử dụng thư viện mã hóa bên ngoài Java.
- AES-CTR không cần padding nhưng không cung cấp xác thực dữ liệu.
- AES-CBC cần IV phù hợp và cơ chế padding.
- PBKDF2 kết hợp salt được sử dụng để xử lý mật khẩu.
- SQL Server hỗ trợ lưu tài khoản và lịch sử thao tác của ứng dụng.

**Hạn chế:** Các chương trình được xây dựng phục vụ học tập và minh họa thuật toán, chưa phải giải pháp bảo mật hoàn chỉnh dành cho môi trường sản xuất.

---

## 11. Kết luận

LAB 2 cung cấp kiến thức thực hành về mã hóa đối xứng hiện đại thông qua việc xây dựng chương trình Java Swing và Python.

Bài thực hành kết hợp thuật toán mã hóa, giao diện người dùng, quản lý tệp, xác thực tài khoản và lưu trữ dữ liệu trên SQL Server.

Thông qua các bài tập, sinh viên có thể củng cố kỹ năng lập trình Java, Python, JDBC, SQL Server và hiểu rõ hơn vai trò của mã hóa trong việc bảo vệ thông tin.

---

## 12. Tài liệu tham khảo

1. Tài liệu học phần An toàn và Bảo mật thông tin – LAB 2: Mã hóa đối xứng hiện đại.
2. Oracle Java Cryptography Architecture Documentation.
3. Microsoft JDBC Driver for SQL Server Documentation.
4. BouncyCastle Cryptography Documentation.
5. Python `cryptography` Documentation.

---

**Sinh viên thực hiện:** [Họ tên]  
**MSSV:** [Mã số sinh viên]  
**Lớp:** [Tên lớp]
