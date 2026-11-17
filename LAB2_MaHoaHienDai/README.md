# LAB 2 - Mã hóa đối xứng hiện đại (Eclipse, Java 17)

Project Maven hoàn chỉnh dựa theo `LAB2_HuongDan.pdf` (49 trang), package `week_02`. Các form Swing được viết bằng code thay cho NetBeans GUI Designer.

## Import vào Eclipse

1. Giải nén toàn bộ ZIP ra một thư mục.
2. Eclipse -> File -> Import -> Maven -> Existing Maven Projects -> chọn thư mục `LAB2_MaHoaHienDai` -> Finish.
3. Project -> Properties -> Java Build Path -> chọn JDK 17. Maven -> Update Project (Internet cần thiết để tải `bcprov` và JDBC).
4. Mở `src/main/java/week_02/frm_Menu.java` -> Run As -> Java Application.

## Bài thực hành

- 2.1 `DESCipher` / `frm_DES`: DES CBC/PKCS5, IV ngẫu nhiên, Base64(IV+cipher), khóa 8 byte.
- 2.2 `TripleDESCipher` / `frm_TripleDES`: 3DES CBC, khóa 24 byte.
- 2.3 `AESCipher` / `frm_AES`: AES-256-GCM, PBKDF2, đăng ký/login qua SQL, ticket phiên.
- 2.4 `RC4Cipher` / `frm_RC4`: RC4 KSA+PRGA, xuất HEX.
- 2.5 `TwofishCipher` / `frm_Twofish`: Twofish CBC/PKCS7 (BouncyCastle), IV HEX.
- 2.6 `AESCTRCipher` / `frm_AES_CTR`: AES-CTR, key/nonce Base64, lưu NONCE + ciphertext.
- Nâng cao 02: `LAB2_Python/aes_cbc_gui.py` Tkinter, cần `pip install cryptography`.

## SQL Server SUGAR

Cài SQL Server 2022 Express sẵn có trên máy; không tạo instance mới. Chạy ba script `sql/01_...`, `sql/02_...`, `sql/03_...` lần lượt trong SSMS kết nối `LAPTOP-1OLPGQ5K\SUGAR` bằng Windows Authentication.

Trong SQL Server Configuration Manager, bật TCP/IP cho `SUGAR`, cấu hình cổng cố định ví dụ 14333 (hoặc cổng trống khác) và restart riêng dịch vụ SQL Server (SUGAR). Trong form Kết nối SQL: Server `127.0.0.1`, Port là cổng thực tế, Database `LAB2_MaHoaHienDai`, User `lab2_user`, Password mẫu `LabUser@2026`. Mixed Mode phải bật. SSMS kết nối bằng Shared Memory không thay thế được kết nối JDBC qua TCP.

**Lưu ý:** SQL chưa kết nối thì các thuật toán DES/3DES/RC4/Twofish/AES-CTR vẫn chạy, chỉ không lưu lịch sử. AES Register/Login cần SQL hoạt động.

## Test mẫu

- DES: `Xin chào Lab 2 - Mã hóa hiện đại!` + `12345678`: decrypt(encrypt(x)) = x, 2 lần encrypt phải khác vì IV ngẫu nhiên.
- TripleDES: khóa `123456789012345678901234`; 23 byte phải báo lỗi.
- AES: Register/Login, tạo ticket 32 ký tự HEX, Key AES `MatKhauAES@2026`, sai khóa phải báo lỗi.
- RC4: Plaintext `Plaintext`, Key `Key` -> `BBF316E8D940AF0AD3`.
- Twofish: Generate IV, encrypt/decrypt, Save/Open phải khôi phục IV.
- AES-CTR: Generate Key + Nonce, encrypt/decrypt, Save/Open phải khôi phục nonce (Key phải lưu riêng).
- AES-CBC Python: `python LAB2_Python/aes_cbc_gui.py --self-test` -> `SELF-TEST OK`.

DES, TripleDES, RC4 và CBC không xác thực ciphertext, chỉ dành cho mục tiêu thực hành. Với dữ liệu thật nên dùng thuật toán AEAD hiện đại như AES-GCM.
