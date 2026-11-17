-- Chạy bằng Windows Authentication có quyền admin. Đổi mật khẩu mẫu khi cần.
USE master;
GO
IF SUSER_ID(N'lab2_user') IS NULL
  CREATE LOGIN lab2_user WITH PASSWORD = 'LabUser@2026', CHECK_POLICY = ON;
GO
USE LAB2_MaHoaHienDai;
GO
IF DATABASE_PRINCIPAL_ID(N'lab2_user') IS NULL CREATE USER lab2_user FOR LOGIN lab2_user;
GO
IF IS_ROLEMEMBER('db_datareader','lab2_user') = 0 ALTER ROLE db_datareader ADD MEMBER lab2_user;
IF IS_ROLEMEMBER('db_datawriter','lab2_user') = 0 ALTER ROLE db_datawriter ADD MEMBER lab2_user;
GO
